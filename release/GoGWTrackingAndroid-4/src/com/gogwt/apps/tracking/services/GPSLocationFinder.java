package com.gogwt.apps.tracking.services;

import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;

public class GPSLocationFinder {
	private GPSLocationIF gpsLocationIF;
	private LocationManager locationManager;
	private long mLastLocationMillis;
	private Location mLastLocation;
	
	public GPSLocationFinder(Context context, GPSLocationIF gpsLocationIF) {
		if (locationManager == null) {
		   locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		}
		
		this.gpsLocationIF = gpsLocationIF;
	}

	public void getLocation(final boolean fromGPS, final boolean fromNetwork) throws Exception {
		String provider = getLocProvider(fromGPS, fromNetwork);
		if (provider == null) {		
			throw new Exception("GPS provider available");
		}
		
		sendCurrentLocation(provider);
		
	}
	
	private void sendCurrentLocation(String provider) throws Exception {
		// Define a listener that responds to location updates
      	LocationListener locationListener = new LocationListener() {
    	    public void onLocationChanged(Location location) {
    	    	if (location != null) {
    	    	    mLastLocationMillis = SystemClock.elapsedRealtime();
    	    	    mLastLocation = location;
    	    	}
    	    	gpsLocationIF.findGeocode(location); 
    	    	locationManager.removeUpdates(this);
    	    }

    	    public void onStatusChanged(String provider, int status, Bundle extras) {}

    	    public void onProviderEnabled(String provider) {}

    	    public void onProviderDisabled(String provider) {}
    	};
    	 
    	
    	locationManager.requestLocationUpdates(provider, 0, 0, locationListener); 
    	//locationManager.addGpsStatusListener(new GPSStatusListener()) ;
	}
	
	 
	private class GPSStatusListener implements GpsStatus.Listener {
	    public void onGpsStatusChanged(int event) {
	    	boolean isGPSFix = false;
	        switch (event) {
	            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
	               if (mLastLocation != null)
	                    isGPSFix = (SystemClock.elapsedRealtime() - mLastLocationMillis) < 3000;

	                if (isGPSFix) { // A fix has been acquired.
	                    // Do something.
	                } else { // The fix has been lost.
	                    // Do something.
	                }

	                break;
	            case GpsStatus.GPS_EVENT_FIRST_FIX:
	                // Do something.
	               isGPSFix = true;
	               
	                break;
	            case GpsStatus.GPS_EVENT_STARTED:
                    
                    break;
                case GpsStatus.GPS_EVENT_STOPPED:
                    
                    break;
	        }
	    }
	}
    
	
	private String getLocProvider(final boolean fromGPS, final boolean fromNetwork) {	 	
		if (fromGPS) {
		   if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
    		  return LocationManager.GPS_PROVIDER; 		
    	   }
		}
		
		if (fromNetwork) {
		   if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
    		  return LocationManager.NETWORK_PROVIDER;
    	   }
		}
		
		return null;
	}
 	 
}
