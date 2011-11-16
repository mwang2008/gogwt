package com.gogwt.apps.tracking.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gogwt.apps.tracking.R;
import com.gogwt.apps.tracking.utils.SessionManager;

public class SaveTrackActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.save_track_layout);
		
		SessionManager.clearProfile(this);
		
		Bundle bundle = this.getIntent().getExtras();
		final ArrayList pgxPointList = bundle.getParcelableArrayList("locs");
		
		final Button saveBtn = (Button) findViewById(R.id.save);
		  saveBtn.setOnClickListener(new View.OnClickListener() {
			 public void onClick(View v) {
			 	//todo: save 
			 }
		});
		
		
		final Button cancelBtn = (Button) findViewById(R.id.save);
		   cancelBtn.setOnClickListener(new View.OnClickListener() {
			  public void onClick(View v) {
				 Intent toLoginIntent = new Intent(getApplicationContext(), LoginActivity.class);        
			     startActivity(toLoginIntent);		 
			  }
		});		
		
		//process logout function and return back to login page
		//Intent toLoginIntent = new Intent(getApplicationContext(), LoginActivity.class);        
        //startActivity(toLoginIntent);
	}
}
