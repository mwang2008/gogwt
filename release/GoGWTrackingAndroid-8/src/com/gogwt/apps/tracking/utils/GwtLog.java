package com.gogwt.apps.tracking.utils;

import android.util.Log;

import com.gogwt.apps.tracking.GwtConfig;

public final class GwtLog {
	
	public static void i(String tag, String msg) {
		if (GwtConfig.DEBUG) {
		   Log.i(tag, msg);
		}
	}
	
	public static void i(String tag, String msg, boolean forceLog) {
		Log.i(tag, msg);		
	}

	public static void d(String tag, String msg) {
		if (GwtConfig.DEBUG) {
		   Log.i(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (GwtConfig.DEBUG) {
		   Log.e(tag, msg, tr);
		}
	}

	
	public static void d(String tag, String msg, boolean forceLog) {
		Log.d(tag, msg);		
	}
	
	public static void e(String tag, String msg, boolean forceLog) {
		Log.e(tag, msg);		
	}
}
