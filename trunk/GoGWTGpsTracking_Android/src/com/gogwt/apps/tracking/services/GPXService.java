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
import com.gogwt.apps.tracking.utils.GwtLog;
import com.gogwt.apps.tracking.utils.NotifyMessageUtils;

/**
 * <pre>
 * telnet localhost 5554 
 * geo fix -83.22 33.44
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

	private LocationManager locationManager = null;
	
	public static final String GPX_SERVICE = "com.gogwt.apps.tracking.services.GPXService.SERVICE";
	
	private static final String TAG = GPXService.class.getSimpleName();
	public static final String GPS_EXTRA_UPDATE_RATE = "gps_update-rate";
	
	private static final int GPS_UPDATE_RATE_IN_SEC = 15;  //20s
	private static final int TIMER_UPDATE_RATE_IN_SEC = 30;
	
	//private List<GLocation> locationList = new ArrayList<GLocation>();
	private List<ICollectionListener> listeners = new ArrayList<ICollectionListener>();
	private int gpsUpdateRate;
	private long lastTime = -1;
	
	private Location firstLocation = null;
	private long firstTime = -1;
	private double totalDistance;
	private GLocation currentGlocation;
	private String groupId;
	private String startEnableGPSTime;
	private Location currentLocation;
	private Location lastLocation = null;
	
	private Timer timer;
	
	@Override
	public void onCreate() {
		super.onCreate();
		GwtLog.i(TAG, "==== onCreate");
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	 	
		int currentTimerRate = TIMER_UPDATE_RATE_IN_SEC;
		//timer = new Timer("GPXService-Timer");
		//timer.schedule(updateTask, 1000L, currentTimerRate * 1000L);

	}
	

	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
		Log.d(TAG, "==== onStart");

		initGPSProvider(intent);
 	}
	
 
    
    @Override
	public void onDestroy() {
		super.onDestroy();
		
		Log.d(TAG, "==== onDestroy");

		if (locationManager != null) {
			locationManager.removeUpdates(trackListener);
			locationManager = null;
		}

		 
		String endGPSTime = android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date()).toString();
			     
	    String info = "Tracking started at " + startEnableGPSTime;
	    info += " and stopped at + " + endGPSTime;
	    
		// notify that we've stopped
		NotifyMessageUtils.showNotifyMsg(getApplicationContext(),
				"GoGPS Tracking Stoped at " + endGPSTime, info);

		Toast.makeText(getApplicationContext(), "GPS Tracking - Tracking stopped " + info, Toast.LENGTH_SHORT).show();

		timer.cancel();
		timer = null;
	}
	
	@Override
	public IBinder onBind(Intent intent) {		 
		Log.d(TAG, "==== onBind");
		initGPSProvider(intent);
		return mRemoteInterfaceBinder;		 
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(TAG, "==== onUnbind");
		return super.onUnbind(intent);
	}
	
	/**
	 * 
	 */
	private TimerTask updateTask = new TimerTask() {
		@Override
		public void run() {
			
			try {
				GwtLog.d(TAG, "== Timer task doing work");
				//Toast.makeText(getApplicationContext(), "Timer:  " + TIMER_UPDATE_RATE_IN_SEC + " sec", Toast.LENGTH_SHORT).show();
				
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
				gpsUpdateRate = GPS_UPDATE_RATE_IN_SEC;   //20s
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
				locationManager.requestLocationUpdates(provider, gpsUpdateRate, 0,
						trackListener);
			}

			 	
			android.text.format.DateFormat df = new android.text.format.DateFormat();
			startEnableGPSTime = android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date()).toString();
			
			 
			String gpsProviderInfo = "Tracking start at " + gpsUpdateRate
					+ "s intervals with [" + provider + "] as the provider";
			
			NotifyMessageUtils.showNotifyMsg(getApplicationContext(),
					"GoGPS starts: " + startEnableGPSTime, gpsProviderInfo);
			
			Toast.makeText(getApplicationContext(), "GPS Tracking - Start ", Toast.LENGTH_SHORT).show();
	 }
	   
	/**
	 * remote interface
	 */
	private final IRemoteInterface.Stub mRemoteInterfaceBinder = new IRemoteInterface.Stub() {
		public Location getLastLocation() {
			GwtLog.d("interface", "===== getLastLocation() called");
			return lastLocation;
		}

		public GPXPoint getGPXPoint() {
			GwtLog.i("interface", "==== getGPXPoint() called");
			if (currentLocation == null) {
				return null;
			}
			
			return fromCurlocation();		 	
		}

		@Override
		public void addListener(ICollectionListener listener)
				throws RemoteException {
			//synchronized (listeners) {
				listeners.add(listener);
			//}
		}

		@Override
		public void removeListener(ICollectionListener listener)
				throws RemoteException {
			//synchronized (listeners) {
				listeners.remove(listener);
			//}
		}

		private GPXPoint fromCurlocation() {
			GPXPoint point = new GPXPoint();
			 
			point.latitude = (int)(currentLocation.getLatitude() * 1E6); //.getLatitude();
			point.longitude = (int)(currentLocation.getLongitude() * 1E6);
			point.altitude = currentLocation.getAltitude();
			point.provider = currentLocation.getProvider();
			point.accuracy = currentLocation.getAccuracy();
			point.bearing = currentLocation.getBearing();
			point.speed = currentLocation.getSpeed();
			
			
			if (lastLocation != null) {
				double distance = currentLocation.distanceTo(lastLocation);
			    point.distance = distance;		
			    totalDistance += distance;
			} 
			else {
				//first time
				firstTime = System.currentTimeMillis();
				totalDistance = 0;
			}
			
			point.startTime = firstTime;
			point.totalDistance = totalDistance;

			return point;
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

		/**
		 * Callback from GPS satellite
		 */
		@Override 
		public void onLocationChanged(Location location) {
			GwtLog.d(TAG, "=== onLocationChanged ");
	 		
			long thisTime = System.currentTimeMillis();
			long diffTime = thisTime - lastTime;
			
			/*
			if (diffTime < gpsUpdateRate) {
				// it hasn't been long enough yet
				return;
			}
			*/
			
			lastTime = thisTime;
			currentLocation = location;
			
			/*
			if (lastLocation != null) {
				double distance = location.distanceTo(lastLocation);
				totalDistance += distance;
			}
			*/
			
			
			//locInfo(location, diffTime);

			//lastLocation = location;
			
			/*
			if (firstLocation == null) {
				firstLocation = location;
				firstTime = thisTime;
				totalDistance = 0;
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
			gLocation.setStartTime(firstTime);
			gLocation.setTotalDistance(totalDistance);

			currentGlocation = gLocation;
			*/
			
			/*
			synchronized (locationList) {
				locationList.add(gLocation);
			}
            */
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
	

	
}
