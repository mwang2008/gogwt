package com.gogwt.apps.tracking.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.gogwt.apps.tracking.AbstractMenuActivity;
import com.gogwt.apps.tracking.R;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.utils.SessionManager;
import com.gogwt.apps.tracking.utils.StringUtils;

public class MainMenuActivity extends AbstractMenuActivity {
	protected static final String TAG = MainMenuActivity.class.getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		Profile profile = SessionManager.getProfile(getApplicationContext());
		if (profile != null) {
			setTitle(profile.getGroupId() + " : " + profile.getDisplayName());
		}
		
		//check GPS
		LocationManager locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			createGpsDisabledAlert();
		}
 	}
	
	@Override
	protected String getDescription() {
		return getResources().getString(R.string.disclimaer);  //string.xml
	}

	@Override
	protected String[] getMenuItems() {
		
		String [] strArr = getResources().getStringArray(R.array.main_menu_items);  //string.xml
		List<String> strings = 
		     new ArrayList<String>(Arrays.asList(strArr));
		
		//if this device is the same number as account holder
		Profile profile = SessionManager.getProfile(getApplicationContext());
		if (profile != null && StringUtils.isPhoneMatched(profile.getPhoneNumber(), profile.getServerPhone())) {
			strings.add("Remote Control:\n get current location \n start/stop tracking");
		}
		 
		String[] finalArr = strings.toArray(new String[strings.size()]);

		//return getResources().getStringArray(R.array.main_menu_items);  //string.xml
		return finalArr;
	}

	@Override
	protected OnItemClickListener getMenuOnItemClickListener() {
		return new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
				Class<?> cls = null;
				switch (position) {
				case 0:			 		
					cls = LocationTrackingActivity.class;					
					break;			 
 				case 1:
					cls = LogoutActivity.class;
					break;
				case 2:
					cls = AdminInvokeActivity.class;
					break;
				default:
					break;
				}
				startActivity(new Intent(parentView.getContext(), cls));
			}
		};
	}
	
	@Override  
	public boolean onCreateOptionsMenu(Menu menu) {  
	   
		 MenuInflater inflater = getMenuInflater();
		 inflater.inflate(R.menu.main_menu, menu);
		 
		 return true;
	}  

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_setting:     
	        	//Toast.makeText(this, "You pressed the setting!", Toast.LENGTH_LONG).show();
	        	startActivity(new Intent(this, SettingPrefsActivity.class));
	            break;
	        case R.id.menu_help:     
	            startActivity(new Intent(this, HelpActivity.class));	        	
	            break;
	        case R.id.menu_logout: 	        	
	        	startActivity(new Intent(this, LogoutActivity.class));	     
	            break;
	    }
	    return true;
	}
	
	private void createGpsDisabledAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Your GPS is disabled! Would you like to enable it?")
				.setCancelable(false);
		
		builder.setPositiveButton("Enable GPS",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						showGpsOptions();
					}
				});
		builder.setNegativeButton("Do nothing",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void showGpsOptions() {
		Intent gpsOptionsIntent = new Intent(
				android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(gpsOptionsIntent);
	}

}
