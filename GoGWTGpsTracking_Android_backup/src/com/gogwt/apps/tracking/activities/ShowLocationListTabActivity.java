package com.gogwt.apps.tracking.activities;

import com.gogwt.apps.tracking.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ShowLocationListTabActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_location_list_tab);
	}
}
