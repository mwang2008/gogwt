package com.gogwt.apps.tracking.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.gogwt.apps.tracking.activities.LocationTrackingActivity;

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
		if (notifier == null) {
		   notifier = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		}
		
		Notification notify = new Notification(
				android.R.drawable.stat_notify_more, msgTitle,
				System.currentTimeMillis());
		//notify.flags |= Notification.FLAG_AUTO_CANCEL;
		Intent toLaunch = new Intent(context,
				LocationTrackingActivity.class);
		PendingIntent intentBack = PendingIntent.getActivity(
				context, 0, toLaunch, 0);

		 
		notify.setLatestEventInfo(context, msgTitle, msg, intentBack);
		
		notifier.notify(GPS_NOTIFY, notify);
	}
}
