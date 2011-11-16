package com.gogwt.apps.tracking.activities;

import android.app.TabActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gogwt.apps.tracking.R;
import com.gogwt.apps.tracking.data.GPXPoint;
import com.gogwt.apps.tracking.data.ICollectionListener;
import com.gogwt.apps.tracking.data.IRemoteInterface;
import com.gogwt.apps.tracking.services.GPXService;
import com.gogwt.apps.tracking.utils.GwtLog;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;

public class ShowLocationMainTabActivity extends TabActivity implements
		OnTabChangeListener, OnClickListener {

	protected static final String TAG = ShowLocationMainTabActivity.class.getSimpleName();	
	private static final String LIST_TAB_TAG = "List";
	private static final String MAP_TAB_TAG = "Map";
	private static final String STOP_TRACKING_TAB_TAG = "Stop Tracking";
	private static final String LOGOUT = "Logout";
	private String currentTabId = LIST_TAB_TAG;
	
	private ToggleButton mTogglebutton;
	private IRemoteInterface mRemoteInterface = null;
	private Handler mHandler;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// We don't need a window title bar:
	    requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.show_location_main_tab_layout);
 		
		// Remove the window's background because the MapView 
		// not good used in tab widget
		//getWindow().setBackgroundDrawable(null);
		
		mHandler = new Handler();
		
		TabHost tabHost = getTabHost();
		tabHost.setOnTabChangedListener(this);

		Intent mapIntent = new Intent().setClass(this, ShowLocationMapTabActivity.class);
		TabHost.TabSpec mapTabSpec = tabHost.newTabSpec(MAP_TAB_TAG);
		mapTabSpec.setIndicator("Map");
		mapTabSpec.setContent(mapIntent);
		tabHost.addTab(mapTabSpec);

		Intent listIntent = new Intent().setClass(this, ShowLocationListTabActivity.class);
		TabHost.TabSpec listSpec = tabHost.newTabSpec(LIST_TAB_TAG)
				.setIndicator("List").setContent(listIntent);
		tabHost.addTab(listSpec);

		final TextView stopTrackingview = new TextView(this);
		tabHost.addTab(tabHost.newTabSpec("Logout").setIndicator("Logout")
				.setContent(new TabContentFactory() {
					public View createTabContent(String arg0) {
						return stopTrackingview;
					}
				}));

		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i)
					.findViewById(android.R.id.title); // Unselected Tabs
			// tv.setTextColor(Color.parseColor("#ff0000"));
			tabHost.getTabWidget().getChildAt(i).getLayoutParams().height /= 2; // 30
		}
		tabHost.setCurrentTab(0);

		mTogglebutton = (ToggleButton) findViewById(R.id.togglebutton);
		mTogglebutton.setOnClickListener(this);
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
	public void onTabChanged(String tablId) {
		Log.d(TAG, " ==== tablId =" + tablId);
		if (tablId.equals(LOGOUT)) {

			Intent intent = new Intent().setClass(this, MainMenuActivity.class);
			startActivity(intent);
			return;
		}
	}

	@Override
	public void onClick(View view) {
		if (view == mTogglebutton) {
			if (mTogglebutton.isChecked()) {
				Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();
			}
		}		
	}
	
	private void showView(final GPXPoint point) {
		Log.d(TAG, "***** showView currentTabId= " + currentTabId);
		if (LIST_TAB_TAG.equals(currentTabId)) {
			//showList(point);
			return;
		}

		if (MAP_TAB_TAG.equals(currentTabId)) {
			showMap(point);
		}
	}
	
	private void showMap(final GPXPoint gpxPoint) {
		GwtLog.d(TAG, "***** showMap  " );
		
		/*
		updateSpeedInfoView(gpxPoint);
		
		drawPolylineOverlay.addNewGPXPoint(gpxPoint);
		mapView.postInvalidate();
				
		//recenter if not visible.
		GeoPoint geoPoint = new GeoPoint(gpxPoint.latitude, gpxPoint.longitude);
	    if (gpxPoint != null && !locationIsVisible(geoPoint)) {	        
	        MapController controller = mapView.getController();
	        controller.animateTo(geoPoint);
	     }
	     */
	}
	
	private void updateView() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				GwtLog.d(TAG, "***** updateView run ");
				try {
					// mRemoteInterface is null, meaning GPXService is not bind
					// yet.
					if (mRemoteInterface != null) {
						GPXPoint point = mRemoteInterface.getGPXPoint();
						if (point != null) {
							//pgxPointList.add(point);
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
			GwtLog.i(TAG, "==== onServiceDisconnected");
			mRemoteInterface = null;
		}
	};
	
	private ICollectionListener.Stub collectorListener = new ICollectionListener.Stub() {
		@Override
		public void handleLocationUpdated() throws RemoteException {
			updateView();
		}
	};

}
