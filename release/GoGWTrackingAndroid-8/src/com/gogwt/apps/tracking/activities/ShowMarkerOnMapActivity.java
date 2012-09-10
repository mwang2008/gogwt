package com.gogwt.apps.tracking.activities;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;

import com.gogwt.apps.tracking.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

//public class ShowLocationMapActivity extends Activity {
public class ShowMarkerOnMapActivity extends MapActivity {
	protected static final String TAG = ShowMarkerOnMapActivity.class.getSimpleName();	
	private MapView mapView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// We don't need a window title bar:
	    requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.show_marker_map_layout);
		
		//Toast.makeText(getApplicationContext(), "On ShowLocationMapActivity", Toast.LENGTH_LONG).show();
		
		double lat = getIntent().getDoubleExtra("lat", 33.331929);
		double lng = getIntent().getDoubleExtra("lng", -83.327283);
		int latE6 = (int)(lat*1e6);
		int lngE6 = (int)(lng*1e6);
		
		// setup map view
	    mapView = (MapView) findViewById(R.id.mapview);		 
		mapView.setBuiltInZoomControls(true);
		
		showMarkerOnMap(latE6, lngE6);
		


	}
	
	private void showMarkerOnMap(int latE6, int lngE6) {
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.google_green_arrow);
		DrawMarkerOverlay itemizedOverlay = new DrawMarkerOverlay(drawable, this);
		
		GeoPoint point = new GeoPoint(latE6, lngE6);
		OverlayItem overlayitem = new OverlayItem(point, "Location", "Latitude,Longtitude " + latE6 +","+lngE6);
		
 	/*	Drawable marker = getResources().getDrawable(R.drawable.google_green_arrow);
        marker.setBounds(0, 0, marker.getMinimumWidth(), marker.getMinimumHeight());
        overlayitem.setMarker(marker);*/
        
        itemizedOverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedOverlay);
        
        MapController mapController = mapView.getController();
        mapController.animateTo(point);
        mapController.setZoom(12);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
