package com.gogwt.apps.tracking.receiver;

import static com.gogwt.apps.tracking.GoGWTConstants.AUTO_START;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.gogwt.apps.tracking.services.DummyService;
import com.gogwt.apps.tracking.services.SmsService;
import com.gogwt.apps.tracking.utils.GwtLog;
import com.gogwt.apps.tracking.utils.SessionManager;
import com.gogwt.apps.tracking.utils.StringUtils;

/**
 * <B>StartupReceiver</B> Invoke when device is start. When autostart is enable,
 * start the SmsService.
 * 
 * @author michael.wang
 * 
 */
public class StartupReceiver extends BroadcastReceiver {
	protected static final String TAG = StartupReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		GwtLog.d(TAG,	"==== onReceive action="+action);
		
		try {
			if (StringUtils.equalsIgnoreCase(action, Intent.ACTION_BOOT_COMPLETED)) {

				boolean autoStart = PreferenceManager
						.getDefaultSharedPreferences(context).getBoolean(AUTO_START, false);
				
				GwtLog.d(TAG,	"$$$$$==== onReceive autoStart="+autoStart);
				if (autoStart) {
					// start tracking by invoke LocationTrackingActivity
					/*
					Intent toIntent = new Intent(context, GoGWTrackingMainActivity.class);
					toIntent.putExtra(FROM_START_RECEIVER, true);
					context.startActivity(toIntent);
					*/
					GwtLog.d(TAG,	"==== onReceive isRunning="+SmsService.isRunning);
					//if (SmsService.isRunning == false) { 
					   //Intent myIntent = new Intent(context, SmsService.class);
					   //use DummyService to keep app live
					   Intent myIntent = new Intent(context, DummyService.class);
					   context.startService(myIntent);	
					   SessionManager.getGpxContext().setStartSmsService(true);
					   
					   GwtLog.d(TAG,	"==== onReceive after isRunning="+SmsService.isRunning);
					//}
				} else {
					GwtLog.d(TAG,	"Not starting tracking. Adjust the settings if you want to !");
				}
			} else {
				// this shouldn't happen !
				GwtLog.d(TAG, "==== onReceive OpenGPSTracker's BootReceiver received " + action
						+ ", but it's only able to respond to "
						+ Intent.ACTION_BOOT_COMPLETED
						+ ". This shouldn't happen !");
			}
		} catch (Throwable e) {
			GwtLog.d(TAG,	"==== onReceive error="+e.getMessage());
            //do not want to interrupt normal service, ignore exception.
		}
	}
}
