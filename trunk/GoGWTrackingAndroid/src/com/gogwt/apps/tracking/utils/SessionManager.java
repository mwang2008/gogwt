package com.gogwt.apps.tracking.utils;

import android.content.Context;

import com.gogwt.apps.tracking.data.Profile;

public final class SessionManager {
	
    private static Profile profile;

	public static Profile getProfile(Context context) {
		if (profile == null) {
			//check preference
			profile = SharedPreferenceUtils.retrieveProfile(context);
		}
		return profile;
	}

	public static void saveProfile(Context context, Profile profile) {
		//1. save profile in session
		SessionManager.profile = profile;
		
		//2. back up profile to preference
		SharedPreferenceUtils.savePreference(context, profile);
	}
}
