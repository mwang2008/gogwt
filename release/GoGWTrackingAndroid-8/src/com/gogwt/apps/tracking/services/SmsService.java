package com.gogwt.apps.tracking.services;

import static com.gogwt.apps.tracking.GoGWTConstants.CONTENT_URI;
import static com.gogwt.apps.tracking.GoGWTConstants.*;
import static com.gogwt.apps.tracking.GoGWTConstants.START_TRACK1;
import static com.gogwt.apps.tracking.GoGWTConstants.START_TRACK2;
import static com.gogwt.apps.tracking.GoGWTConstants.START_TRACKING1;
import static com.gogwt.apps.tracking.GoGWTConstants.START_TRACKING2;
import static com.gogwt.apps.tracking.GoGWTConstants.STOP_TRACK1;
import static com.gogwt.apps.tracking.GoGWTConstants.STOP_TRACK2;

import java.util.ArrayList;
import java.util.Date;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.telephony.SmsManager;
import android.util.Log;

import com.gogwt.apps.tracking.data.GSmsData;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.utils.GwtLog;
import com.gogwt.apps.tracking.utils.SessionManager;
import com.gogwt.apps.tracking.utils.StringUtils;

/**
 * 
 * Sms is started with StartupReceiver or bind by LocationTrackingActivity
 * It is used for account holder to enable/disable GPS function.
 * 
 * 1) when sms message body is *location# send back GPS location
 * 2) when sms message body is *start track# or *startrack# or *start track# to start tracking 
 * 3) when sms message body is *stop track# or *stoptrack# to stop tracking 
 * 
 * @author michael.wang
 *
 */
public class SmsService extends Service {
	private static final String TAG = SmsService.class.getSimpleName();
	public static final String SERVICE = "com.gogwt.apps.tracking.services.SmsService.SERVICE";
    
	
	public static boolean isRunning = false;
	private long startTime;
	private long lastTimeSMSRetrive = -1;
	private LocationManager locationManager;
	private SmsManager smsManager;
	private PendingIntent pIntent;
	
	// Keeps track of all current registered clients.
	ArrayList<Messenger> mClients = new ArrayList<Messenger>();
	private SmsContentObserver smsContentObserver = null;
	
	public static final int MSG_REGISTER_CLIENT = 1;
	public static final int MSG_UNREGISTER_CLIENT = 2;
	public static final int MSG_SET_INT_VALUE = 3;
	public static final int MSG_SET_STRING_VALUE = 4;
	
	// Target we publish for clients to send messages to IncomingHandler.
	final Messenger mMessenger = new Messenger(new IncomingHandler());
	class IncomingHandler extends Handler { // Handler of incoming messages from
		// clients.
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_REGISTER_CLIENT:
				mClients.add(msg.replyTo);
				break;
			case MSG_UNREGISTER_CLIENT:
				mClients.remove(msg.replyTo);
				break;
			case MSG_SET_INT_VALUE:
				//incrementby = msg.arg1;
				GwtLog.i(TAG, " === from server int : " +msg.arg1);
				break;
			case MSG_SET_STRING_VALUE:
				String fromClient = msg.getData().getString("str1");
				GwtLog.i(TAG, " === from server String : " + fromClient);
				break;
			default:
				super.handleMessage(msg);
			}
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		try {
			GwtLog.i(TAG, "==== Service onCreate start.");
			registerContentObservers();
			startTime = System.currentTimeMillis();
			isRunning = true;
			
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			smsManager = SmsManager.getDefault();
			
			pIntent = PendingIntent.getActivity(this, (int)System.currentTimeMillis(), new Intent(getApplicationContext(), SmsService.class), 0);
			GwtLog.i(TAG, "==== Service onCreate end");
			
		} catch (Throwable e) {
			// ignore error
			e.printStackTrace();
			GwtLog.i(TAG, "== Service onCreate error " + e.getMessage());
		}
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);		 
		GwtLog.d(TAG, "==== onStart startId="+startId);		 
	}
	
	//@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.i(TAG, "==== Received start id " + startId + ": " + intent);
		// return Service.START_STICKY; // run until explicitly stopped.
		return 1;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			GwtLog.i(TAG, "== Service onDestroy.");
			unregisterContentObservers();
			isRunning = false;
			smsManager = null;
		} catch (Throwable e) {
			// ingore error
		}
	}

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	/**
	 * monitor the changes of content://sms
	 */
	private void registerContentObservers() {
		GwtLog.i(TAG, "registerContentObservers");

		if (smsContentObserver == null) {
			ContentResolver contentResolver = getContentResolver();
			smsContentObserver = new SmsContentObserver(new Handler());
			Uri smsUri = Uri.parse(CONTENT_URI);
			contentResolver.registerContentObserver(smsUri, true,
					smsContentObserver);

			lastTimeSMSRetrive = new Date().getTime(); // SessionManager.getGpxContext().getGpsStartTime().getTime();
		}
		GwtLog.i(TAG, "registerContentObservers after="+lastTimeSMSRetrive);
	}

	private void unregisterContentObservers() {
		Log.i(TAG, "unregisterContentObservers");
		if (smsContentObserver != null) {
			ContentResolver contentResolver = getContentResolver();
			contentResolver.unregisterContentObserver(smsContentObserver);
			smsContentObserver = null;
			lastTimeSMSRetrive = -1;
		}
	}

	/**
	 * Invoked whenever user send/receive sms
	 * 
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
			Log.i(TAG, "SmsContentObserver.onChange( " + selfChange + ")");

			// catch all exception, do not want SMS function to affect others.
			// as SMS function is undocument.
			try {
				chechSMS();
			} catch (Throwable e) {
				// skip, nothing
			}
		}

		@Override
		public boolean deliverSelfNotifications() {
			return true;
		}
	}
	
	/**
	 * filter on when sent event or read sms action. read [0:1] 0 -- new, 1 --
	 * read type [1:2] 1 -- receive(inbox), 2 -- send
	 */
	private void chechSMS() {
		final int TYPE_RECEIVE = 1;
		final int TYPE_SENT = 2;
		final int READ_READ = 1;
		final int READ_UNREAD = 0;

		ContentResolver contentResolver = getContentResolver();

		// type 1=inbox : 2=send
		// read 0=new : 1=read
		String[] projection = { "address", "date", "read", "type", "body" };
		StringBuilder whereBuf = new StringBuilder();		 
		whereBuf.append("type=" + TYPE_RECEIVE);

		/*
		 * whereBuf.append("(type="+TYPE_SENT); whereBuf.append(" or ");
		 * whereBuf.append("type="+TYPE_RECEIVE + ") and date > " +
		 * lastTimeSMSRetrive);
		 * 
		 * whereBuf.append(" (type="+TYPE_RECEIVE); whereBuf.append(" and ");
		 * whereBuf.append(" read="+READ_READ +")");
		 */

		Cursor mCurSms = contentResolver.query(Uri.parse(CONTENT_URI),
				projection, whereBuf.toString(), null, "DATE desc");

		// Cursor mCurSms =
		// contentResolver.query(Uri.parse(CONTENT_URI),null,null,null,"DATE desc");

		int addressCol = mCurSms.getColumnIndex("address");
		int dateCol = mCurSms.getColumnIndex("date");
		int readCol = mCurSms.getColumnIndex("read");
		int typeCol = mCurSms.getColumnIndex("type");
		int bodyCol = mCurSms.getColumnIndex("body"); // "body"
		mCurSms.moveToFirst();
		long currItemDate, lastItemFetchedDate = -1;
		if (mCurSms.getCount() > 0) {
			do {
				currItemDate = mCurSms.getLong(dateCol);
				if (currItemDate > lastTimeSMSRetrive) {
					GSmsData smsData = new GSmsData();

					smsData.address = mCurSms.getString(addressCol);
					smsData.date = new Date().getTime(); //new Date(currItemDate);
					smsData.read = mCurSms.getInt(readCol);
					smsData.type = mCurSms.getInt(typeCol);

					smsData.body = mCurSms.getString(bodyCol);

					smsData.startTime = startTime;

					// smsList.add(smsData);
					lastItemFetchedDate = currItemDate;
					
					sendSmsBackToSender(smsData);
	 				 
					 
					   String str = "=== address: " + smsData.getAddress();
					   str += " date: " + smsData.getDate() + " : ";
					   str +=  mCurSms.getLong(dateCol);
					   str += " read: " + smsData.getRead();
					   str += " type: " + smsData.getType();
					   str += " body: " + smsData.getBody();
					   str += " startTime: " + smsData.getStartTime();

					   GwtLog.d(TAG, str);
				     
					//seed back to client
					sendMessageBackToUI(str);		 
				}
			} while (mCurSms.moveToNext());

			// update latest update date with the last record read
			if (lastItemFetchedDate != -1) {
				lastTimeSMSRetrive = lastItemFetchedDate;
			}

		} else {
			GwtLog.i(TAG, "no result");
		}
		mCurSms.close();
	}
	
	private synchronized void sendSmsBackToSender(GSmsData smsData) {
		
		final String callerNumber = smsData.getAddress();
		String body = "";
		
		Profile profile = SessionManager.getProfile(getApplicationContext());
		
		//1. not login, return info back to caller.
		if (profile == null) {
			GwtLog.d(TAG, "=== sendSmsBackToSender The remote user is not logged in");
			smsManager.sendTextMessage(callerNumber, null, "The remote user is not logged in", pIntent, null);
			return;
		}
		
		//2. caller is not account holder.
		if (!StringUtils.isPhoneMatched(profile.getServerPhone(), callerNumber)) {
			body = "Fail to active GPS in the remote, as you are not the account holder.\n + " +
					"Remote user phone: " + profile.getPhoneNumber() + "\n" +
					"Group Id: " + profile.getGroupId()+ "\n" + 
					"Display Name: " + profile.getDisplayName()+ "\n";
			
			GwtLog.d(TAG, "=== sendSmsBackToSender: " + body + ", profile.getServerPhone()="+profile.getServerPhone() + ",callerNumber="+callerNumber);
			smsManager.sendTextMessage(callerNumber, null, body, pIntent, null);
			return;			
		}
		
		body = StringUtils.removeWhiteSpace(smsData.getBody());
		
		//3. profile and call phone are the same, meaning caller wants to do something:  
		
		//3.1 check provider for GPS/WIFI 
		String provider = getLocProvider();
		if (provider == null) {
			GwtLog.d(TAG, "=== sendSmsBackToSender: Remote GPS/WIFI is disabled");
			smsManager.sendTextMessage(smsData.getAddress(), null, "Remote GPS/WIFI is disabled", pIntent, null); 
			return;
		}
			
		//3.2 send location back to caller
		if (StringUtils.equalsIgnoreCase(body, LOCATION1) || 
				StringUtils.equalsIgnoreCase(body, LOCATION2)) {
		   	sendCurrentLocation(smsManager, smsData, provider);
		   	return;
		}
		
	 	//3.3 enable/start tracking 
		if (StringUtils.equalsIgnoreCase(START_TRACK1, body) || 
			StringUtils.equalsIgnoreCase(START_TRACK2, body) ||
			StringUtils.equalsIgnoreCase(START_TRACK3, body) ||
			StringUtils.equalsIgnoreCase(START_TRACK4, body) ||
			StringUtils.equalsIgnoreCase(START_TRACKING1, body) ||
			StringUtils.equalsIgnoreCase(START_TRACKING2, body) ||
			StringUtils.equalsIgnoreCase(START_TRACKING3, body) ||
			StringUtils.equalsIgnoreCase(START_TRACKING4, body) ) {
			if (!SessionManager.getGpxContext().isStartGPXService()) {	
			   Intent myIntent = new Intent(getApplicationContext(), com.gogwt.apps.tracking.services.GPXService.class);
			   getApplicationContext().startService(myIntent);	
			   SessionManager.getGpxContext().setStartGPXService(true);
			  
			   body = "Tracking is started, please <a href=\"http://www.gogwt.com/tracking\">login</a> to view the tracker";
			   GwtLog.d(TAG, "=== START_TRACK1 true  " + body);			   
			   smsManager.sendTextMessage(smsData.getAddress(), null, body, pIntent, null);
			}
			else {
			   body ="Start tracking, please <a href=\"http://www.gogwt.com/tracking\">login</a> to view the tracker";
			   GwtLog.d(TAG, "=== START_TRACK1  false " + body);
			   smsManager.sendTextMessage(smsData.getAddress(), null, body , pIntent, null);
			}			
			return;
		}
					 
		//3.4 disable/stop tracking: may not stop the service if the server is stated with bind.
		if (StringUtils.equalsIgnoreCase(STOP_TRACK1, body) ||
			StringUtils.equalsIgnoreCase(STOP_TRACK2, body) ||
			StringUtils.equalsIgnoreCase(STOP_TRACK3, body) ||
			StringUtils.equalsIgnoreCase(STOP_TRACK4, body)) {
			if (SessionManager.getGpxContext().isStartGPXService()) {	
				Intent myIntent = new Intent(getApplicationContext(), com.gogwt.apps.tracking.services.GPXService.class);
				getApplicationContext().stopService(myIntent);	
				SessionManager.getGpxContext().setStartGPXService(false);
				
				body = "Stop tracking";
				GwtLog.d(TAG, "=== STOP_TRACK1  true " + body);
				smsManager.sendTextMessage(smsData.getAddress(), null, body , pIntent, null);
			}	
			else {
				body = "Tracking is stopped already";
				GwtLog.d(TAG, "=== STOP_TRACK1  true " + body);
				smsManager.sendTextMessage(smsData.getAddress(), null, body, pIntent, null);
			}
			return;
		}
	}
	
	private String getLocProvider() {
		String provider = null;
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
    		provider =  LocationManager.GPS_PROVIDER; 		
    	}
    	else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
    		provider = LocationManager.NETWORK_PROVIDER;
    	}
		
		return provider;
	}

	private void sendCurrentLocation(final SmsManager sms, final GSmsData smsData, final String provider) {
		GwtLog.d(TAG, "=== sendSmsBackToSender:onLocsendCurrentLocation ");
		
    	// Define a listener that responds to location updates
      	LocationListener locationListener = new LocationListener() {
    	    public void onLocationChanged(Location location) {
    	    	String loc = "current location: http://maps.google.com/?q=" + location.getLatitude()+","+location.getLongitude();
    	    	
    	    	GwtLog.d(TAG, "=== sendSmsBackToSender:onLocationChanged loc=" + loc);
    	    	sendLocationWithSms(smsManager, loc, smsData.getAddress());
    	    	GwtLog.d(TAG, "=== after sendSmsBackToSender:onLocationChanged loc=" + loc);
    	    	//finish     	
    	    	locationManager.removeUpdates(this);
    	    }

    	    public void onStatusChanged(String provider, int status, Bundle extras) {}

    	    public void onProviderEnabled(String provider) {}

    	    public void onProviderDisabled(String provider) {}
    	};
    	    	
    	 
    	locationManager.requestLocationUpdates(provider, 0, 0, locationListener); 
    }
  
    

    private void sendLocationWithSms(SmsManager sms, String body, String number) {
    	int requestID = (int) System.currentTimeMillis();
    	final PendingIntent pIntent = PendingIntent.getActivity(this, requestID, new Intent(getApplicationContext(), SmsService.class), 0);    	
    	GwtLog.d(TAG, "=== sendSmsBackToSender:sendLocationWithSms number="+number +", body=" + body);
    	sms.sendTextMessage(number, null, body, pIntent, null);
    	GwtLog.d(TAG, "=== here ddd");
    }
    
	 
	
	private void sendMessageBackToUI(final String intvaluetosend) {
		for (int i = mClients.size() - 1; i >= 0; i--) {
			try {
			 
				// Send data as a String
				Bundle b = new Bundle();
				b.putString("str1", "ab" + intvaluetosend + "cd");
				//todo: use parcelable later on
				//b.putParcelable(key, value);
				//b.getParcelable(key);
				
				Message msg = Message.obtain(null, MSG_SET_STRING_VALUE);
				msg.setData(b);
				mClients.get(i).send(msg);

			} catch (RemoteException e) {
				// The client is dead. Remove it from the list; we are going
				// through the list from back to front so this is safe to do
				// inside the loop.
				mClients.remove(i);
			}
		}
	}
}
