package com.gogwt.apps.tracking.processor;

import static com.gogwt.apps.tracking.GoGWTConstants.GOGWT_TAG;
import static com.gogwt.apps.tracking.GoGWTConstants.LOCATION1;
import static com.gogwt.apps.tracking.GoGWTConstants.LOCATION2;
import static com.gogwt.apps.tracking.GoGWTConstants.START_TRACK1;
import static com.gogwt.apps.tracking.GoGWTConstants.START_TRACK2;
import static com.gogwt.apps.tracking.GoGWTConstants.START_TRACK3;
import static com.gogwt.apps.tracking.GoGWTConstants.START_TRACK4;
import static com.gogwt.apps.tracking.GoGWTConstants.START_TRACKING1;
import static com.gogwt.apps.tracking.GoGWTConstants.START_TRACKING2;
import static com.gogwt.apps.tracking.GoGWTConstants.START_TRACKING3;
import static com.gogwt.apps.tracking.GoGWTConstants.START_TRACKING4;
import static com.gogwt.apps.tracking.GoGWTConstants.STOP_TRACK1;
import static com.gogwt.apps.tracking.GoGWTConstants.STOP_TRACK2;
import static com.gogwt.apps.tracking.GoGWTConstants.STOP_TRACK3;
import static com.gogwt.apps.tracking.GoGWTConstants.*;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.gogwt.apps.tracking.data.GSmsData;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.receiver.SmsReceiver;
import com.gogwt.apps.tracking.utils.GwtLog;
import com.gogwt.apps.tracking.utils.SessionManager;
import com.gogwt.apps.tracking.utils.StringUtils;

public class SmsProcessor {
	private static final String TAG = SmsProcessor.class.getSimpleName();
	private LocationManager locationManager;
	
    public void handleMsg(GSmsData smsData, Context context, boolean fromAdmin) {
    	PendingIntent pIntent = PendingIntent.getActivity(context, 
    			(int)System.currentTimeMillis(), new Intent(context, SmsReceiver.class), 0);
    	
    	
    	
		final String callerNumber = smsData.getAddress();
		String body = "";
		
		String signature = fromAdmin == true ? ADMIN : GOGWT_TAG;  
		
		GwtLog.d(TAG, "=== SmsProcessor handleMsg signature= "+signature);
		
		Profile profile = SessionManager.getProfile(context);
		
		//1. not login, return info back to caller.
		if (profile == null) {
			GwtLog.d(TAG, "=== sendSmsBackToSender The remote user is not logged in");
			SendSmsManager.getInstance().sendTextMessage(
					callerNumber, null, signature +" The remote user is not logged in", pIntent);
			return;
		}
		
		//2. caller is not account holder.
		if (!StringUtils.isPhoneMatched(profile.getServerPhone(), callerNumber)) {
			body = signature +" Fail to active GPS in the remote, as you are not the group owner.\n + " +
					"Remote user phone: " + profile.getPhoneNumber() + "\n" +
					"Group Id: " + profile.getGroupId()+ "\n" + 
					"Display Name: " + profile.getDisplayName()+ "\n";
			
			GwtLog.d(TAG, "=== sendSmsBackToSender: " + body + ", profile.getServerPhone()="+profile.getServerPhone() + ",callerNumber="+callerNumber);
			SendSmsManager.getInstance().sendTextMessage(callerNumber, null, body, pIntent);
			return;			
		}
		
		body = StringUtils.removeWhiteSpace(smsData.getBody().trim());
		
		//3. profile and call phone are the same, meaning caller wants to do something:  
		
		//3.1 check provider for GPS/WIFI 
		String provider = getLocProvider(context);
		if (provider == null) {
			GwtLog.d(TAG, "=== sendSmsBackToSender: Remote GPS/WIFI is disabled");
			SendSmsManager.getInstance().sendTextMessage(smsData.getAddress(), null, signature +" Remote GPS/WIFI is disabled", pIntent); 
			return;
		}
			
		//3.2 send location back to caller
		if (StringUtils.equalsIgnoreCase(LOCATION, body) ||
			StringUtils.equalsIgnoreCase(LOCATION1, body) ||
			StringUtils.equalsIgnoreCase(LOCATION2, body)) {
		   	sendCurrentLocation(smsData, provider, pIntent, fromAdmin);
		   	return;
		}
		
	 	//3.3 enable/start tracking 
		if (StringUtils.equalsIgnoreCase(START_TRACK, body) ||
			StringUtils.equalsIgnoreCase(START_TRACK1, body) || 
			StringUtils.equalsIgnoreCase(START_TRACK2, body) ||
			StringUtils.equalsIgnoreCase(START_TRACK3, body) ||
			StringUtils.equalsIgnoreCase(START_TRACK4, body) ||
			StringUtils.equalsIgnoreCase(START_TRACKING1, body) ||
			StringUtils.equalsIgnoreCase(START_TRACKING2, body) ||
			StringUtils.equalsIgnoreCase(START_TRACKING3, body) ||
			StringUtils.equalsIgnoreCase(START_TRACKING4, body) ) {
			
			if (!SessionManager.getGpxContext().isStartGPXService()) {	
			   Intent myIntent = new Intent(context, com.gogwt.apps.tracking.services.GPXService.class);
			   context.startService(myIntent);	
			   SessionManager.getGpxContext().setStartGPXService(true);
			  
			   body = signature +" Tracking is started, please <a href=\"http://www.gogwt.com/tracking\">login</a> to view the tracker";
			   GwtLog.d(TAG, "=== START_TRACK1 true  " + body);			   
			   SendSmsManager.getInstance().sendTextMessage(smsData.getAddress(), null, body, pIntent);
			}
			else {
			   body = signature +" Start tracking, please go to http://www.gogwt.com/tracking to view the tracker";
			   GwtLog.d(TAG, "=== START_TRACK1  false " + body);
			   SendSmsManager.getInstance().sendTextMessage(smsData.getAddress(), null, body , pIntent);
			}			
			return;
		}
					 
		//3.4 disable/stop tracking: may not stop the service if the server is stated with bind.
		if (StringUtils.equalsIgnoreCase(STOP_TRACK, body) ||
			StringUtils.equalsIgnoreCase(STOP_TRACK1, body) ||
			StringUtils.equalsIgnoreCase(STOP_TRACK2, body) ||
			StringUtils.equalsIgnoreCase(STOP_TRACK3, body) ||
			StringUtils.equalsIgnoreCase(STOP_TRACK4, body)) {
			if (SessionManager.getGpxContext().isStartGPXService()) {	
				Intent myIntent = new Intent(context, com.gogwt.apps.tracking.services.GPXService.class);
				context.stopService(myIntent);	
				SessionManager.getGpxContext().setStartGPXService(false);
				
				body = signature +" Stop tracking";
				GwtLog.d(TAG, "=== STOP_TRACK1  true " + body);
				SendSmsManager.getInstance().sendTextMessage(smsData.getAddress(), null, body , pIntent);
			}	
			else {
				body = GOGWT_TAG +" Tracking is stopped already";
				GwtLog.d(TAG, "=== STOP_TRACK1  true " + body);
				SendSmsManager.getInstance().sendTextMessage(smsData.getAddress(), null, body, pIntent);
			}
			return;
		}
	}
    
	private String getLocProvider(Context context) {
		String provider = null;
		if (locationManager == null) {
		    locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		}
		
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
    		provider =  LocationManager.GPS_PROVIDER; 		
    	}
    	else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
    		provider = LocationManager.NETWORK_PROVIDER;
    	}
		
		return provider;
	}
	
	private void sendCurrentLocation(final GSmsData smsData, final String provider, final PendingIntent pIntent, final boolean fromAdmin ) {
		GwtLog.d(TAG, "=== sendSmsBackToSender:onLocsendCurrentLocation ");
		
    	// Define a listener that responds to location updates
      	LocationListener locationListener = new LocationListener() {
    	    public void onLocationChanged(Location location) {
    	    	String signature = fromAdmin == true ? ADMIN : GOGWT_TAG;
    	    	
    	    	String loc = signature +" current location: http://maps.google.com/?q=" + ((int)(location.getLatitude()*1e6 + 0.5))/1e6+","+((int)location.getLongitude()*1e6 + 0.5)/1e6;
    	    	
    	    	GwtLog.d(TAG, "=== sendSmsBackToSender:onLocationChanged loc=" + loc);
    	    	sendLocationWithSms(loc, smsData.getAddress(), pIntent);
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
  
    private void sendLocationWithSms(String body, String number, final PendingIntent pIntent) {
     	GwtLog.d(TAG, "=== sendSmsBackToSender:sendLocationWithSms number="+number +", body=" + body);
    	SendSmsManager.getInstance().sendTextMessage(number, null, body, pIntent);
    	GwtLog.d(TAG, "=== here ddd");
    }
    
}
