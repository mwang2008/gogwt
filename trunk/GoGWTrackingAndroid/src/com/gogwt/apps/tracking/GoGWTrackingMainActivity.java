package com.gogwt.apps.tracking;

import static com.gogwt.apps.tracking.GoGWTConstants.FROM_START_RECEIVER;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.gogwt.apps.tracking.activities.LocationTrackingActivity;
import com.gogwt.apps.tracking.activities.LoginActivity;
import com.gogwt.apps.tracking.activities.MainMenuActivity;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.utils.DatabaseHelper;
import com.gogwt.apps.tracking.utils.SessionManager;


/**
 * The very first activity
 * @author michael.wang
 *
 */
public class GoGWTrackingMainActivity extends Activity {
	protected static final String TAG = GoGWTrackingMainActivity.class.getSimpleName();
	//SharedPreferences userPreference;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
	 	
		final Handler callBack = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Profile profile = SessionManager.getProfile(getApplicationContext());
				//String savedGroupId = userPreference.getString(GROUP_ID, UNKNOWN);

				Intent redirectIntent = null;
				//if (!StringUtils.equalsIgnoreCase(savedGroupId, UNKNOWN)) {
				if (profile != null) {					
					boolean isAutoStart = getIntent().getBooleanExtra(FROM_START_RECEIVER, false);
					if (isAutoStart) {
					   redirectIntent = new Intent(getApplicationContext(), LocationTrackingActivity.class);						 
					}
					else {
					   redirectIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
					}
				}
				else {
					redirectIntent = new Intent(getApplicationContext(), LoginActivity.class);
				}
				startActivity(redirectIntent);
				
				//to remove from history stack
				finish();
			}
		};
		 
		
		initializeApplication(this, callBack);		 
	}
	
	private void initializeApplication(final Context context, final Handler callback) {
		new Thread() {
			public void run() {
				//init DB
				
				Log.i(this.getClass().getSimpleName(), "==== initializeApplication");
				
				// Initialize the database
				DatabaseHelper dbHelper = new DatabaseHelper(context);				
				try {
					SQLiteDatabase database = dbHelper.getWritableDatabase();
					//dbHelper.onCreate(database);
				} catch (Exception e) {
					Log.e(this.getClass().getSimpleName(), "====" + e.getMessage(), e);
				} finally {
					dbHelper.close();
				}
				
				Message msg = callback.obtainMessage();
				callback.sendMessage(msg);
			}
		}.start();
	}
	 

}
