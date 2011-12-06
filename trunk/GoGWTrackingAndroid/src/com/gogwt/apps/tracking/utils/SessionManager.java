package com.gogwt.apps.tracking.utils;

import android.content.Context;

import com.gogwt.apps.tracking.data.GPXContext;
import com.gogwt.apps.tracking.data.Profile;

public final class SessionManager {
	
    private static Profile profile;
    private static GPXContext gpxContext;
    
	public static Profile getProfile(Context context) {
		if (profile == null) {
			//check preference
			profile = SharedPreferenceUtils.retrieveProfile(context);
		}
		return profile;
	}

	public static String getGroupId(Context context) {
		Profile profile = getProfile(context);
		if (profile == null) {
			return null;
		}
		
		return profile.getGroupId();
	}
	
	public static void saveProfile(Context context, Profile profile) {
		//1. save profile in session
		SessionManager.profile = profile;
		
		//2. back up profile to preference
		if (profile != null && profile.isRememberMe()) {
		   SharedPreferenceUtils.savePreference(context, profile);
		}
	}
	
	public static void clearProfile(Context context) {
		profile = null;
		
		SharedPreferenceUtils.clearProfile(context);
	}

	public static GPXContext getGpxContext() {
		if (gpxContext == null) {
			gpxContext = new GPXContext();
		}
		return gpxContext;
	}
	

	 
	
	
}
