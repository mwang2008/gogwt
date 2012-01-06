package com.gogwt.apps.tracking.receiver;

import static com.gogwt.apps.tracking.GoGWTConstants.AUTO_START;
import static com.gogwt.apps.tracking.GoGWTConstants.INTERVAL;
import static com.gogwt.apps.tracking.GoGWTConstants.UNIT;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.gogwt.apps.tracking.activities.LocationTrackingActivity;
import com.gogwt.apps.tracking.activities.LogoutActivity;
import com.gogwt.apps.tracking.utils.GwtLog;
import com.gogwt.apps.tracking.utils.StringUtils;

/**
 * <B>StartupReceiver</B> Invoke when device is start. When autostart is enable,
 * start the tracking app.
 * 
 * @author michael.wang
 * 
 */
public class StartupReceiver extends BroadcastReceiver {
	protected static final String TAG = StartupReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		try {
			if (StringUtils.equalsIgnoreCase(action,
					Intent.ACTION_BOOT_COMPLETED)) {

				boolean autoStart = PreferenceManager
						.getDefaultSharedPreferences(context).getBoolean(AUTO_START, false);
				if (autoStart) {
					// start tracking by invoke LocationTrackingActivity
					context.startActivity(new Intent(context,
							LocationTrackingActivity.class));
				} else {
					Log.i(TAG,
							"Not starting tracking. Adjust the settings if you want to !");
				}
			} else {
				// this shouldn't happen !
				Log.w(TAG, "OpenGPSTracker's BootReceiver received " + action
						+ ", but it's only able to respond to "
						+ Intent.ACTION_BOOT_COMPLETED
						+ ". This shouldn't happen !");
			}
		} catch (Throwable e) {
            //not want to interrupt normal service, ignore exception.
		}
	}
}
