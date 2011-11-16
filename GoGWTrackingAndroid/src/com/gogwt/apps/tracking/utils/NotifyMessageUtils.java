package com.gogwt.apps.tracking.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.gogwt.apps.tracking.GoGWTrackingMainActivity;
import com.gogwt.apps.tracking.R;

/**
 * 
 * Usage: 
 * NotifyMessageUtils.showNotifyMsg(getApplicationContext(), "LocationListener", "onLocationChanged");
 * 
 *
 */
public final class NotifyMessageUtils {
	private static final int GPS_NOTIFY = 0x2001;
	private static  NotificationManager notifier = null; //(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);;
	
	public static void showNotifyMsg(Context context, String msgTitle, String msg) {
		showNotifyMsg(GoGWTrackingMainActivity.class, context, msgTitle, msg);
	}
	
	public static void showNotifyMsg(Class<?> cls, Context context, String msgTitle, String msg) {
		if (notifier == null) {
		   notifier = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		}
				 
		//Notification notify = new Notification(android.R.drawable.stat_notify_more, msgTitle, System.currentTimeMillis());
		Notification notify = new Notification(R.drawable.global_gwt, msgTitle, System.currentTimeMillis());
		//notify.flags |= Notification.FLAG_AUTO_CANCEL;
		Intent toLaunch = new Intent(context, cls);
		PendingIntent intentBack = PendingIntent.getActivity(
				context, 0, toLaunch, 0);

		 
		notify.setLatestEventInfo(context, msgTitle, msg, intentBack);
		
		notifier.notify(GPS_NOTIFY, notify);
	}
}
