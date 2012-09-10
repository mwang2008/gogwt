package com.gogwt.apps.tracking.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

import com.gogwt.apps.tracking.R;

public class HelpActivity extends Activity {
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    requestWindowFeature(Window.FEATURE_PROGRESS);

	    setContentView(R.layout.help);

	    WebView web = (WebView) findViewById(R.id.welcome_web);
	    web.loadUrl("file:///android_asset/help.html");

/*	    findViewById(R.id.welcome_read_later).setOnClickListener(
	        new OnClickListener() {
	          public void onClick(View v) {
	            finish();
	          }
	        });*/

/*	    findViewById(R.id.welcome_about).setOnClickListener(new OnClickListener() {
	      public void onClick(View v) {
	        about();
	      }
	    });*/
	  }
}
