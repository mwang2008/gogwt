package com.gogwt.apps.tracking.c2dm;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.gogwt.apps.tracking.R;

public class C2DMMessageReceivedActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.c2dm_activity_result);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String message = extras.getString("payload");
			if (message != null && message.length() > 0) {
				TextView view = (TextView) findViewById(R.id.result);
				view.setText(message);
			}
		}

		super.onCreate(savedInstanceState);
	}

}
