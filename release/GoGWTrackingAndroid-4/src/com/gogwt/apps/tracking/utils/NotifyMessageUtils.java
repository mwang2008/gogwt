package com.gogwt.apps.tracking.utils;

import java.util.Random;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

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
	
	/**
	 * 
	 * @param context
	 * @param msgTitle
	 * @param msg
	 * @deprecated  
	 */
	public static void showNotifyMsg(Context context, String msgTitle, String msg) {
		showNotifyMsgXXX(GoGWTrackingMainActivity.class, context, msgTitle, msg);
		//showNotifyMsg(LocationTrackingActivity.class, context, msgTitle, msg);
	}
	
 	public static void showNotifyMsgXXX(Class<?> cls, Context context, String msgTitle, String msg) {
		if (notifier == null) {
		   notifier = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		}
				 
		Notification notify = new Notification(R.drawable.global_gwt, msgTitle, System.currentTimeMillis());
		//notify.flags |= Notification.FLAG_AUTO_CANCEL;
		Intent toLaunch = new Intent(context, cls);
		//toLaunch.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		PendingIntent intentBack = PendingIntent.getActivity(
				context, 0, toLaunch, PendingIntent.FLAG_UPDATE_CURRENT);

		 
		notify.setLatestEventInfo(context, msgTitle, msg, intentBack);
		
		notifier.notify(GPS_NOTIFY, notify);
	}
 	
 	
 	public static void showNotifyMsgOnceTime(Class<?> cls, Context context, String msgTitle, String msg) {
		if (notifier == null) {
		   notifier = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		}
				 
		Notification notify = new Notification(R.drawable.global_gwt, msgTitle, System.currentTimeMillis());
		notify.flags |= Notification.FLAG_AUTO_CANCEL;
		
		Intent toLaunch = new Intent(context, cls);
		toLaunch.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		PendingIntent intentBack = PendingIntent.getActivity(
				context, 0, toLaunch, PendingIntent.FLAG_UPDATE_CURRENT);

		 
		notify.setLatestEventInfo(context, msgTitle, msg, intentBack);
		
		notifier.notify(GPS_NOTIFY, notify);
	}
 	
 	public static void showNotifyMsgWithResume(Class<?> cls, Context context, String msgTitle, String msg) {
		if (notifier == null) {
		   notifier = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		}
				 
		Notification notify = new Notification(R.drawable.global_gwt, msgTitle, System.currentTimeMillis());
		//notify.flags |= Notification.FLAG_AUTO_CANCEL;
		Intent toLaunch = new Intent(context, cls);
		toLaunch.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		PendingIntent intentBack = PendingIntent.getActivity(
				context, 0, toLaunch, PendingIntent.FLAG_UPDATE_CURRENT);

		 
		notify.setLatestEventInfo(context, msgTitle, msg, intentBack);
		
		notifier.notify(GPS_NOTIFY, notify);
	}
 	
 	 
 	public static void sendCustomNotificationWithOnceTime (Class<?> cls,  Context context, String title, String message) {       
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);;

         
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification_layout);
        contentView.setImageViewResource(R.id.image, R.drawable.global_gwt);
        contentView.setTextViewText(R.id.title, title);
        contentView.setTextViewText(R.id.text, message);

        CharSequence tickerText = message;
        long when = System.currentTimeMillis();

        Notification notification = new Notification(R.drawable.global_gwt, tickerText, when);
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        Intent notificationIntent = new Intent(context, cls);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        notification.contentIntent = contentIntent;
        notification.contentView = contentView;
        
        //Random randInt = new Random();
        //int id = randInt.nextInt(100) - 1;
        
        mNotificationManager.notify(GPS_NOTIFY, notification);
     }
     
}
