package com.gogwt.apps.tracking.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.gogwt.apps.tracking.data.GLocation;
import com.gogwt.apps.tracking.data.GPXPoint;
import com.gogwt.apps.tracking.data.ICollectionListener;
import com.gogwt.apps.tracking.data.IRemoteInterface;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.request.LocationRequest;
import com.gogwt.apps.tracking.data.response.LocationResponse;
import com.gogwt.apps.tracking.dataaccess.dao.LocationDAO;
import com.gogwt.apps.tracking.dataaccess.http.SendLocation;
import com.gogwt.apps.tracking.utils.NotifyMessageUtils;
import com.gogwt.apps.tracking.utils.SessionManager;
import com.gogwt.apps.tracking.utils.SharedPreferenceUtils;

/**
 * <pre>
 * telnet localhost 5554 
 * geo fix 22.33 33.44
 * </pre>
 * 
 * <pre>
 * call sequences: Step app start: 
 * 1) GoGPSTrackingActivity.onCreate 
 * 2) GoGPSTrackingActivity.onResume 
 * 3) GPXService.onCreate 
 * 4) GPXService.onBind 
 * 5) GoGPSTrackingActivity.onServiceConnected
 * </pre>
 * 
 * 
 * @author michael.wang
 * 
 */
public class GPXService extends Service {
	private static final int GPS_NOTIFY = 0x2001;
	private static final String TAG = GPXService.class.getSimpleName();

	// GPX_SERVICE matches AndroidManifest.xml
	public static final String GPX_SERVICE = "com.gogwt.apps.tracking.services.GPXService.SERVICE";
	public static final String EXTRA_UPDATE_RATE = "update-rate";

	private LocationManager locationManager = null;
	//private NotificationManager notifier = null;
	private int GPS_UPDATE_RATE_IN_SEC = 5;  //20s
    private int TIMER_UPDATE_RATE_IN_SEC = 10;  //20s
    private int MAX_TIMER_UPDATE_RATE_IN_SEC = 180;
    
	private Timer timer;
	private long startGPSTime;
	
	private List<GLocation> locationList = new ArrayList<GLocation>();
	private List<ICollectionListener> listeners = new ArrayList<ICollectionListener>();
	private long lastTime = -1;
	private long firstTime = -1;
	private int extractUpdateRate;
	private double totalDistance;
	
	private GLocation currentGlocation;
	private Location firstLocation = null;
	private Location lastLocation = null;

	private String startEnableGPSTime;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "==== onCreate");
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//notifier = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		currentTimerRate = TIMER_UPDATE_RATE_IN_SEC;
		timer = new Timer("GPXService-Timer");
		timer.schedule(new MyTimerTask(), 1000L, currentTimerRate * 1000L);

	}

	/**
	 * When user click Start Tracking
	 */
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
		Log.d(TAG, "==== onStart");

		extractUpdateRate = intent.getIntExtra(EXTRA_UPDATE_RATE, -1);
		if (extractUpdateRate == -1) {
			extractUpdateRate = GPS_UPDATE_RATE_IN_SEC;   //20s
		}

		String provider = null;
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			provider = LocationManager.GPS_PROVIDER;
		} else {			
			if (locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
				provider = LocationManager.NETWORK_PROVIDER;
				Toast.makeText(this, "Provider NETWORK_PROVIDER", 3000).show();
			} else {
				Toast.makeText(this, "Provider Not available", 3000 ).show();
			}
		}

		if (provider != null) {
			locationManager.removeUpdates(trackListener);
			locationManager.requestLocationUpdates(provider, extractUpdateRate, 0,
					trackListener);
		}

		 	
		android.text.format.DateFormat df = new android.text.format.DateFormat();
		startEnableGPSTime = android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date()).toString();
		
		 
		String gpsProviderInfo = "Tracking start at " + extractUpdateRate/1000
				+ "s intervals with [" + provider + "] as the provider at " + startEnableGPSTime;
		
		NotifyMessageUtils.showNotifyMsg(getApplicationContext(),
				"GoGPS starts: " + startEnableGPSTime, gpsProviderInfo);
	}

	/**
	 * Called by LocationTrackingActivity "Stop Tracking" tab
	 */
	@Override
	public void onDestroy() {

		Log.d(TAG, "==== onDestroy");

		if (locationManager != null) {
			locationManager.removeUpdates(trackListener);
			locationManager = null;
		}

		timer.cancel();
		timer = null;

		String endGPSTime = android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date()).toString();
		
	     
	    String info = "Tracking started at " + startEnableGPSTime;
	    info += " and stopped at + " + endGPSTime;
	    
		// notify that we've stopped
		NotifyMessageUtils.showNotifyMsg(getApplicationContext(),
				"GoGPS Tracking Stoped at " + endGPSTime, info);

		//Toast.makeText(getApplicationContext(), "GPS Tracking - Tracking stopped", Toast.LENGTH_SHORT).show();

		super.onDestroy();
	}

	/**
	 * Called by MainMenuActivity “Start Tracking” List Item
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		Log.d(TAG, "==== onBind");
		startGPSTime = System.currentTimeMillis();
		// we only have one, so no need to check the intent
		return mRemoteInterfaceBinder;
	}

	/**
	 * remote interface
	 */
	private final IRemoteInterface.Stub mRemoteInterfaceBinder = new IRemoteInterface.Stub() {
		public Location getLastLocation() {
			Log.d("interface", "===== getLastLocation() called");
			return lastLocation;
		}

		public GPXPoint getGPXPoint() {
			Log.i("interface", "==== getGPXPoint() called");
			if (lastLocation == null) {
				return null;
			} else {
				Log.d("interface", "getGPXPoint() called");
				GPXPoint point = convertPoint();
				return point;
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

		private GPXPoint convertPoint() {
			GPXPoint point = new GPXPoint();

			point.latitude = currentGlocation.getLatitude();
			point.longitude = currentGlocation.getLongitude();
			point.altitude = currentGlocation.getAltitude();
			point.provider = currentGlocation.getProvider();
			point.accuracy = currentGlocation.getAccuracy();
			point.bearing = currentGlocation.getBearing();
			point.speed = currentGlocation.getSpeed();
			point.distance = currentGlocation.getDistance();
			point.startTime = currentGlocation.getStartTime();
			point.totalDistance = currentGlocation.getTotalDistance();

			return point;
		}
	};

	/**
	 * 
	 */
	private LocationListener trackListener = new LocationListener() {
		String groupId;

		public void onLocationChanged(Location location) {
			Log.d(TAG, "=== onLocationChanged");

			if (currentTimerRate >= TIMER_UPDATE_RATE_IN_SEC) {
			   currentTimerRate = TIMER_UPDATE_RATE_IN_SEC;
			   
			   try {
			      timer.cancel();
	              timer.purge();
			   }
			   catch (Throwable e) {
				   e.printStackTrace();
			   }
			   
	           timer = new Timer("GPXService-Timer");
			   timer.schedule(new MyTimerTask(), 1000L, currentTimerRate * 1000L);
			}
			
			if (groupId == null) {
				groupId = SharedPreferenceUtils
						.getGroupId(getApplicationContext());
			}


			NotifyMessageUtils.showNotifyMsg(getApplicationContext(),
					"LocationListener", "onLocationChanged");

			Toast.makeText(getApplicationContext(),
					"LocationListener-->onLocationChanged", Toast.LENGTH_SHORT)
					.show();

			long thisTime = System.currentTimeMillis();
			long diffTime = thisTime - lastTime;

			Log.d(TAG, "diffTime == " + diffTime + ", updateRate = " + extractUpdateRate);

			if (diffTime < extractUpdateRate) {
				// it hasn't been long enough yet
				return;
			}

			lastTime = thisTime;
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

			if (firstLocation != null && firstTime != -1) {
				float overallDistance = location.distanceTo(firstLocation);
				float overallSpeed = overallDistance / (thisTime - firstTime);
				locInfo += String.format(
						"\n\tOverall speed: %.1fm/s over %.1f meters",
						overallSpeed, overallDistance);
			}

			lastLocation = location;
			if (firstLocation == null) {
				firstLocation = location;
				firstTime = thisTime;
				totalDistance = 0;
			}

			Toast.makeText(getApplicationContext(), locInfo, Toast.LENGTH_LONG)
					.show();
			Log.d(TAG, "=== locInfo=" + locInfo);

			totalDistance += distance;

			// write to database
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
			gLocation.setStartTime(firstTime);
			gLocation.setTotalDistance(totalDistance);

			currentGlocation = gLocation;

			synchronized (locationList) {
				locationList.add(gLocation);
			}

			synchronized (listeners) {
				for (ICollectionListener listener : listeners) {
					try {
						//Note: listener.updateLocation(point) is not working as
						// point bind to different thread
						
						listener.handleLocationUpdated();
					} catch (RemoteException e) {
						Log.w(TAG, "Failed to notify listener " + listener, e);
					}
				}
			}

			// save to db
			LocationDAO.getInstance(getApplicationContext()).insertLocation(
					gLocation);

			Log.d(TAG, "=== finish insert to db=" + gLocation.toString());
		}

		public void onProviderDisabled(String provider) {
			Log.d(TAG, "=== onProviderDisabled disabled " + provider);
			Toast.makeText(getApplicationContext(), "GPS Disabled",
					Toast.LENGTH_SHORT).show();
		}

		public void onProviderEnabled(String provider) {
			Log.d(TAG, "=== onProviderEnabled enabled " + provider);
			Toast.makeText(getApplicationContext(), "GPS Enabled",
					Toast.LENGTH_SHORT).show();
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			Log.d(TAG, "=== onStatusChanged enabled " + provider);
			Toast.makeText(getApplicationContext(),
					"GPS Status Change:" + status, Toast.LENGTH_SHORT).show();
		}
	};

	/**
	 * Send locations back to server
	 */
	private int numIdle=0;
	private long TIMER_IDLE_RATE [] = {30, 60, 120, 180, 240, 300};
	private long currentTimerRate;
	
	//private TimerTask timerUpdateTask = new TimerTask() {
	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			Log.d(TAG, "Timer task doing work");
			try {
				Log.d(TAG, "====== Timer to send location");
				final SendLocation sendLocation = new SendLocation();
				// http request
				sendLocation.setContext(getApplicationContext());
				
				Profile profile = SessionManager.getProfile(getApplicationContext());
				
				LocationRequest request = new LocationRequest();
	 			List<GLocation> mylocationList = new ArrayList<GLocation>();
				synchronized (locationList) {
					mylocationList.addAll(locationList);
					locationList.clear();
				}
				
				//debug
				//if (Log.isLoggable(TAG, Log.DEBUG)) {
					if (mylocationList == null || mylocationList.isEmpty()) {
						Log.d(TAG, "---$$$ mylocationList is null");
					}
					else {
						int i=0;
						for (GLocation theLocation : mylocationList) {
							Log.d(TAG, "---$$$ i=" + i++ + ", " + theLocation.toString());
						}
					}
				//}
				
				if (mylocationList == null || mylocationList.isEmpty()) {
					numIdle++;
					if (numIdle>150) {
						numIdle = 150;
					}
					
					long period =  TIMER_IDLE_RATE[numIdle/20] ;
					timer.cancel();
		            timer.purge();

					timer.schedule(new MyTimerTask(), 1000L, period * 1000L);
				}
				request.setLocations(mylocationList);
				
				request.setProfile(profile);
				
				// todo process result later
				LocationResponse result = sendLocation.httpPost(request);

			} catch (Throwable t) {
				Log.e(TAG, "Failed to send results", t);
			}
		}
	};

}
