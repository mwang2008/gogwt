package com.gogwt.apps.tracking.utils;

import static com.gogwt.apps.tracking.GoGWTConstants.GROUP_ID;
import static com.gogwt.apps.tracking.GoGWTConstants.PHONE_NUMBER;
import static com.gogwt.apps.tracking.GoGWTConstants.SERVER_EMAIL;
import static com.gogwt.apps.tracking.GoGWTConstants.SERVER_FIRST_NAME;
import static com.gogwt.apps.tracking.GoGWTConstants.SERVER_LAST_NAME;
import static com.gogwt.apps.tracking.GoGWTConstants.SERVER_USER_NAME;
import static com.gogwt.apps.tracking.GoGWTConstants.UNKNOWN;
import static com.gogwt.apps.tracking.GoGWTConstants.*;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.gogwt.apps.tracking.data.Profile;

public class SharedPreferenceUtils {
   
	public static String getGroupId(Context context) {
		SharedPreferences userPreference = context.getSharedPreferences(USER_PRFERENCE,
				Context.MODE_PRIVATE);
		String groupId = userPreference.getString(GROUP_ID, UNKNOWN);
		
		return groupId;
	}
	
	public static void savePreference(Context context, Profile profile) {
		SharedPreferences  userPreference = context.getSharedPreferences(USER_PRFERENCE, Context.MODE_PRIVATE);
		
		Editor edit = userPreference.edit();
		edit.clear();
		edit.putString(GROUP_ID, profile.getGroupId());
		edit.putString(DISPLAY_NAME, profile.getDisplayName());
		edit.putString(PHONE_NUMBER, profile.getPhoneNumber());		
		edit.putString(SERVER_USER_NAME, profile.getServerUsername());
		edit.putString(SERVER_FIRST_NAME, profile.getServerFirstName());
		edit.putString(SERVER_LAST_NAME, profile.getServerLastName());
		edit.putString(SERVER_EMAIL, profile.getServerEmail());
		edit.commit();
	}
	
	public static Profile retrieveProfile(Context context) {
		SharedPreferences userPreference = context.getSharedPreferences(USER_PRFERENCE, Context.MODE_PRIVATE);
		
		String savedGroupId = userPreference.getString(GROUP_ID, UNKNOWN);
		if (StringUtils.equalsIgnoreCase(savedGroupId, UNKNOWN)) {
			return null;
		}
		
		Profile profile = new Profile();
		
		profile.setGroupId(userPreference.getString(GROUP_ID, UNKNOWN));
		profile.setDisplayName(userPreference.getString(DISPLAY_NAME, UNKNOWN));
		profile.setPhoneNumber(userPreference.getString(PHONE_NUMBER, UNKNOWN));
		profile.setServerUsername(userPreference.getString(SERVER_USER_NAME, UNKNOWN));
		profile.setServerFirstName(userPreference.getString(SERVER_FIRST_NAME, UNKNOWN));
		profile.setServerLastName(userPreference.getString(SERVER_LAST_NAME, UNKNOWN));
		profile.setServerEmail(userPreference.getString(SERVER_EMAIL, UNKNOWN));
		
		return profile;
	}
	
	public static void clearProfile(Context context) {
		SharedPreferences  userPreference = context.getSharedPreferences(USER_PRFERENCE, Context.MODE_PRIVATE);
		
		Editor edit = userPreference.edit();
        edit.clear();
        edit.commit();
	}
}
