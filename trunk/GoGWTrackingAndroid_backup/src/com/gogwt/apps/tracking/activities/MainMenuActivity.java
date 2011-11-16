package com.gogwt.apps.tracking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.gogwt.apps.tracking.AbstractMenuActivity;
import com.gogwt.apps.tracking.R;
import com.gogwt.apps.tracking.services.GPXService;


public class MainMenuActivity extends AbstractMenuActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected String getDescription() {
		return getResources().getString(R.string.disclimaer);  //string.xml
	}

	@Override
	protected String[] getMenuItems() {
		return getResources().getStringArray(R.array.main_menu_items);  //string.xml
	}

	@Override
	protected OnItemClickListener getMenuOnItemClickListener() {
		return new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
				Class<?> cls = null;
				switch (position) {
				case 0:
					//start GPS tracking, then redirect to LocationTrackingActivity list view
					Intent service = new Intent(GPXService.GPX_SERVICE);
					service.putExtra(GPXService.EXTRA_UPDATE_RATE, 2000);
					startService(service);
					
					cls = LocationTrackingActivity.class;
					break;
 				case 1:
					cls = LogoutActivity.class;
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
	        case R.id.menu_icon:     
	        	Toast.makeText(this, "You pressed the icon!", Toast.LENGTH_LONG).show();
	            break;
	        case R.id.menu_map:     
	        	Toast.makeText(this, "You pressed the text!", Toast.LENGTH_LONG).show();
	            break;
	        case R.id.menu_logout: 
	        	startActivity(new Intent(this, LogoutActivity.class));	        	 
	            break;
	    }
	    return true;
	}
}
