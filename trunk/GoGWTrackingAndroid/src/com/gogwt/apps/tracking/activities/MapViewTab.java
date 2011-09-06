package com.gogwt.apps.tracking.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

//public class MapViewTab extends MapActivity {
public class MapViewTab extends Activity {	
	MapItemizedOverlay itemizedOverlay;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.tab_map_view_layout);
     
         
        TextView textview = new TextView(this);
        textview.setText("This is the MapViewTab tab");
        setContentView(textview);
         
        /*
		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
		itemizedOverlay = new MapItemizedOverlay(drawable, this);

		GeoPoint point = new GeoPoint(19240000, -99120000);
		OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!",
				"I'm in Mexico City!");

		itemizedOverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedOverlay);
        */
    }

    
    /*
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	*/

}
