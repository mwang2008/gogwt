package com.gogwt.apps.tracking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.gogwt.apps.tracking.activities.MainMenuActivity;


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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
		final Button btntomainpage = (Button) findViewById(R.id.tomainpage);
		btntomainpage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent toMainIntent = new Intent(getApplicationContext(),
						MainMenuActivity.class);
				// startActivityForResult(myIntent, 0);
				startActivity(toMainIntent); 
				 
			}
		});		
		 
		 
		final Button btn = (Button) findViewById(R.id.testbtn);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent toMainIntent = new Intent(getApplicationContext(),
						MyTestConfigActivity.class);
				// startActivityForResult(myIntent, 0);
				startActivity(toMainIntent); 
				 
			}
		});		
		 
		
	     
		final Button mybtn = new Button(this);
		mybtn.setText("my test");
	 
		mybtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent toMainIntent = new Intent(getApplicationContext(),
						MyTestConfigActivity.class);
				// startActivityForResult(myIntent, 0);
				startActivity(toMainIntent); 
				 
			}
		});		
		
		 
	}
	
	 

}
