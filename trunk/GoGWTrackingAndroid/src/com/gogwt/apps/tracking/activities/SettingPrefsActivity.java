package com.gogwt.apps.tracking.activities;

import static com.gogwt.apps.tracking.GoGWTConstants.AUTO_START;
import static com.gogwt.apps.tracking.GoGWTConstants.INTERVAL;
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
	protected static final String TAG = SettingPrefsActivity.class
	.getSimpleName();
	
	private SharedPreferences sharedPrefs;
	private CheckBoxPreference enableUpdatePref;
	private EditTextPreference welcomeMmessage;
	private ListPreference perferredUnit;
	private EditTextPreference gpsInterval;
	private EditTextPreference serverInterval;
	private CheckBoxPreference autoStart;
	private static String unitSummary;
	private static String sendIntervalSummary;
	
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
			String keyVal = sharedPreferences.getString(key, "mi");
			String perferredUnitSummary = perferredUnit.getSummary().toString();
			String unit = perferredUnit.getEntry().toString();
			perferredUnit.setSummary(unitSummary + unit);
 		}
		
		if (StringUtils.equalsIgnoreCase(key, INTERVAL)) {
			String keyVal = sharedPreferences.getString(key, "5");
			
			//edit.putInt(INTERVAL, keyVal);
			serverInterval.setSummary(sendIntervalSummary + keyVal);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		sharedPrefs.unregisterOnSharedPreferenceChangeListener(this); 
 	}
	
	private void initPreference(Context context) {
		
		//enableUpdatePref = 	(CheckBoxPreference)getPreferenceScreen().findPreference("perform_updates");
		//welcomeMmessage = (EditTextPreference)getPreferenceScreen().findPreference("welcome_message");
	 
		perferredUnit = (ListPreference)getPreferenceScreen().findPreference(UNIT);
		String unit = perferredUnit.getEntry().toString();
		unitSummary = perferredUnit.getSummary().toString();
		perferredUnit.setSummary(perferredUnit.getSummary() + unit);
		
		
		//set number only
		/*
		gpsInterval = (EditTextPreference) getPreferenceScreen().findPreference("gps_interval");
		gpsInterval.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
		gpsInterval.setSummary(gpsInterval.getSummary() + gpsInterval.getText());
		*/
		
		serverInterval = (EditTextPreference) getPreferenceScreen().findPreference(INTERVAL);
		serverInterval.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
		sendIntervalSummary = serverInterval.getSummary().toString();
		serverInterval.setSummary(serverInterval.getSummary() + serverInterval.getText());
		
		
		/*
		autoStart = (CheckBoxPreference)getPreferenceScreen().findPreference("auto_start");
		autoStart.setChecked(false);
		*/
		autoStart = (CheckBoxPreference)getPreferenceScreen().findPreference(AUTO_START);
		 
		 
	 
	}
}
