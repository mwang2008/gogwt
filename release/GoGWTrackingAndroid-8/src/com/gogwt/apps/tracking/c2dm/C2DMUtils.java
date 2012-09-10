package com.gogwt.apps.tracking.c2dm;

import com.gogwt.apps.tracking.utils.StringUtils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class C2DMUtils {
	public final static String AUTH = "authentication";
	
	public final static String SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
	public final static String ACCOUNT_MISSING = "ACCOUNT_MISSING";
	public final static String AUTHENTICATION_FAILED = "AUTHENTICATION_FAILED";
	public final static String TOO_MANY_REGISTRATIONS = "TOO_MANY_REGISTRATIONS";
	public final static String INVALID_SENDER = "INVALID_SENDER";
	public final static String PHONE_REGISTRATION_ERROR = "PHONE_REGISTRATION_ERROR";
	
	private C2DMUtils() {}
	
	/**
	 * Register Google
	 * Step 1: sending out register
	 * Step 2: receive register response C2DMRegistrationReceiver.onReceive
	 *         Step 2.1 send registrationId to app server gogwt.com/tracking/en-us/c2mdreg
	 *         Step 2.2 save registrationId to profile.
	 * @param context
	 */
	public static void register(Context context) {
		Log.w("C2DM", "start register");
		Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
		intent.putExtra("app",
				PendingIntent.getBroadcast(context, 0, new Intent(), 0));

		// Use registered Google email: SENDER_ID
		intent.putExtra("sender", "contact@gogwt.com");

		// Toast.makeText(this, "register", Toast.LENGTH_LONG).show();
		context.startService(intent);
	}

	public static void unregister(Context context) {
		Log.w("C2DM", "start unregister");
		 
		Intent unregIntent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
		unregIntent.putExtra("app",	PendingIntent.getBroadcast(context, 0, new Intent(), 0));
		context.startService(unregIntent);
		 
	}
	
	public static String getRegistrationId(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getString(AUTH, "n/a");
	}
	
	public static void saveRegistrationId(Context context, String registrationId) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Editor edit = prefs.edit();
		edit.putString(C2DMUtils.AUTH, registrationId);
		edit.commit();
	}
	
	public static void removeRegistrationId(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Editor edit = prefs.edit();
		edit.remove(C2DMUtils.AUTH);
		edit.commit();
	}
	
	public static boolean hasRegistrationId(Context context) {
		String regId = getRegistrationId(context);
		if (!StringUtils.isSet(regId)) {
			return false;
		}
		if (StringUtils.equalsIgnoreCase(regId, "n/a")) {
			return false;
		}
		
		return true;
	}
	

}
