package com.gogwt.apps.tracking;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public abstract class AbstractMenuActivity extends Activity {
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_activity_layout);
	
		final TextView textViewDescription = (TextView) this.findViewById(R.id.text_view_description);
		textViewDescription.setText(getDescription());
		
		final ListView listViewMenu = (ListView) this.findViewById(R.id.list_view_menu_items);
		listViewMenu.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getMenuItems()));
		
		listViewMenu.setOnItemClickListener(getMenuOnItemClickListener());
		
		//TextView test = (TextView) findViewById(R.id.mytest_text);
		/*
		test.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				double latitude = 33.331929;
				double longitude = -83.327283;
				
				Intent toIntent = new Intent(getApplicationContext(), ShowLocationMapActivity.class);
				toIntent.putExtra("lat", latitude);
				toIntent.putExtra("lng", longitude);
				
				getApplicationContext().startActivity(toIntent);
			}
		});
		*/
	}
	
	
	//***************************************
    // Abstract methods
    //***************************************
	protected abstract String getDescription();
	
	protected abstract String[] getMenuItems();
	
	protected abstract OnItemClickListener getMenuOnItemClickListener();

}
