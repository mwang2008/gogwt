package com.gogwt.apps.tracking.services;

import static com.gogwt.apps.tracking.GoGWTConstants.FAKE_MESSGE_BODY;
import static com.gogwt.apps.tracking.GoGWTConstants.SEND_BODY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.gogwt.apps.tracking.GwtConfig;
import com.gogwt.apps.tracking.activities.LocationTrackingActivity;
import com.gogwt.apps.tracking.data.GLocation;
import com.gogwt.apps.tracking.data.GPXPoint;
import com.gogwt.apps.tracking.data.GSmsData;
import com.gogwt.apps.tracking.data.ICollectionListener;
import com.gogwt.apps.tracking.data.IRemoteInterface;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.request.LocationRequest;
import com.gogwt.apps.tracking.data.response.LocationResponse;
import com.gogwt.apps.tracking.services.http.SendLocation;
import com.gogwt.apps.tracking.utils.GwtLog;
import com.gogwt.apps.tracking.utils.NotifyMessageUtils;
import com.gogwt.apps.tracking.utils.SessionManager;

 

/**
 * <pre>
 * telnet localhost 5554 
 * geo fix -83.22 33.44
 * 
 * telnet localhost 5554
 * sms send 12333333 hi there
 * </pre>
 * 
 * <pre>
 * call sequences: Step app start: 
 * 1) GoGPSTrackingActivity.onCreate 
 * 2) GoGPSTrackingActivity.onResume 
 * 3) GPXService.onCreate 
 * 4) GPXService.onBind 
 * 5) GoGPSTrackingActivity.onServiceConnected
 * 
 * Note: onStart is not got called when to use binding approach
 * </pre>
 * 
 * <pre>
 * sms: register to listen the resource changes under content://sms
 * whenever any changes under sms such as send, receive, read/write to that resource. 
 * chechSMS will be invoked and filter for the interested actions: send or read message,
 * 
 * telnet localhost 5554
 * sms send 123456789 hi there
 * </pre>
 * 
 * 
 * @author michael.wang
 * 
 */
public class GPXService extends Service {

	private LocationManager locationManager = null;

	public static final String GPX_SERVICE = "com.gogwt.apps.tracking.services.GPXService.SERVICE";

	private static final String TAG = GPXService.class.getSimpleName();
	public static final String GPS_EXTRA_UPDATE_RATE = "gps_update-rate";

	private static final int GPS_UPDATE_RATE_IN_SEC = 15; // 20s
	private static final int TIMER_UPDATE_RATE_IN_SEC = 30;
	private static final int GPS_CHANGE_NUMBER = 5;

	private List<ICollectionListener> listeners = new ArrayList<ICollectionListener>();
	private int gpsUpdateRate;
	private long lastTime = -1;
	private long firstTime = -1;
	private double totalDistance;
	private double srvTotalDistance;	 
	private String groupId;
	private String startEnableGPSTime;
	private Location currentLocation;
	private Location lastLocation = null;
	private Location srvLastLocation = null;
	private int numOfCycle = 0;
	private Timer timer;
	private SendLocation sendLocation;
	private ArrayList<GLocation> locationList = new ArrayList<GLocation>();
	private ArrayList<GSmsData> smsList = new ArrayList<GSmsData>();
	
	private SmsContentObserver smsContentObserver = null;
	private long lastTimeSMSRetrive = -1;
	private final Object gpxPointLocationLock = new Object();
	
	@Override
	public void onCreate() {
		super.onCreate();
		GwtLog.d(TAG, "==== onCreate");
		//locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		int currentTimerRate = TIMER_UPDATE_RATE_IN_SEC;
		timer = new Timer("GPXService-Timer");
		timer.schedule(updateTask, 1000L, currentTimerRate * 1000L);
	}

	/**
	 * not call as used binding approach
	 */
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		 
		GwtLog.d(TAG, "==== onStart");
		initGPSProvider(intent);
		registerContentObservers();
	}
 	
	@Override
	public void onDestroy() {
		super.onDestroy();

		GwtLog.d(TAG, "==== onDestroy");

		if (locationManager != null) {
			locationManager.removeUpdates(trackListener);
			locationManager = null;
		}

		String endGPSTime = android.text.format.DateFormat.format(
				"yyyy-MM-dd hh:mm:ss", new java.util.Date()).toString();

		String info = "Tracking started at " + startEnableGPSTime;
		info += " and stopped at + " + endGPSTime;

		// notify that we've stopped
		// NotifyMessageUtils.showNotifyMsg(getApplicationContext(),
		//   "GoGPS Tracking Stoped at " + endGPSTime, info);

		Toast.makeText(getApplicationContext(),
				"GPS Tracking - Tracking stopped " + info, Toast.LENGTH_SHORT)
				.show();

		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		
		unregisterContentObservers();
	}

	@Override
	public IBinder onBind(Intent intent) {
		GwtLog.d(TAG, "==== onBind");
		initGPSProvider(intent);
		registerContentObservers();
		return mRemoteInterfaceBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		GwtLog.d(TAG, "==== onUnbind");
		firstTime = -1;
		return super.onUnbind(intent);
	}

	/**
	 * 
	 */
	private TimerTask updateTask = new TimerTask() {
		@Override
		public void run() {

			try {
				GwtLog.d(TAG, "== Timer task doing work - "	+ System.currentTimeMillis());
				 
				if (sendLocation == null) {
					sendLocation = new SendLocation();
				}

				Profile profile = SessionManager.getProfile(getApplicationContext());
				
				LocationRequest request = new LocationRequest();
				List<GLocation> mylocationList = new ArrayList<GLocation>();
				synchronized (locationList) {
					if (locationList != null && !locationList.isEmpty()) {
					   mylocationList.addAll(locationList);
					   locationList.clear();
					}
				}
				
				List<GSmsData> mySmsDataList = new ArrayList<GSmsData>();
				synchronized (smsList) {
					if (smsList != null && !smsList.isEmpty()) {
						mySmsDataList.addAll(smsList);
					}
					smsList.clear();
				}

				request.setLocations(mylocationList);
				request.setProfile(profile);
				request.setSmsDataList(mySmsDataList);

				// todo process result later
				GwtLog.d(TAG, request.toString());
				LocationResponse result = sendLocation.httpPost(request);

			} catch (Throwable t) {
				GwtLog.e(TAG, "Failed to send results", t);
			}
		}
	};

	/**
	 * 
	 * @param intent
	 */
	private void initGPSProvider(Intent intent) {
		gpsUpdateRate = intent.getIntExtra(GPS_EXTRA_UPDATE_RATE, -1);
		if (gpsUpdateRate == -1) {
			gpsUpdateRate = GPS_UPDATE_RATE_IN_SEC; // 20s
		}

		/*
		 * String provider = null; if
		 * (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
		 * provider = LocationManager.GPS_PROVIDER; } else { if (locationManager
		 * .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) { provider =
		 * LocationManager.NETWORK_PROVIDER; Toast.makeText(this,
		 * "Provider NETWORK_PROVIDER", 3000).show(); } else {
		 * Toast.makeText(this, "Provider Not available", 3000 ).show(); } }
		 * 
		 * if (provider != null) { locationManager.removeUpdates(trackListener);
		 * locationManager.requestLocationUpdates(provider, gpsUpdateRate, 0,
		 * trackListener); }
		 */

		String provider = changeRequestLocationFrequency(0);

		android.text.format.DateFormat df = new android.text.format.DateFormat();
		startEnableGPSTime = android.text.format.DateFormat.format(
				"yyyy-MM-dd hh:mm:ss", new java.util.Date()).toString();

		String gpsProviderInfo = "Tracking starts at " + gpsUpdateRate
				+ "s intervals with [" + provider + "] as the provider";

		//NotifyMessageUtils.showNotifyMsg(getApplicationContext(),
		//    "GoGPS starts: " + startEnableGPSTime, gpsProviderInfo);        
		NotifyMessageUtils.showNotifyMsgWithResume(LocationTrackingActivity.class, this.getApplicationContext(), "GPS - Start", gpsProviderInfo);
		
		Toast.makeText(getApplicationContext(), "GPS Tracking - Start ",
				Toast.LENGTH_SHORT).show();
	}

	private String changeRequestLocationFrequency(int updateRate) {
		String provider = null;
		if (getLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			provider = LocationManager.GPS_PROVIDER;
		} else {
			if (getLocationManager()
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
				provider = LocationManager.NETWORK_PROVIDER;
				Toast.makeText(this, "Provider NETWORK_PROVIDER", 3000).show();
			} else {
				Toast.makeText(this, "Provider Not available", 3000).show();
			}
		}

		if (provider != null) {
			getLocationManager().removeUpdates(trackListener);
			getLocationManager().requestLocationUpdates(provider, updateRate, 0,
					trackListener);
		}

		return provider;
	}

	private LocationManager getLocationManager() {
		if (locationManager == null) {
		   locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		}
		return locationManager;
	}
	/**
	 * remote interface
	 */
	
	private final IRemoteInterface.Stub mRemoteInterfaceBinder = new IRemoteInterface.Stub() {
		public Location getLastLocation() {
			GwtLog.d("interface", "===== getLastLocation() called");
			return lastLocation;
		}
		
		@Override
		public GPXPoint getGPXPoint() {
			GwtLog.i("interface", "==== getGPXPoint() called");
			synchronized (gpxPointLocationLock) {
			   if (currentLocation == null) {
				 return null;
			   }
			 
			   return fromCurlocation();
			}
		}

		@Override
		public void addListener(ICollectionListener listener)
				throws RemoteException {
			synchronized (listeners) {
				listeners.add(listener);
			}
		}

		@Override
		public void removeListener(ICollectionListener listener)
				throws RemoteException {
			synchronized (listeners) {
				listeners.remove(listener);
			}
		}

		private GPXPoint fromCurlocation() {
			GPXPoint point = new GPXPoint();

			point.latitude = (int) (currentLocation.getLatitude() * 1E6); // .getLatitude();
			point.longitude = (int) (currentLocation.getLongitude() * 1E6);
			point.altitude = currentLocation.getAltitude();
			point.provider = currentLocation.getProvider();
			point.accuracy = currentLocation.getAccuracy();
			point.bearing = currentLocation.getBearing();
			point.speed = currentLocation.getSpeed();

			if (lastLocation != null) {
				double distance = currentLocation.distanceTo(lastLocation);
				point.distance = distance;
				totalDistance += distance;
			} else {
				// first time
				//firstTime = System.currentTimeMillis();
				totalDistance = 0;
			}

			/*
			if (firstTime == -1) {
			   firstTime = System.currentTimeMillis(); //SessionManager.getGpxContext().getFirstTime(); //System.currentTimeMillis();
			}
			*/
			
			point.startTime = getFirstTime();
			point.totalDistance = totalDistance;

			lastLocation = currentLocation;

			return point;
		}

	};

	/**
	 * GPS, listening the geocode changes.
	 */
	private LocationListener trackListener = new LocationListener() {

		/**
		 * Callback from GPS satellite
		 */
		@Override
		public void onLocationChanged(Location location) {
			GwtLog.d(TAG, "=== onLocationChanged ");

			long thisTime = System.currentTimeMillis();
			long diffTime = thisTime - lastTime;

			lastTime = thisTime;
			currentLocation = location;

			if (numOfCycle == GPS_CHANGE_NUMBER) {
				numOfCycle = GPS_CHANGE_NUMBER + 1;
				changeRequestLocationFrequency(gpsUpdateRate);
			} else {
				numOfCycle++;
			}

			synchronized (listeners) {
				for (ICollectionListener listener : listeners) {
					try {
						// Note: listener.updateLocation(point) is not working
						// as point bind to different thread
						listener.handleLocationUpdated();
					} catch (RemoteException e) {
						Log.w(TAG, "Failed to notify listener " + listener, e);
					}
				}
			}

			double distance=0.0;
			if (srvLastLocation != null) {
				distance = location.distanceTo(srvLastLocation);	//in meters		 
				srvTotalDistance += distance;
			} else {
				srvTotalDistance = 0;
			}
			
			GLocation gLocation = new GLocation();
			 
			gLocation.setGroupId(groupId);
			gLocation.setLatitude((int) (location.getLatitude() * 1E6));
			gLocation.setLongitude((int) (location.getLongitude() * 1E6));
			gLocation.setAltitude(location.getAltitude());
			gLocation.setProvider(location.getProvider());

			gLocation.setAccuracy(location.hasAccuracy() == true ? location
					.getAccuracy() : -1);
			gLocation.setBearing(location.hasBearing() == true ? location
					.getBearing() : -1);
			gLocation.setSpeed(location.getSpeed());
			gLocation.setTime(location.getTime());
			gLocation.setDistance(distance);
			gLocation.setStartTime(getFirstTime());
			//gLocation.setStartTime(SessionManager.getGpxContext().getFirstTime());
			gLocation.setTotalDistance(srvTotalDistance);

			synchronized (locationList) {
				locationList.add(gLocation);
			}
			
			srvLastLocation = location;
			

			// save to db
			// saveToSQLite();
		}

		@Override
		public void onProviderDisabled(String provider) {

		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		private void saveToSQLite() {
			GLocation gLocation = new GLocation();
			Location location = currentLocation;

			gLocation.setGroupId(groupId);
			gLocation.setLatitude((int) (location.getLatitude() * 1E6));
			gLocation.setLongitude((int) (location.getLongitude() * 1E6));
			gLocation.setAltitude(location.getAltitude());
			gLocation.setProvider(location.getProvider());

			gLocation.setAccuracy(location.hasAccuracy() == true ? location
					.getAccuracy() : -1);
			gLocation.setBearing(location.hasBearing() == true ? location
					.getBearing() : -1);
			gLocation.setSpeed(location.getSpeed());
			gLocation.setTime(location.getTime());

			double distance = location.distanceTo(lastLocation);
			gLocation.setDistance(distance);
			gLocation.setStartTime(getFirstTime());
			gLocation.setTotalDistance(totalDistance);

			// save to db, save at end
			// LocationDAO.getInstance(getApplicationContext()).insertLocation(gLocation);
		}

		private String locInfo(Location location, long diffTime) {
			String locInfo = String.format(
					"Current loc = (%f, %f) @ (%.1f meters up)",
					location.getLatitude(), location.getLongitude(),
					location.getAltitude());

			float distance = 0;
			float lastSpeed = 0;
			if (lastLocation != null) {
				distance = location.distanceTo(lastLocation);
				locInfo += String.format("\n Distance from last = %.1f meters",
						distance);
				lastSpeed = distance / diffTime;
				locInfo += String.format("\n\tSpeed: %.1fm/s", lastSpeed);
				if (location.hasSpeed()) {
					float gpsSpeed = location.getSpeed();
					locInfo += String.format(" (or %.1fm/s)", lastSpeed,
							gpsSpeed);
				} else {

				}
			}
			return locInfo;
		}
	};
	
	/**
	 * Invoked whenever user send/receive sms 
	 * @author michael.wang
	 *
	 */
	class SmsContentObserver extends ContentObserver {
		public SmsContentObserver(Handler h) {
			super(h);
		}
		
		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			GwtLog.d(TAG, "SmsContentObserver.onChange( " + selfChange + ")");
			
			//catch all exception, do not want SMS function to affect others. 
			//as SMS function is undocument. 
			try {
   			   chechSMS();
			}
			catch (Throwable e) {
			   //skip, nothing	
				GwtLog.d(TAG, "error send/receive sms: " + e.getMessage());
			}			
		}
		
		@Override
	    public boolean deliverSelfNotifications() {
	        return true;
	    }
	}

	private String CONTENT_URI = "content://sms";
	/**
	 * monitor the changes of content://sms
	 */
 	private void registerContentObservers() {
 		GwtLog.d("== registerContentObservers", "registerContentObservers");
 		
 		if (smsContentObserver == null) {
		    ContentResolver contentResolver = getContentResolver();	 
		    smsContentObserver = new SmsContentObserver(new Handler());
		    Uri smsUri = Uri.parse(CONTENT_URI);
		    contentResolver.registerContentObserver(smsUri, true, smsContentObserver);
		     
		    lastTimeSMSRetrive = new Date().getTime(); //SessionManager.getGpxContext().getGpsStartTime().getTime();
 		}
		
	}

	private void unregisterContentObservers() {
		GwtLog.d("== unregisterContentObservers", "unregisterContentObservers");		
		if (smsContentObserver != null) {
			ContentResolver contentResolver = getContentResolver();
			contentResolver.unregisterContentObserver(smsContentObserver);
			smsContentObserver = null;
			lastTimeSMSRetrive = -1;
		}		
	}
	
	/**
	 * filter on when sent event or read sms action. 
	 * read [0:1] 0 -- new,  1 -- read 
	 * type [1:2] 1 -- receive(inbox), 2 -- send  
	 */
	private void chechSMS() {
		final int TYPE_RECEIVE  = 1;
		final int TYPE_SENT   = 2;
		final int READ_READ = 1;
	 	final int READ_UNREAD = 0;
	 	
		ContentResolver contentResolver = getContentResolver();
		SharedPreferences appPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		boolean sendBodyPref = appPreference.getBoolean(SEND_BODY, false);
		
		//type 1=inbox : 2=send   
		//read 0=new : 1=read
		String[] projection = {"address","date", "read", "type", "body"};
		StringBuilder whereBuf = new StringBuilder();
		whereBuf.append("type="+TYPE_SENT);
		whereBuf.append(" or ");
		whereBuf.append("type="+TYPE_RECEIVE);
		
		
		/*
		whereBuf.append("(type="+TYPE_SENT);
		whereBuf.append(" or ");
		whereBuf.append("type="+TYPE_RECEIVE + ") and date > " + lastTimeSMSRetrive);
		
		whereBuf.append(" (type="+TYPE_RECEIVE);		
		whereBuf.append(" and ");
		whereBuf.append(" read="+READ_READ +")");
		*/
		
		Cursor mCurSms = contentResolver.query(Uri.parse(CONTENT_URI), projection, whereBuf.toString(), null, "DATE desc");
		
	    //Cursor mCurSms = contentResolver.query(Uri.parse(CONTENT_URI),null,null,null,"DATE desc");
		
		int addressCol = mCurSms.getColumnIndex("address");
 		int dateCol = mCurSms.getColumnIndex("date");
 		int readCol = mCurSms.getColumnIndex("read");
 		int typeCol = mCurSms.getColumnIndex("type");
 		int bodyCol = mCurSms.getColumnIndex("body"); //"body"
		mCurSms.moveToFirst();
        long currItemDate, lastItemFetchedDate=-1;
		if (mCurSms.getCount() > 0) {
			do {				
				currItemDate = mCurSms.getLong(dateCol);
				if (currItemDate > lastTimeSMSRetrive) {
					GSmsData smsData = new GSmsData();
					
					smsData.address = mCurSms.getString(addressCol);
					smsData.date = new Date(currItemDate);
					smsData.read =  mCurSms.getInt(readCol);
					smsData.type = mCurSms.getInt(typeCol);
					
					if (sendBodyPref) {
					   smsData.body = mCurSms.getString(bodyCol);
					}
					else {
						smsData.body = FAKE_MESSGE_BODY;	
					}
					
					smsData.startTime = getFirstTime(); //SessionManager.getGpxContext().getFirstTime();
					
					smsList.add(smsData);
					lastItemFetchedDate = currItemDate;
				
				
				    if (GwtConfig.DEBUG) {
				       String str = "=== address: "	+ smsData.getAddress();
 				       str += " date: " + smsData.getDate() + " : " + mCurSms.getLong(dateCol);
 				       str += " read: " + smsData.getRead();				
 				       str += " type: " + smsData.getType();
 				       str += " body: " + smsData.getBody();
 				       str += " startTime: " + smsData.getStartTime();
 				       
				       GwtLog.d("====== MyMain testSMS ", str);
				    }
				}
			} while (mCurSms.moveToNext());
			
			//update lastest update date with the last record read
			if (lastItemFetchedDate != -1) {
			   lastTimeSMSRetrive = lastItemFetchedDate;
			}
			
			//debug
			if (GwtConfig.DEBUG) {
			   for (GSmsData smsData: smsList) {
				  GwtLog.d("--- SMS catched result ", smsData.getAddress() + "," + smsData.getDate() + ", " + smsData.getType() + "," + smsData.getRead() +"," + smsData.getBody());
			   }
			}
			
		} else {
			GwtLog.i("====== MyMain testSMS ", "no result");
		}
		mCurSms.close();
	}
	
	private long getFirstTime() {
		if (firstTime == -1) {
			firstTime = System.currentTimeMillis();
		}
		return firstTime;
	}
}
