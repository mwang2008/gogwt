package com.gogwt.apps.tracking.activities;

import static com.gogwt.apps.tracking.GoGWTConstants.UNIT;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.provider.Contacts.People;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.processor.SendSmsManager;
import com.gogwt.apps.tracking.provider.QuickContactSearcher;
import com.gogwt.apps.tracking.provider.QuickContactSearcher.MyContact;
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

/**
 * 
 * The main class for display map.
 * @author michael.wang
 *
 */
public class LocationTrackingActivity extends MapActivity implements
		OnTabChangeListener, View.OnTouchListener, View.OnClickListener, PhoneContactIF {
	
	protected static final String TAG = LocationTrackingActivity.class.getSimpleName();
	
	private static final String LIST_TAB_TAG = "List";
	private static final String MAP_TAB_TAG = "Map";
	private static final String STOP = "stop";
	protected static final int CREATE_REQUEST_CODE = 1;
	private static final int PICK_CONTACT2 = 21;
	
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
    
    //private Messenger smsService = null;
    //boolean smsIsBound;
	//boolean gpxIsBound;
	private GPXPoint currentPoint;
	private Dialog locDialog;	
	private EditText phoneNumberText;
	private LinearLayout dialogPhonesec;
	private LinearLayout dialogButtonSec;
	private EditText dialogMessage;
	private Button sendSMS;
	private Button dialogCancel;
	private ListView mList;
	
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
		    //gpxIsBound = true;
		} 		
 		super.onStart();
	}

	@Override
	protected void onResume() {
		GwtLog.d(TAG, "**** onResume");
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	  super.onRestoreInstanceState(savedInstanceState);

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
			//gpxIsBound = false;
		}
		
		if (SessionManager.getGpxContext().isStartGPXService()) {
			stopService(new Intent(getApplicationContext(), GPXService.class));
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
		 		
				String startEnableGPSTime = android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", SessionManager.getGpxContext().getAppStartTime()).toString();
				String endEnableGPSTime   = android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date()).toString();
				String msg = "GPS started at " + startEnableGPSTime;
				msg += " and ended at " +  endEnableGPSTime;
				//NotifyMessageUtils.showNotifyMsgOnceTime(MainMenuActivity.class, this.getApplicationContext(), "GPS - Stop", msg);				 
				NotifyMessageUtils.sendCustomNotificationWithOnceTime(MainMenuActivity.class, this.getApplicationContext(), "GPS - Stop", msg);
				
			}
		 				
			//notify server that this Device is stop tracking 
			new HttpService().stopTracking(this.getApplicationContext());
			
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

	/**
	 * Load menu
	 */
	@Override  
	public boolean onCreateOptionsMenu(Menu menu) {  	   
		 MenuInflater inflater = getMenuInflater();
		 Profile profile = SessionManager.getProfile(getApplicationContext());
		 
		 /* by using admin, the map could not be refershed.
		 if (profile != null && profile.isGroupOwner()) {
			 inflater.inflate(R.menu.location_tracking_menu_admin, menu);
		 }
		 else {
			 inflater.inflate(R.menu.location_tracking_menu, menu);
		 }
		 */
		 inflater.inflate(R.menu.location_tracking_menu, menu);
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
	        case R.id.menu_currentlocation:     
	        	//Toast.makeText(this, "You pressed the sendlocation!", Toast.LENGTH_LONG).show();
	        	
	        	if (currentPoint == null) {
	        		Toast.makeText(this, "Your current location is not available yet, please try again", Toast.LENGTH_LONG).show();
	        	}
	        	else {
	          		try {
	          			showLocationDialog();
	        		}
	        		catch (Throwable e) {
	        			GwtLog.d(TAG, " showLocDialog error  " + e.getMessage());
	        		}
	        	}
	            break;	   
	        case R.id.menu_admin:
	        	 		 
				Intent nextIntent = new Intent(this, AdminInvokeActivity.class);
				 
				SessionManager.setFromActivityCls(LocationTrackingActivity.class);
				
	        	startActivity(nextIntent);   
	        	break;
	        case R.id.menu_logout: 	        	
	        	startActivity(new Intent(this, LogoutActivity.class));	     
	            break;
	        default:
				break;
	    }
	    return true;
	}
	
	/**
	 * Create custom dialog after user click Send Location from MENU
	 */
	private void showLocationDialog() {
		if (locDialog == null) {
			locDialog = new Dialog(LocationTrackingActivity.this);
			locDialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
			locDialog.setContentView(R.layout.custom_location_dialog);		 
			locDialog.setTitle("Send Current Location");
			locDialog.setCancelable(true);
			
			dialogButtonSec = (LinearLayout)locDialog.findViewById(R.id.dialogButtonSec);
			dialogMessage = (EditText)locDialog.findViewById(R.id.dialogMessage);  
			dialogCancel = (Button)locDialog.findViewById(R.id.dialogCancel);
		}
	
		showHideButtonsAndMsg(true);
		
		phoneNumberText = (EditText) locDialog.findViewById(R.id.remotePhone);
		dialogPhonesec = (LinearLayout)locDialog.findViewById(R.id.dialogPhonesec);
		
	    sendSMS = (Button)locDialog.findViewById(R.id.sendSMS);
		sendSMS.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
        	  
		       String phone = phoneNumberText.getText().toString();
		       if (!StringUtils.isSet(phone)) {
				   Toast.makeText(getApplicationContext(),"Please enter phone number", Toast.LENGTH_LONG).show();						 
		       }
		       else if (!StringUtils.isValidPhoneNumber(phone)) {
		    	   Toast.makeText(getApplicationContext(),"Invalid phone number, please provide correct one", Toast.LENGTH_LONG).show();
		       }
		       else {
		  	      //create new thread to send sms		    	  
		    	  String dialogMsg = dialogMessage.getText().toString();
		    	  StringBuilder sbud = new StringBuilder();
		    	  
		    	  if (StringUtils.isSet(dialogMsg)) {
		    		  sbud.append(dialogMsg);
		    		  sbud.append("\n");
		    	  }
		    	  
		    	  sbud.append("My current location: http://maps.google.com/?q="); 
				  sbud.append(currentPoint.latitude/1e6);
				  sbud.append(",");
				  sbud.append(currentPoint.longitude/1e6);
					  
 				  PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 
				   			(int)System.currentTimeMillis(), new Intent(getApplicationContext(), SendSmsManager.class), 0);
 
 				  new SendSmsTask().execute(phone, null, sbud.toString(), pIntent);
  		          locDialog.cancel();
		       }
            }
        });
		
		   
		Button searchBtn = (Button)locDialog.findViewById(R.id.dialogSearch);
		searchBtn.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
        	   //get contact list from contact resource
        	   
        	   //comment it out as could not search with name; however it is good to keep it here for ACTION_PICK demo
    		   //Intent intent = new Intent(Intent.ACTION_PICK, People.CONTENT_URI);
    	       //startActivityForResult(intent, PICK_CONTACT2); 
        	   
    	       String contactName = phoneNumberText.getText().toString();
    	       
    	       List<MyContact> contacts = QuickContactSearcher.getInstance().searchContactsByPartialName(getApplicationContext(), contactName);
    	       displayContactList(contacts);
           }
        });
		   
		
		dialogCancel.setOnClickListener(new OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	locDialog.cancel();
		 	}
		});   
		   
		locDialog.show();
		locDialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.ic_dialog_info);
	}

	private void displayContactList(List<MyContact> contacts) {
		if (contacts == null || contacts.isEmpty()) {
			Toast.makeText(this, "could not find the person", Toast.LENGTH_LONG).show();
			return;
		}
 
		//hide send/cancel button as well as dislogMessage 		
		showHideButtonsAndMsg(false);
		dialogPhonesec.setVisibility(View.VISIBLE);
		
		//display contact list for selection
		ListView myList = getLocDialogListView(); //(ListView)locDialog.findViewById(R.id.dialogList);
         
		boolean displayContact = true;
        PhoneAdapter phoneAdapter = new PhoneAdapter(displayContact, contacts, getApplicationContext(), this);
        myList.setAdapter(phoneAdapter);
        myList.setOnItemClickListener(phoneAdapter);	
        
	}
	/**
	 * Response for the call of startActivityForResult(intent, PICK_CONTACT2);
	 * @deprecated 
	 */
	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
	    super.onActivityResult(reqCode, resultCode, data);
	    dialogPhonesec.setVisibility(View.GONE);
	    try {
	        if (resultCode == Activity.RESULT_OK && data != null) {
	        	if (reqCode == PICK_CONTACT2) {
	        		//show list 
		            Uri contactData = data.getData();	 	            
		            Cursor cur = managedQuery(contactData, null, null, null, null);
       		
		            if (cur == null) {
		            	Toast.makeText(this, "could not find the person", Toast.LENGTH_LONG).show();
		            	return;
		            }
		            
		            cur.moveToFirst();		            
		          
		            String name = cur.getString(cur.getColumnIndexOrThrow(People.NAME));
	                //String number = cur.getString(cur.getColumnIndexOrThrow(People.NUMBER));
	                
	                //close cursor
	                cur.close();
	                
	                List<MyContact> myPhoneContacts = QuickContactSearcher.getInstance().searchPhonesByPartialName(this, name);
	                if (myPhoneContacts == null || myPhoneContacts.isEmpty()) {
	        	        Toast.makeText(this, "Could not find the person phone number, please type in phone number", Toast.LENGTH_LONG).show();
	        	        return;
	                }
	                
	                if (myPhoneContacts.size() == 1) {
	                	phoneNumberText.setText(myPhoneContacts.get(0).number);
	                	showHideButtonsAndMsg(true);
	        	        return;
	                }
	                
	                dialogPhonesec.setVisibility(View.VISIBLE);
	                
	        		//Toast.makeText(this, name + "," + number, Toast.LENGTH_LONG).show();
	                TextView dialogContactName = (TextView)locDialog.findViewById(R.id.dialogContactName);
	                dialogContactName.setText(name);
	                ListView myList = getLocDialogListView(); //(ListView)locDialog.findViewById(R.id.dialogList);
	                
	                //
	                
	                PhoneAdapter phoneAdapter = new PhoneAdapter(false, myPhoneContacts, getApplicationContext(), this);
	                myList.setAdapter(phoneAdapter);
	                myList.setOnItemClickListener(phoneAdapter);	                 
	        	}
	        }	     
        } catch (IllegalArgumentException e) {           
           GwtLog.d("IllegalArgumentException :: ", e.toString());
        } catch (Exception e) {
           GwtLog.d("Error :: ", e.toString());
        }
	}
	
	/**
	 * Call back
	 * After user click phone : Mobile phonenumber 
	 * @param theContact
	 */
	public void handlePhoneList(QuickContactSearcher.MyContact theContact, boolean isContact) {		
		if (isContact) {
			// get contact name
			retrieveNdisplayPhoneNumberByName(theContact.name);
			 	
		}
		else {
			// get phone number
		   phoneNumberText.setText(theContact.number);		
		   dialogPhonesec.setVisibility(View.GONE);
		   showHideButtonsAndMsg(true);
		}
		
	}
	
	private void showHideButtonsAndMsg(boolean show) {
		 
		if (show) {
			dialogButtonSec.setVisibility(View.VISIBLE);
			dialogMessage.setVisibility(View.VISIBLE); 
			
		}
		else {
			dialogButtonSec.setVisibility(View.INVISIBLE);
			dialogMessage.setVisibility(View.INVISIBLE);
		}
		 
	}
	
	private void retrieveNdisplayPhoneNumberByName(final String contactName) {
		 	
		showHideButtonsAndMsg(true);
		
		List<MyContact> myPhoneContacts = QuickContactSearcher.getInstance().searchPhonesByPartialName(this, contactName);
		if (myPhoneContacts == null || myPhoneContacts.isEmpty()) {
	        Toast.makeText(this, "Could not find the person phone number, please type in phone number", Toast.LENGTH_LONG).show();
	        return;
        }
        
        if (myPhoneContacts.size() == 1) {
        	phoneNumberText.setText(myPhoneContacts.get(0).number);
        	dialogPhonesec.setVisibility(View.GONE);
        	
	        return;
        }
        
        showHideButtonsAndMsg(false);
        dialogPhonesec.setVisibility(View.VISIBLE);
        
		//Toast.makeText(this, name + "," + number, Toast.LENGTH_LONG).show();
        TextView dialogContactName = (TextView)locDialog.findViewById(R.id.dialogContactName);
        dialogContactName.setText(contactName);
        ListView myList = getLocDialogListView(); // (ListView)locDialog.findViewById(R.id.dialogList);
         
        PhoneAdapter phoneAdapter = new PhoneAdapter(false, myPhoneContacts, getApplicationContext(), this);
        myList.setAdapter(phoneAdapter);
        myList.setOnItemClickListener(phoneAdapter);	
	}
	
	
	private ListView getLocDialogListView() {
		if (mList == null) {
			mList = (ListView)locDialog.findViewById(R.id.dialogList);
		}
		return mList;
	}
	/**
	 * @deprecated use showLocationDialog
	 */
	private void showLocDialog() {
		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.alert_dialog_loc_entry, null);
		//sendSMS
		
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setView(textEntryView);
		alertDialog.setTitle("Send Current Location");
		//alertDialog.setMessage("");
		
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Send", new DialogInterface.OnClickListener() {
		      public void onClick(DialogInterface dialog, int which) {
		    	  EditText phoneNumberText = (EditText) alertDialog.findViewById(R.id.remotePhone);
		    	  String phone = phoneNumberText.getText().toString();
		    	  if (!StringUtils.isSet(phone)) {
					  Toast.makeText(getApplicationContext(),"Please enter phone number", Toast.LENGTH_LONG).show();						 
				  }
		    	  else {
		    		  //Toast.makeText(getApplicationContext(), "Send location to " + phone, Toast.LENGTH_LONG).show();
		    		  
			    	  //create new thread to send sms
		        	  StringBuilder sbud = new StringBuilder("My current location: http://maps.google.com/?q="); 
					  sbud.append(currentPoint.latitude/1e6);
					  sbud.append(",");
					  sbud.append(currentPoint.longitude/1e6);
					  
 					  PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 
				    			(int)System.currentTimeMillis(), new Intent(getApplicationContext(), SendSmsManager.class), 0);
 
 					  new SendSmsTask().execute(phone, null, sbud.toString(), pIntent);
 					  alertDialog.dismiss();
			    	  			    	 
		    	  }
             } 
		});
 
		alertDialog.show();
	}
	
	/**
	 * Send sms with different thread
	 * @author Michael.Wang
	 *
	 */
	private class SendSmsTask extends AsyncTask<Object, Void, Boolean> {
 		@Override
		protected Boolean doInBackground(Object... params) {
			String callerNum = (String)params[0];
			String senderNum = (String)params[1];
			String body = (String)params[2];
			PendingIntent sentIntent = (PendingIntent)params[3];
			GwtLog.d(TAG, "---- Send location to " + callerNum + ", " + body);
			//Toast.makeText(getApplicationContext(), "Send location to " + callerNum + ", " + body, Toast.LENGTH_LONG).show(); 
			
		 	SendSmsManager.getInstance().sendTextMessage(callerNum, senderNum, body, sentIntent);  
	 		return true;
		}
		
	}
	
	private void showView(final GPXPoint point) {
		GwtLog.d(TAG, "***** showView currentTabId= " + currentTabId);
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
					if (mRemoteInterface != null) {
						currentPoint = mRemoteInterface.getGPXPoint();
						if (currentPoint != null) {
							showView(currentPoint);
						}
					}
					else {
						// mRemoteInterface=null, meaning GPXService is not bind yet.
						speedinfoView.setText("Waiting for GPS signal ...");
					}

				} catch (Throwable t) {
					GwtLog.e(TAG, "***** Error while updating the UI with GPXService",t);
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
		info.add("Latitude: " + decformat.format(gpxPoint.latitude / 1e6));
		info.add("Lontitude: " + decformat.format(gpxPoint.longitude / 1e6));

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
	      return false;
	  }
	  

	private boolean locationIsVisible(GeoPoint geoPoint) {
		    if (geoPoint == null || mapView == null) {
		      return false;
		    }
		    GeoPoint center = mapView.getMapCenter();
		    int latSpan = mapView.getLatitudeSpan();
		    int lngSpan = mapView.getLongitudeSpan();

		    // Bottom of map view is obscured by zoom controls/buttons.
		    // Subtract a margin from the visible area:
		    GeoPoint marginBottom = mapView.getProjection().fromPixels(
		        0, mapView.getHeight());
		    GeoPoint marginTop = mapView.getProjection().fromPixels(0,
		        mapView.getHeight()
		            - mapView.getZoomButtonsController().getZoomControls().getHeight());
		    int margin =
		        Math.abs(marginTop.getLatitudeE6() - marginBottom.getLatitudeE6());
		    GeoRect rec = new GeoRect(center, latSpan, lngSpan);
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
				GwtLog.e(TAG, "Failed to add listener", e);
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
