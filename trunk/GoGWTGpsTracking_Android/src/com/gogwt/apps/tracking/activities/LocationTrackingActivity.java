package com.gogwt.apps.tracking.activities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
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
import com.gogwt.apps.tracking.services.GPXService;
import com.gogwt.apps.tracking.utils.GwtLog;
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
	private TextView speedinfoView;
	private Handler handler;
	private MapItemizedOverlay mItemizedOverlay;

	private IRemoteInterface mRemoteInterface = null;
	private List<GPXPoint> pgxPointList = null;
	private boolean isFirstPoint;
	private GeoPoint lastPoint = null;
	private Projection mProjection;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		GwtLog.i(TAG, "***** onCreate");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.tracking_location_tab_layout);

		LocationManager locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			createGpsDisabledAlert();
		}
		
		handler = new Handler();
		pgxPointList = new ArrayList<GPXPoint>();

		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(); // required if using findViewById
		tabHost.setOnTabChangedListener(this);
		// tabHost.setBackgroundColor(Color.WHITE);
		//tabHost.getTabWidget().setBackgroundColor(Color.BLUE);

		/*
		 * LinearLayout ll = (LinearLayout) tabHost.getChildAt(0); TabWidget tw
		 * = (TabWidget) ll.getChildAt(0);
		 * 
		 * // first tab RelativeLayout rllf = (RelativeLayout) tw.getChildAt(0);
		 * TextView lf = (TextView) rllf.getChildAt(1); lf.setTextSize(21);
		 * lf.setPadding(0, 0, 0, 6);
		 */

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
		mapController.setZoom(7); // Zoom 1 is world view
		mProjection = mapView.getProjection();

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
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onStart() {
		GwtLog.d(TAG, "**** onStart");
		String remoteName = GPXService.GPX_SERVICE;
		Intent intent = new Intent(remoteName);

		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

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
	public void onTabChanged(String tabId) {
		GwtLog.d(TAG, "***** onTabChanged");

		if (tabId.equals(STOP_TRACKING_TAB_TAG)) {

			pgxPointList.clear();
			unbindService(serviceConnection);

			Intent intent = new Intent().setClass(this, MainMenuActivity.class);
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
					// mRemoteInterface is null, meaning GPXService is not bind
					// yet.
					if (mRemoteInterface != null) {
						GPXPoint point = mRemoteInterface.getGPXPoint();
						if (point != null) {
							pgxPointList.add(point);
							showView(point);
						}
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

	private void showList(GPXPoint point) {
		Log.d(TAG, "***** showList point= " + point.latitude);

		final List<String> info = new ArrayList<String>();

		// lat,lng
		info.add("Latitude=" + point.latitude / 1000000.00);
		info.add("Lontitude=" + point.longitude / 1000000.00);

		// speed
		int numOfMile = (int) point.speed / 5280;
		int feetLeft = (int) (point.speed - numOfMile * 5280);
		String speedStr = "";
		if (numOfMile != 0) {
			speedStr = numOfMile + " miles/h ";
		}
		speedStr = feetLeft + " feet/s";
		info.add("Current Speed=" + speedStr);

		speedinfoView.setText("");

		// totalDistance
		DecimalFormat dec = new DecimalFormat("#.00");
		String str = dec.format(StringUtils
				.feetInSecToMileInHour(point.totalDistance));
		info.add("totalDistance=" + str);

		String diff = DateUtils
				.formatElapsedTime((point.time - point.startTime) / 1000L);
		info.add("totalTime=" + diff);

		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, info));

	}

	private void updateSpeedInfoView(final GPXPoint gpxPoint) {
		// speed
		double mileperhour = StringUtils.feetInSecToMileInHour(gpxPoint.speed); /// / 5280.00;
		
		//int feetLeft = (int) (gpxPoint.speed - numOfMile * 5280);
		String speedStr = StringUtils.format(mileperhour) + " miles/hour [ ";		
		speedStr += gpxPoint.speed + " feet/s]";
		speedinfoView.setText(speedStr);
	}

	private void showMap(final GPXPoint gpxPoint) {
		updateSpeedInfoView(gpxPoint);

		//mapView.invalidate();
		// mapView.postInvalidate();

		List<Overlay> mapOverlays = mapView.getOverlays();
		//instead of remove all polylines and markers, only remove marker
		for (Overlay mapOverlay : mapOverlays) {
			if (mapOverlay instanceof MapItemizedOverlay) {
				mapOverlays.remove(mapOverlay);
			}
		}
		
		//mapOverlays.clear();

		final GeoPoint point = new GeoPoint(gpxPoint.latitude,
				gpxPoint.longitude);
		mapController.animateTo(point);

		// add line
		mapOverlays.add(new DrawLinesOverlay(point));

		// add marker
		OverlayItem overlayitem = new OverlayItem(point, "", "");
		
		//if (mItemizedOverlay == null) {		    	
			Drawable marker = this.getResources().getDrawable(R.drawable.red_dot);
		    mItemizedOverlay = new MapItemizedOverlay(marker, this);		
		    marker.setBounds(-10, -10, marker.getIntrinsicWidth() - 7, marker.getIntrinsicHeight() - 7);
		//}
		   
	    overlayitem.setMarker( mItemizedOverlay.getDrawable());
		//overlayitem.setMarker(null);
	    mItemizedOverlay.addOverlay(overlayitem);
		
		mapOverlays.add(mItemizedOverlay);
	    
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
 			 
			
			//todo: revisit it later: only draw new line
			/*
			mProjection.toPixels(newPoint, currentPnt);
			mProjection.toPixels(lastPoint, lastPnt);

			path.moveTo(lastPnt.x, lastPnt.y);
			path.lineTo(currentPnt.x, currentPnt.y);
			
			lastPoint = newPoint;
			*/
			
			canvas.drawPath(path, mPaint);
		}
	}

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
}
