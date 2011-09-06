package com.gogwt.apps.tracking;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * 
 * @author michael.wang
 * @deprecated used only for testing
 */
public class MainActivity extends Activity {
	   public void onCreate(Bundle savedInstanceState) {
	    	requestWindowFeature(Window.FEATURE_NO_TITLE);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			
			
	    }
}
