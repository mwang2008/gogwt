package com.gogwt.apps.tracking;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class C2DMClientTestActivity extends Activity {
	public final static String AUTH = "authentication";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_c2dm);
    }
    
    public void register(View view) {
		Log.w("C2DM", "start registration process 1");
		Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
		intent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0));
		
		// Use registered Google email
		intent.putExtra("sender", "contact@gogwt.com");
		
		//Toast.makeText(this, "register", Toast.LENGTH_LONG).show();
		startService(intent);
	}

    public void unregister(View view) {
    	Intent unregIntent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
    	unregIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0));
    	startService(unregIntent);
    }
    
	public void showRegistrationId(View view) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String string = prefs.getString(AUTH, "n/a");
		Toast.makeText(this, string, Toast.LENGTH_LONG).show();
		Log.d("C2DM RegId", string);

	}
}