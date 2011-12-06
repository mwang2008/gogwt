package com.gogwt.apps.tracking.receiver;

import static com.gogwt.apps.tracking.GoGWTConstants.AUTO_START;
import static com.gogwt.apps.tracking.GoGWTConstants.INTERVAL;
import static com.gogwt.apps.tracking.GoGWTConstants.UNIT;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gogwt.apps.tracking.utils.GwtLog;

/**
 * <B>StartupReceiver</B>
 * Invoke when device is start. 
 * When autostart is enable, start the tracking app.
 * @author michael.wang
 *
 */
public class StartupReceiver extends BroadcastReceiver {
	protected static final String TAG = 
		   StartupReceiver.class.getSimpleName();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		String keyVal = sharedPreferences.getString(UNIT, "unknown");
		String interval = sharedPreferences.getString(INTERVAL, "999");
		boolean autoStart = sharedPreferences.getBoolean(AUTO_START, false);
		GwtLog.d(TAG, "========||||||| **** StartupReceiver onReceive: keyVal="+keyVal);
		GwtLog.d(TAG, "========||||||| **** StartupReceiver onReceive: interval="+interval);
		GwtLog.d(TAG, "========||||||| **** StartupReceiver onReceive: autoStart="+autoStart);
		
		GwtLog.d(TAG, "========||||||| **** StartupReceiver onReceive: intent.getAction()="+intent.getAction());		    	

		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			
			
			GwtLog.d(TAG, "========||||||| **** Inside onReceive: intent.getAction()="+intent.getAction());
			
			}
		return;
	}
}
