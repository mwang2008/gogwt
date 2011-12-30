package com.gogwt.apps.tracking.activities;

import static com.gogwt.apps.tracking.GoGWTConstants.AUTO_START;
import static com.gogwt.apps.tracking.GoGWTConstants.INTERVAL;
import static com.gogwt.apps.tracking.GoGWTConstants.SEND_BODY;
import static com.gogwt.apps.tracking.GoGWTConstants.UNIT;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.text.InputType;

import com.gogwt.apps.tracking.R;
import com.gogwt.apps.tracking.utils.StringUtils;

public class SettingPrefsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
	protected static final String TAG = SettingPrefsActivity.class.getSimpleName();
	
	private SharedPreferences sharedPrefs;
 	private ListPreference perferredUnit;
 	private EditTextPreference serverInterval;
	private CheckBoxPreference autoStart;
	private CheckBoxPreference sendBody;
	private static String unitSummary;
	private static String sendIntervalSummary;
	private static String sendBodySummary; 
	private static String autoStartSummary;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//set default value define in xml file
		PreferenceManager.setDefaultValues(this, R.layout.preferences, false);
		addPreferencesFromResource(R.layout.preferences);
		
	 	setTitle(R.string.window_title_preferences);
		
		sharedPrefs = 
		    PreferenceManager.getDefaultSharedPreferences(this);
		
		sharedPrefs.registerOnSharedPreferenceChangeListener(this);
		
		initPreference(this);
 	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
	 
		//Editor edit = appPreference.edit();
		if (StringUtils.equalsIgnoreCase(key, UNIT)) {
			String unit = perferredUnit.getEntry().toString();
			perferredUnit.setSummary(unitSummary + unit);
 		}
		
		if (StringUtils.equalsIgnoreCase(key, INTERVAL)) {
			String keyVal = sharedPreferences.getString(key, "5");
	 		serverInterval.setSummary(sendIntervalSummary + keyVal);
		}
		
		if (StringUtils.equalsIgnoreCase(key, SEND_BODY)) {
			boolean sendBodyPre = sharedPreferences.getBoolean(key, false);
			
			if (sendBodyPre) {
				sendBody.setSummary(sendBodySummary + "[YES]");
			}
			else {
				sendBody.setSummary(sendBodySummary + "[NO]");
			}
		}
		if (StringUtils.equalsIgnoreCase(key, AUTO_START)) {
			boolean autoStartPre = sharedPreferences.getBoolean(key, false);
			
			if (autoStartPre) {
				autoStart.setSummary(autoStartSummary + "[YES]");
			}
			else {
				autoStart.setSummary(autoStartSummary + "[NO]");
			}
		}
 	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		sharedPrefs.unregisterOnSharedPreferenceChangeListener(this); 
 	}
	
	private void initPreference(Context context) {
	 	perferredUnit = (ListPreference)getPreferenceScreen().findPreference(UNIT);
		String unit = perferredUnit.getEntry().toString();
		unitSummary = perferredUnit.getSummary().toString();
		perferredUnit.setSummary(perferredUnit.getSummary() + unit);
		
	 	
		serverInterval = (EditTextPreference) getPreferenceScreen().findPreference(INTERVAL);
		serverInterval.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
		sendIntervalSummary = serverInterval.getSummary().toString();
		serverInterval.setSummary(serverInterval.getSummary() + serverInterval.getText());
		
		sendBody = (CheckBoxPreference)getPreferenceScreen().findPreference(SEND_BODY);
		sendBodySummary = sendBody.getSummary().toString();
		
		autoStart = (CheckBoxPreference)getPreferenceScreen().findPreference(AUTO_START);
		autoStartSummary = autoStart.getSummary().toString();		 
	 
	}
}
