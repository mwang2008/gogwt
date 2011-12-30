package com.gogwt.apps.tracking.activities;

import static com.gogwt.apps.tracking.GoGWTConstants.UNIT;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gogwt.apps.tracking.R;
import com.gogwt.apps.tracking.data.GPXPoint;
import com.gogwt.apps.tracking.data.ICollectionListener;
import com.gogwt.apps.tracking.data.IRemoteInterface;
import com.gogwt.apps.tracking.services.GPXService;
import com.gogwt.apps.tracking.services.http.HttpService;
import com.gogwt.apps.tracking.utils.GeoRect;
import com.gogwt.apps.tracking.utils.GwtLog;
import com.gogwt.apps.tracking.utils.NotifyMessageUtils;
import com.gogwt.apps.tracking.utils.SessionManager;
import com.gogwt.apps.tracking.utils.StringUtils;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class LocationTrackingActivity extends MapActivity implements
		OnTabChangeListener, View.OnTouchListener, View.OnClickListener {
	
	protected static final String TAG = LocationTrackingActivity.class.getSimpleName();
	
	private static final String LIST_TAB_TAG = "List";
	private static final String MAP_TAB_TAG = "Map";
	private static final String STOP = "stop";
	
	private String currentTabId = LIST_TAB_TAG;
	private TabHost tabHost;
	private ListView listView;
	private MapView mapView;
	private MapController mapController;
	private TextView stopTrackingview;
	private TextView speedinfoView;
	
	private Handler handler;
	private IRemoteInterface mRemoteInterface = null;
	
    private DrawPolylineOverlay drawPolylineOverlay;
    private ToggleButton togglebutton;
    private boolean isFirstPoint;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		GwtLog.i(TAG, "====== LocationTrackingActivity onCreate \n\n");
	 	
		super.onCreate(savedInstanceState);
		 // We don't need a window title bar:
	    requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.tracking_location_tab_layout);

		LocationManager locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			createGpsDisabledAlert();
		}
		
		isFirstPoint = false;
		
	 	handler = new Handler();
	
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(); // required if using findViewById
		tabHost.setOnTabChangedListener(this);
		// tabHost.setBackgroundColor(Color.WHITE);
		//tabHost.getTabWidget().setBackgroundColor(Color.BLUE);

		// setup list view
		listView = (ListView) findViewById(R.id.list);
		listView.setEmptyView((TextView) findViewById(R.id.empty));

		// setup map view
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setSatellite(false);
		mapView.setStreetView(false);

		mapView.setBuiltInZoomControls(true);
		//mapView.postInvalidate();
		drawPolylineOverlay = new DrawPolylineOverlay(this);
		mapView.getOverlays().add(drawPolylineOverlay);
		
		mapController = mapView.getController();
		mapController.setZoom(10); // Zoom 1 is world view
		//mProjection = mapView.getProjection();
		//mapView.setReticleDrawMode(MapView.ReticleDrawMode.DRAW_RETICLE_UNDER);
		 
		speedinfoView = (TextView) findViewById(R.id.speedinfo);

		// stop tracking
		stopTrackingview = new TextView(this);

		// add views to tab host
		tabHost.addTab(tabHost.newTabSpec(LIST_TAB_TAG)
				.setIndicator("Text").setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {
						return listView;
					}
				}));
		
		tabHost.addTab(tabHost.newTabSpec(MAP_TAB_TAG).setIndicator("Map", null)
				.setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {
						return mapView;
					}
				}));

		tabHost.addTab(tabHost.newTabSpec(STOP)
				.setIndicator("Stop")
				.setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {
						return stopTrackingview;
					}
				}));
 		 
		for(int i=0;i<tabHost.getTabWidget().getChildCount();i++) {
			 TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
	           // tv.setTextColor(Color.parseColor("#ff0000"));
	            GwtLog.d(TAG, "**** tab.height="+tabHost.getTabWidget().getChildAt(i).getLayoutParams().height);
	            tabHost.getTabWidget().getChildAt(i).getLayoutParams().height /= 2;  //30
		}
		//TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
        //tv.setTextColor(Color.parseColor("#000000"));  status_right
		
		togglebutton = (ToggleButton) findViewById(R.id.togglebutton);
		togglebutton.setOnClickListener(this);
		
		//"MM/dd/yy hh:mm:ss"
        TextView statusRight = (TextView)findViewById(R.id.status_right);
        String startGPSTime = "GPS Start at " + android.text.format.DateFormat.format("hh:mm:ss", new java.util.Date()).toString();
        
        statusRight.setText(startGPSTime);
        
        if (!SessionManager.getGpxContext().isAppStart()) {
            SessionManager.getGpxContext().startApp();
        }
        
		tabHost.setCurrentTab(1);
		//tabHost.setCurrentTab(0);
 	}

	@Override
	protected boolean isRouteDisplayed() {		
		return false;
	}

	@Override
	protected void onStart() {
		GwtLog.d(TAG, "**** onStart");
		if (!SessionManager.getGpxContext().isGPSBound()) {
		    String remoteName = GPXService.GPX_SERVICE;
		    Intent intent = new Intent(remoteName);
		    bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
		    SessionManager.getGpxContext().startTrack();
		} 	
		
 		super.onStart();
	}

	@Override
	protected void onResume() {
		GwtLog.d(TAG, "**** onResume");
		super.onResume();
	}

	@Override
	protected void onPause() {
		GwtLog.d(TAG, "***** onPause");
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		GwtLog.d(TAG, " == onDestroy");
		
		if (SessionManager.getGpxContext().isGPSBound()) {
			unbindService(serviceConnection);			
			SessionManager.getGpxContext().setAppStart(false);
		}
		 
		
	}

	@Override
	public void onClick(View paramView) {
		GwtLog.d(TAG, " == onClick:togglebutton");
	   if (togglebutton == paramView) {
		   // Perform action on clicks
	     if (togglebutton.isChecked()) {
		     
		    if (!SessionManager.getGpxContext().isGPSBound()) {
				String remoteName = GPXService.GPX_SERVICE;
				Intent intent = new Intent(remoteName);
				bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
				SessionManager.getGpxContext().setGPSBound(true);
		    }
	     } else {
	        //pause
			if (SessionManager.getGpxContext().isGPSBound()) {
				unbindService(serviceConnection);
				SessionManager.getGpxContext().setGPSBound(false);
				
				NotifyMessageUtils.showNotifyMsgWithResume(LocationTrackingActivity.class, this.getApplicationContext(), "GPS - Pause", "GPS is paused");
			}
	     }
	  }
	}
	  
	@Override
	public void onTabChanged(String tabId) {
		GwtLog.d(TAG, "***** onTabChanged tabId="+tabId + " , id=" +tabHost.getCurrentTab());
	
		if (tabId.equals(STOP)) {

			//pgxPointList.clear();
			if (SessionManager.getGpxContext().isGPSBound()) {
				unbindService(serviceConnection);
				//mIsBound = false;
				
 				
				String startEnableGPSTime = android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", SessionManager.getGpxContext().getAppStartTime()).toString();
				String endEnableGPSTime   = android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date()).toString();
				String msg = "GPS started at " + startEnableGPSTime;
				msg += " and ended at " +  endEnableGPSTime;
				//NotifyMessageUtils.showNotifyMsgOnceTime(MainMenuActivity.class, this.getApplicationContext(), "GPS - Stop", msg);
				//todo: mwang
				NotifyMessageUtils.sendCustomNotificationWithOnceTime(MainMenuActivity.class, this.getApplicationContext(), "GPS - Stop", msg);
				
			}
		 		
		    /* todo: add back later
	 		Intent intent = null;
			if (pgxPointList !=null && !pgxPointList.isEmpty()) {
			   intent = new Intent().setClass(this, SaveTrackActivity.class);
               Bundle extras = new Bundle();
               extras.putParcelableArrayList("locs", pgxPointList);
               intent.putExtras(extras);
			}
			else {
			   intent = new Intent().setClass(this, MainMenuActivity.class);
			}
			*/
			HttpService.stopTracking(this.getApplicationContext());
			SessionManager.getGpxContext().stopTrack();
			
			Intent intent = null;
			intent = new Intent().setClass(this, MainMenuActivity.class);
			startActivity(intent);
			return;
		}
		
   
		if (tabId.equals(MAP_TAB_TAG)) {
			currentTabId = MAP_TAB_TAG;
			
		} else if (tabId.equals(LIST_TAB_TAG)) {
			currentTabId = LIST_TAB_TAG;
			 
		}

		updateView();
	}

	@Override  
	public boolean onCreateOptionsMenu(Menu menu) {  	   
		 MenuInflater inflater = getMenuInflater();
		 inflater.inflate(R.menu.main_menu, menu);		 
		 return true;
	}  

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_setting:     
	        	//Toast.makeText(this, "You pressed the setting!", Toast.LENGTH_LONG).show();
	        	startActivity(new Intent(this, SettingPrefsActivity.class));
	            break;
	        case R.id.menu_help:     
	        	startActivity(new Intent(this, HelpActivity.class));
	            break;
	        case R.id.menu_logout: 	        	
	        	startActivity(new Intent(this, LogoutActivity.class));	     
	            break;
	    }
	    return true;
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
				GwtLog.d(TAG, "***** updateView run ");
				try {
					// mRemoteInterface=null, meaning GPXService is not bind yet.
					if (mRemoteInterface != null) {
						GPXPoint point = mRemoteInterface.getGPXPoint();
						if (point != null) {
							//pgxPointList.add(point);
							showView(point);
						}
					}
					else {
						speedinfoView.setText("Waiting for GPS signal ...");
					}

				} catch (Throwable t) {
					GwtLog.e(
							TAG,
							"***** Error while updating the UI with GPXService",
							t);
				}
			}
		});
	}

	private void showList(GPXPoint gpxPoint) {
		GwtLog.d(TAG, "***** showList point= " + gpxPoint.latitude);
		
		//add point to collection of polyline defined in drawPolylineOverlay 
		drawPolylineOverlay.addNewGPXPoint(gpxPoint);
		
		final List<String> info = new ArrayList<String>();

		speedinfoView.setText(" ");

		SharedPreferences appPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String unit = appPreference.getString(UNIT, "mi");
		if (StringUtils.equalsIgnoreCase(unit, "mi")) {
			// currrent speed
		    double speed = StringUtils.meterPerSecToMilePerHour(gpxPoint.speed);
			String speedStr = StringUtils.format(speed) + " mph [ ";		
			speedStr += StringUtils.format(StringUtils.meterToFeet(gpxPoint.speed)) + " f/s]";
			info.add("Current speed: " + speedStr);
		}
		else {
		    double speed = StringUtils.meterPerSecToKilometerPerHour(gpxPoint.speed);
			String speedStr = StringUtils.format(speed) + " kph [ ";		
			speedStr += StringUtils.format(gpxPoint.speed) + " m/s]";
			info.add("Current speed: " + speedStr);			
		}
	

			
		// totalDistance
		//DecimalFormat dec = new DecimalFormat("#.00");
		String str = StringUtils.format(gpxPoint.totalDistance*0.000621371192); //meters + " ms";
		String strKm = StringUtils.format(gpxPoint.totalDistance*0.000621371192*1.609);  //kilometer
				
		info.add("Total distance: " + str + " mi [" + strKm + " km]");

		CharSequence diff = DateUtils.getRelativeTimeSpanString(gpxPoint.startTime, System.currentTimeMillis(), 0);		
		info.add("GPS start: " + diff);

		// lat,lng
		DecimalFormat decformat = new DecimalFormat("#.00000");
		info.add("Latitude: " + decformat.format(gpxPoint.latitude / 1000000.00));
		info.add("Lontitude: " + decformat.format(gpxPoint.longitude / 1000000.00));

		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, info));

	}

	private void updateSpeedInfoView(final GPXPoint gpxPoint) {
		
		SharedPreferences appPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String unit = appPreference.getString(UNIT, "mi");
		if (StringUtils.equalsIgnoreCase(unit, "mi")) {
			// speed
			double mileperhour = StringUtils.meterPerSecToMilePerHour(gpxPoint.speed); 
			String speedStr = StringUtils.format(mileperhour) + " mph [ ";		
			speedStr += StringUtils.format(StringUtils.meterToFeet(gpxPoint.speed)) + " f/s]";
			speedinfoView.setText(speedStr);
		}
		else {
			double kilometerperhour = StringUtils.meterPerSecToKilometerPerHour(gpxPoint.speed); 
			String speedStr = StringUtils.format(kilometerperhour) + " kph [ ";
			speedStr += StringUtils.format(gpxPoint.speed) + " m/s]";
			speedinfoView.setText(speedStr);
		}
 	}

	private void showMap(final GPXPoint gpxPoint) {
		GwtLog.d(TAG, "***** showMap  " );
		updateSpeedInfoView(gpxPoint);
		
		//add point to overlay and call postInvalidate to trigger draw
		drawPolylineOverlay.addNewGPXPoint(gpxPoint);
		mapView.postInvalidate();
				
		//recenter if not visible.
		GeoPoint geoPoint = new GeoPoint(gpxPoint.latitude, gpxPoint.longitude);
	    if (gpxPoint != null && !locationIsVisible(geoPoint)) {	        
	    	mapController = mapView.getController();			 
	    	mapController.animateTo(geoPoint);	
	    	if (!isFirstPoint) {
	    		mapController.setZoom(14);
	    		isFirstPoint = true;	    		
	    	}
	    }
	}
	
	  @Override
	  public boolean onTouch(View view, MotionEvent event) {
		  GwtLog.d(TAG, "***** new onTouch  " ); 
	    /*if (keepMyLocationVisible && event.getAction() == MotionEvent.ACTION_MOVE) {
	      if (!locationIsVisible(currentLocation)) {
	        keepMyLocationVisible = false;
	      }
	    }*/
	    return false;
	  }
	  

	private boolean locationIsVisible(GeoPoint geoPoint) {
		    if (geoPoint == null || mapView == null) {
		      return false;
		    }
		    GeoPoint center = mapView.getMapCenter();
		    int latSpan = mapView.getLatitudeSpan();
		    int lonSpan = mapView.getLongitudeSpan();

		    // Bottom of map view is obscured by zoom controls/buttons.
		    // Subtract a margin from the visible area:
		    GeoPoint marginBottom = mapView.getProjection().fromPixels(
		        0, mapView.getHeight());
		    GeoPoint marginTop = mapView.getProjection().fromPixels(0,
		        mapView.getHeight()
		            - mapView.getZoomButtonsController().getZoomControls().getHeight());
		    int margin =
		        Math.abs(marginTop.getLatitudeE6() - marginBottom.getLatitudeE6());
		    GeoRect rec = new GeoRect(center, latSpan, lonSpan);
		    rec.top += margin;

		    return rec.contains(geoPoint);
    }



	/**
	 * serviceConnection is used for binding onServiceConnected is called after
	 * onBind in GPXService
	 */
	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			GwtLog.i(TAG, "==== onServiceConnected");
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
	};
	
	private void createGpsDisabledAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Your GPS is disabled! Would you like to enable it?")
				.setCancelable(false)
				.setPositiveButton("Enable GPS",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								showGpsOptions();
							}
						});
		builder.setNegativeButton("Do nothing",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void showGpsOptions() {
		Intent gpsOptionsIntent = new Intent(
				android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(gpsOptionsIntent);
	}
	/**
	 * 
	 * @author michael.wang
	 * 
	 */
	//private ArrayList<GPXPoint> pgxPointList = null;
	/*
	class DrawLinesOverlay extends Overlay {
		// Projection projection;
		GeoPoint newPoint;

		public DrawLinesOverlay(GeoPoint point) {
			// this.projection = projection;
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
			Point currentPnt = new Point();
			Point lastPnt = new Point();
            
			GeoPoint currentGeoPoint = null;
			GeoPoint theLastGeoPoint = null;
			for (GPXPoint gpxPoint : pgxPointList) {
				currentGeoPoint = new GeoPoint(gpxPoint.latitude,
						gpxPoint.longitude);
				if (theLastGeoPoint != null) {
					mProjection.toPixels(currentGeoPoint, currentPnt);
					mProjection.toPixels(theLastGeoPoint, lastPnt);

					path.moveTo(lastPnt.x, lastPnt.y);
					path.lineTo(currentPnt.x, currentPnt.y);
				}
				theLastGeoPoint = currentGeoPoint;
			}
			canvas.drawPath(path, mPaint);
		}
	}
    */
	
	/*private void showMap_ori(final GPXPoint gpxPoint) {
	GwtLog.d(TAG, "***** showMap= " );
		updateSpeedInfoView(gpxPoint);

	mapView.invalidate();
	//mapView.postInvalidate();

	List<Overlay> mapOverlays = mapView.getOverlays();
	//instead of remove all polylines and markers, only remove marker
	
	for (Overlay mapOverlay : mapOverlays) {
		if (mapOverlay instanceof MapItemizedOverlay) {
			mapOverlays.remove(mapOverlay);
			break;
		}
	}
	
	
	//mapOverlays.clear();

	final GeoPoint point = new GeoPoint(gpxPoint.latitude, gpxPoint.longitude);
	
	// add marker
	OverlayItem overlayitem = new OverlayItem(point, "", "");
	
	if (markerDot == null) {
		markerDot = this.getResources().getDrawable(R.drawable.red_circle);  //red_circle	red_dot		
		markerDot.setBounds(-10, -10, markerDot.getIntrinsicWidth() - 7, markerDot.getIntrinsicHeight() - 7);
	}
	mItemizedOverlay = new MapItemizedOverlay(markerDot, this);		
	    
	   
    overlayitem.setMarker( mItemizedOverlay.getDrawable());
	//overlayitem.setMarker(null);
    mItemizedOverlay.addOverlay(overlayitem);
    mapOverlays.add(mItemizedOverlay);
    
	// add line		
	if (drawPolylineOverlay == null) {
		drawPolylineOverlay = new DrawPolylineOverlay(this);
	}
	
	drawPolylineOverlay.addNewGPXPoint(gpxPoint);
	
	
	if (lastPoint == null) {
		mapController.animateTo(point);
		lastPoint = point;
		mapController.setZoom(10);
		return;
	}
	 
 	mapOverlays.add(drawPolylineOverlay);
	
	//mapOverlays.add(new DrawLinesOverlay(point));
 	 
    
}*/

	
}
