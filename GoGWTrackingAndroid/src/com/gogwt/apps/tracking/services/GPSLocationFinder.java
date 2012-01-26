package com.gogwt.apps.tracking.services;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPSLocationFinder {
	private GPSLocationIF gpsLocationIF;
	private LocationManager locationManager;
	
	public GPSLocationFinder(Context context, GPSLocationIF gpsLocationIF) {
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		this.gpsLocationIF = gpsLocationIF;
	}

	public void getLocation() {
		String provider = getLocProvider();
		if (provider == null) {
		
			return;
		}
		
		sendCurrentLocation(provider);
		
	}
	
	private void sendCurrentLocation(String provider) {
		// Define a listener that responds to location updates
      	LocationListener locationListener = new LocationListener() {
    	    public void onLocationChanged(Location location) {
    	    	gpsLocationIF.findGeocode(location); 
    	    	locationManager.removeUpdates(this);
    	    }

    	    public void onStatusChanged(String provider, int status, Bundle extras) {}

    	    public void onProviderEnabled(String provider) {}

    	    public void onProviderDisabled(String provider) {}
    	};
    	    	
    	 
    	locationManager.requestLocationUpdates(provider, 0, 0, locationListener); 
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
}
