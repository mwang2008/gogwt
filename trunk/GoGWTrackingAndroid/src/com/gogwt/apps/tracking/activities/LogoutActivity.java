package com.gogwt.apps.tracking.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.gogwt.apps.tracking.R;
import com.gogwt.apps.tracking.utils.SharedPreferenceUtils;

public class LogoutActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		SharedPreferenceUtils.clearProfile(this);
		
		//process logout function and return back to login page
		Intent toLoginIntent = new Intent(getApplicationContext(), LoginActivity.class);        
        startActivity(toLoginIntent);
	}
}


 
