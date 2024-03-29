package com.gogwt.apps.tracking.activities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

import com.gogwt.apps.tracking.R;
import com.gogwt.apps.tracking.data.GPXPoint;
import com.gogwt.apps.tracking.data.ICollectionListener;
import com.gogwt.apps.tracking.data.IRemoteInterface;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.dataaccess.http.StopTracking;
import com.gogwt.apps.tracking.services.GPXService;
import com.gogwt.apps.tracking.utils.SessionManager;
import com.gogwt.apps.tracking.utils.StringUtils;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class LocationTrackingActivity extends MapActivity implements
		OnTabChangeListener {
	protected static final String TAG = LocationTrackingActivity.class
			.getSimpleName();

	private static final String LIST_TAB_TAG = "List";
	private static final String MAP_TAB_TAG = "Map";
	private static final String STOP_TRACKING_TAB_TAG = "Stop Tracking";

	private String currentTabId = LIST_TAB_TAG;
	private TabHost tabHost;
	private ListView listView;
	
	private MapView mapView;
	private MapController mapController;
	
	private TextView stopTrackingview;
	private MapItemizedOverlay itemizedOverlay;
	private TextView speedinfoView;
	
	IRemoteInterface mRemoteInterface = null;
	private Handler handler;
	
    private List<GPXPoint> pgxPointList = new ArrayList<GPXPoint>();
    
    private GeoPoint lastPoint = null;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "***** onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tracking_location_tab_layout);

		handler = new Handler();

		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(); // required if using findViewById
		tabHost.setOnTabChangedListener(this);

		// setup list view
		listView = (ListView) findViewById(R.id.list);
		listView.setEmptyView((TextView) findViewById(R.id.empty));

		// setup map view
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setSatellite(false);
		mapView.setStreetView(false);
		
		mapView.setBuiltInZoomControls(true);
		mapView.postInvalidate();
		mapController = mapView.getController();
		mapController.setZoom(4); // Zoom 1 is world view

		speedinfoView = (TextView) findViewById(R.id.speedinfo);
		
		// stop tracking
		stopTrackingview = new TextView(this);

		// add views to tab host
		tabHost.addTab(tabHost.newTabSpec(LIST_TAB_TAG)
				.setIndicator("List View").setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {
						return listView;
					}
				}));

		tabHost.addTab(tabHost.newTabSpec(MAP_TAB_TAG).setIndicator("Map View")
				.setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {
						return mapView;
					}
				}));

		tabHost.addTab(tabHost.newTabSpec(STOP_TRACKING_TAB_TAG)
				.setIndicator("Stop Tracking")
				.setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {
						return stopTrackingview;
					}
				}));

		// todo: need to config out why map show first
		tabHost.setCurrentTab(1);
		tabHost.setCurrentTab(0);

	}

	@Override
	protected void onResume() {
		Log.i(TAG, "**** onResume");

		//String remoteName = IRemoteInterface.class.getName(); //working also.
		String remoteName = GPXService.GPX_SERVICE;
		Intent intent = new Intent(remoteName);

		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.i(TAG, "***** onPause");
		// remove the link to the remote service
		try {
			unbindService(serviceConnection);
		} catch (Throwable e) {
			Log.w(TAG, "Failed to unbind ", e);
		}

		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "***** onDestroy");

		try {			
			
			mRemoteInterface.removeListener(collectorListener);
			// todo: do we need to unbind  again if already unbind onPause.
			unbindService(serviceConnection);
		} catch (Throwable e) {
			Log.w(TAG, "Failed to unbind ", e);
		}

		Log.i(TAG, "Activity destroyed");
	}

	@Override
	public void onTabChanged(String tabId) {
		Log.d(TAG, "***** tabId = " + tabId);

		if (tabId.equals(STOP_TRACKING_TAB_TAG)) {
			pgxPointList.clear();
			
			// stop GPS service and redirect back to mainmenu
			Profile profile = SessionManager.getProfile(this);
			new StopTracking().httpPost(profile);
			
			stopService(new Intent(GPXService.GPX_SERVICE));
			Intent intent = new Intent().setClass(this, MainMenuActivity.class);
			startActivity(intent);
			return;
		}
		
		if (tabId.equals(MAP_TAB_TAG)) {
			currentTabId = MAP_TAB_TAG;			 
		}
		else if (tabId.equals(LIST_TAB_TAG)) {
			currentTabId = LIST_TAB_TAG;
 		}
		updateView();
	}

  	private void showView(final GPXPoint point) {
		Log.d(TAG, "***** showView currentTabId= " + currentTabId);
		if (LIST_TAB_TAG.equals(currentTabId)) {
			showList(point);
			return;
		}
		
		if (MAP_TAB_TAG.equals(currentTabId)) {
			showMap(point);
		}		 
	}
	
	private void updateView() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Log.d(TAG, "***** updateView run ");
				try {
					//mRemoteInterface is null, meaning GPXService is not bind yet.
					if (mRemoteInterface != null) {
					   GPXPoint point = mRemoteInterface.getGPXPoint();
					   if (point != null) {
					      pgxPointList.add(point);
					      showView(point);
					   }
					}

				} catch (Throwable t) {
					Log.e(TAG,
							"***** Error while updating the UI with GPXService", t);
				}
			}
		});
	}
 
 
	private void showList(GPXPoint point) {
		Log.d(TAG, "***** showList point= " + point.latitude);
	 
		final List<String> info = new ArrayList<String>();
		
		//lat,lng
		info.add("Latitude="+point.latitude/1000000.00);
		info.add("Lontitude="+point.longitude/1000000.00);
		
		//speed
		int numOfMile = (int)point.speed/5280;
		int feetLeft = (int)(point.speed - numOfMile*5280);
		String speedStr = "";
		if (numOfMile != 0) {
			speedStr = numOfMile + " miles/h ";
		}		 
		speedStr = feetLeft + " feet/s";
 		info.add("Current Speed=" + speedStr);
		
 		speedinfoView.setText("");
 		
 		//totalDistance 		
 		DecimalFormat dec = new DecimalFormat("#.00");
 		String str = dec.format(StringUtils.feetInSecToMileInHour(point.totalDistance));
 		info.add("totalDistance=" + str);
 		
 		String diff = DateUtils.formatElapsedTime((point.time - point.startTime)/1000L);
 		info.add("totalTime=" + diff);
 		
		listView.setAdapter(new ArrayAdapter<String>(this,
 				android.R.layout.simple_list_item_1, info));
		 
	}

	 

	private void showMap(GPXPoint geoPoint) {
		mapView.invalidate();
		
 		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
		itemizedOverlay = new MapItemizedOverlay(drawable, this);
		 
		GeoPoint point;
		
		if (geoPoint != null) {
			point = new GeoPoint(geoPoint.latitude, geoPoint.longitude);
		} 
		else { 
			point = new GeoPoint(33289998, -83700000);
		}
		
		//speed
		int numOfMile = (int)geoPoint.speed/5280;
		int feetLeft = (int)(geoPoint.speed - numOfMile*5280);
		String speedStr = "";
		if (numOfMile != 0) {
			speedStr = numOfMile + " miles/h ";
		}		 
		speedStr = feetLeft + " feet/s";
 		speedinfoView.setText(speedStr);
	 	
		mapController.setZoom(7);
		   mapView.postInvalidate();
		   mapOverlays.clear();
		   
		   Log.d(TAG, "***** showMap point= " + geoPoint.latitude + ", " + geoPoint.longitude);
		   		   
		   
		   mapController.animateTo(point);
		   
		   //add line
		   Projection projection = mapView.getProjection();
		   mapOverlays.add(new DrawLinesOverlay(projection, point));
		   
		  //mapOverlays.add(new DrawTextOverlay());
		   
	 	   OverlayItem overlayitem = new OverlayItem(point, "", "");
 		   Drawable marker = getResources().getDrawable(R.drawable.red_dot);
		   
		   marker.setBounds(-10, -10, marker.getIntrinsicWidth()-7, marker.getIntrinsicHeight()-7);
		   overlayitem.setMarker(marker);
		   
		   itemizedOverlay.addOverlay(overlayitem);
		   mapOverlays.add(itemizedOverlay);
		
	}
	
	private List<GeoPoint> getLocations() {
		List<GeoPoint> pointsList = new ArrayList<GeoPoint>();
		pointsList.add(new GeoPoint((int) (32.864 * 1E6),
				(int) (-117.2353 * 1E6)));
		pointsList.add(new GeoPoint((int) (37.441 * 1E6),
				(int) (-122.1419 * 1E6)));

		return pointsList;
	}

	/**
	 *   draw text 
	 */
	class DrawTextOverlay extends Overlay {
		public void draw(Canvas canvas, MapView mapview, boolean shadow) {
			super.draw(canvas, mapview, shadow);
			
			Paint paint = new Paint();
			paint.setDither(true);
			paint.setColor(Color.BLUE);
			//paint.setStyle(Paint.Style.FILL_AND_STROKE);
			//paint.setStrokeJoin(Paint.Join.ROUND);
			//paint.setStrokeCap(Paint.Cap.ROUND);
			paint.setStrokeWidth(2);
			
			float x = mapview.getLeft() + 10;
			float y = 20;
			canvas.drawText (" Hello Text",  x,  y, paint);
		}
	}
	
	class DrawLinesOverlay extends Overlay {
		Projection projection;
		GeoPoint newPoint;
		
		public DrawLinesOverlay(Projection projection, GeoPoint point) {
			this.projection = projection;
			this.newPoint = point;
		}

		public void draw(Canvas canvas, MapView mapv, boolean shadow) {
			super.draw(canvas, mapv, shadow);

			if (pgxPointList == null || pgxPointList.isEmpty()) {
				return;
			}
			
			Paint mPaint = new Paint();
			mPaint.setDither(true);
			mPaint.setColor(Color.RED);
			mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			mPaint.setStrokeJoin(Paint.Join.ROUND);
			mPaint.setStrokeCap(Paint.Cap.ROUND);
			mPaint.setStrokeWidth(2);
	 		
			if (lastPoint == null) {
				lastPoint = newPoint;
				return;
			}
				
			Path path = new Path();
			Point currentPoint = new Point();
			Point lastPoint = new Point();

			GeoPoint currentGeoPoint = null;
			GeoPoint theLastGeoPoint = null;
			for (GPXPoint gpxPoint : pgxPointList) {
			    currentGeoPoint = new GeoPoint(gpxPoint.latitude, gpxPoint.longitude);
				if (theLastGeoPoint != null) {
					projection.toPixels(currentGeoPoint, currentPoint);
					projection.toPixels(theLastGeoPoint, lastPoint);
					
					path.moveTo(lastPoint.x, lastPoint.y);
					path.lineTo(currentPoint.x, currentPoint.y);		 
				}
				theLastGeoPoint = currentGeoPoint;
			}
			canvas.drawPath(path, mPaint);
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * serviceConnection is used for binding onServiceConnected is called after
	 * onBind in GPXService
	 */
	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i(TAG, "==== onServiceConnected");
			mRemoteInterface = IRemoteInterface.Stub.asInterface(service);
			try {
				mRemoteInterface.addListener(collectorListener);
			} catch (RemoteException e) {
				Log.e(TAG, "Failed to add listener", e);
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.i(TAG, "==== onServiceDisconnected");
			mRemoteInterface = null;
		}
	};
	private ICollectionListener.Stub collectorListener = new ICollectionListener.Stub() {
		@Override
		public void handleLocationUpdated() throws RemoteException {
			updateView();
		}

		@Override
		public void updateLocation(GPXPoint point) throws RemoteException {
			Log.i(TAG, "***** updateLocation point " + point.latitude);
			//updateView(point);
			
		}
	};
}
