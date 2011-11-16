package com.gogwt.apps.tracking.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TextViewTab extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the TextViewTab tab");
        setContentView(textview);
    }

}
