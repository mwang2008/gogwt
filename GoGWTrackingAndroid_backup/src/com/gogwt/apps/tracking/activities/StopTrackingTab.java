package com.gogwt.apps.tracking.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 
 * @author michael.wang
 *
 * @deprecated
 */
public class StopTrackingTab extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the StopTrackingTab tab");
        setContentView(textview);
        
        Intent intent = new Intent().setClass(this, MainMenuActivity.class);
        startActivity(intent);        
    }

}
