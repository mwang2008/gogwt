package com.gogwt.apps.tracking.activities;

import static com.gogwt.apps.tracking.GoGWTConstants.ADMIN;
import static com.gogwt.apps.tracking.GoGWTConstants.LOCATION;
import static com.gogwt.apps.tracking.GoGWTConstants.START_TRACK;
import static com.gogwt.apps.tracking.GoGWTConstants.STOP_TRACK;

import java.util.List;
import java.util.Locale;

import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gogwt.apps.tracking.R;
import com.gogwt.apps.tracking.data.GSmsData;
import com.gogwt.apps.tracking.provider.QuickContactSearcher;
import com.gogwt.apps.tracking.provider.QuickContactSearcher.MyContact;
import com.gogwt.apps.tracking.utils.GwtLog;
import com.gogwt.apps.tracking.utils.SessionManager;
import com.gogwt.apps.tracking.utils.StringUtils;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

/**
 * Functions used by group owner
 * @author michael.wang
 *
 */
public class AdminInvokeActivity extends MapActivity implements PhoneContactIF {
	private static final String TAG = AdminInvokeActivity.class.getSimpleName();

	public static final String ACTION_NAME = AdminInvokeActivity.class.getSimpleName();
	
	private Button btnCurrentLoc, btnStartTrack, btnStopTrack;
	private TextView textRemoteSms, remoteSmsAddress;
	private EditText phoneNumberText;
	private SmsManager smsManager;	
	private MapView mapView;
	private static boolean isOnForeground;
	private IntentFilter mIntentFilter;
 	private LinearLayout phonesecLayout;
    private static String lastPhoneNum;
     
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// We don't need a window title bar:
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setTitle("Remote Activity");

		setContentView(R.layout.admin_invoke_layout);
		
		phonesecLayout = (LinearLayout)this.findViewById(R.id.phonesec);		
	    phonesecLayout.setVisibility(View.GONE);
	    
		// setup map view
	    mapView = (MapView) findViewById(R.id.mapviewX);		 
		mapView.setBuiltInZoomControls(true);
		mapView.setVisibility(View.INVISIBLE);
		
		GwtLog.i(TAG, "==== Service onCreate start.");
		//registerContentObservers();  

		textRemoteSms = (TextView) findViewById(R.id.remoteSms);
		remoteSmsAddress = (TextView) findViewById(R.id.remoteSmsAddress);
		
		phoneNumberText = (EditText) findViewById(R.id.phone);
		if (StringUtils.isSet(lastPhoneNum)) {
			phoneNumberText.setText(lastPhoneNum);
		}
		smsManager = SmsManager.getDefault();

		btnCurrentLoc = (Button) findViewById(R.id.currentLoc);
		btnStartTrack = (Button) findViewById(R.id.startTrack);
		btnStopTrack = (Button) findViewById(R.id.stopTrackBtn);

		btnCurrentLoc.setOnClickListener(btnCurrentLocListener);
		btnStartTrack.setOnClickListener(btnStartTrackListener);
		btnStopTrack.setOnClickListener(btnStopTrackListener);

	    mIntentFilter = new IntentFilter();
	    mIntentFilter.addAction(ACTION_NAME);

	 
	    
		Intent intent = getIntent();
		 
		
		//"from_intent"
		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			// user selects from contact suggestion list 
			String name = intent.getDataString(); 
			procSelectedContacts(name);
			
		} else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			// user clicks search without select from suggestion list
			String query = intent.getStringExtra(SearchManager.QUERY);
		  	GwtLog.i(TAG, "--- onCreate ACTION_SEARCH query=" + query);
		  	
		  	//search from contact repository, not necessary as SuggestionProvider already did that
   	    }
		
		Button searchBtn = (Button) findViewById(R.id.search);
		searchBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//invoke SuggestionProvider
				onSearchRequested();
			}
		});
		
		//todo: test remove it
		/*
		int latE6 = (int)(33.329998333333336*1e6);
		int lngE6 = (int)(-83.32999833333334*1e6);		
		String address = "146,S Wesley Chapel Rd,Eatonton,31024,United States";
		showMarkerOnMap(latE6, lngE6, address);
        */
	}

	/**
	 * Request phone list, if the person has multiple phone number, show the list
	 * otherwise hide show list section and set phone number to phoneNumberText
	 * @param contactName
	 */
	private void procSelectedContacts(String contactName) {
		
		List<MyContact> myPhoneContacts = QuickContactSearcher.getInstance().searchPhonesByPartialName(this, contactName);
        
		if (myPhoneContacts == null || myPhoneContacts.isEmpty()) {
	        Toast.makeText(this, "Could not find, please type in phone number", Toast.LENGTH_LONG).show();
	        return;
        }

        if (myPhoneContacts.size() == 1) {
        	phoneNumberText.setText(myPhoneContacts.get(0).number);
	        return;
        }

        phonesecLayout.setVisibility(View.VISIBLE);
       
        ListView phoneList = (ListView) findViewById(R.id.phoneList);
    	TextView contactNameView = (TextView)this.findViewById(R.id.contactName);

        contactNameView.setText(contactName);
        Object dd = AdminInvokeActivity.class;
        
        PhoneAdapter phoneAdapter = new PhoneAdapter(false, myPhoneContacts, getApplicationContext(), this);
        phoneList.setAdapter(phoneAdapter);
        phoneList.setOnItemClickListener(phoneAdapter);
        //phoneList.setVisibility(View.VISIBLE);
        
	}
	
	/**
	 * After user click phone : Mobile phonenumber 
	 * @param theContact
	 */
	public void handlePhoneList(QuickContactSearcher.MyContact theContact, boolean isContact) {		
		phoneNumberText.setText(theContact.number);
		phonesecLayout.setVisibility(View.GONE);
	}

	private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	
	    	GSmsData theGSmsData = (GSmsData)intent.getExtras().get(AdminInvokeActivity.ACTION_NAME+"_SMS_DATA");
	    	
	    	if (theGSmsData == null) {
	    		GwtLog.d(TAG, "== BroadcastReceiver onReceive intent.getExtras return null");
	    	}
	    	
	    	String body = theGSmsData.getBody();    	
	    	GwtLog.d(TAG, "== before mIntentReceiver onReceive " + body);
	    	if (!StringUtils.isSet(body)) {
				return;
			}
 	    	//Toast.makeText(getApplicationContext(), fromSmsReciever, Toast.LENGTH_LONG).show(); 
			
			if (body.startsWith(ADMIN)) {
				if (body.contains("maps.google.com")) {
					// display google map			
					new ReverseGeocodeTask().execute(body);
				}
				else {
					//remove signature
					body = body.replaceAll(ADMIN, "");
					mapView.setVisibility(View.INVISIBLE);
					textRemoteSms.setText(body);
					textRemoteSms.setTextColor(getResources().getColor(R.color.white));
					remoteSmsAddress.setText("");
				}		 
			}
	    	GwtLog.d(TAG, "== after mIntentReceiver onReceive " + body);
	    }
	};

	@Override
	protected void onResume() {
	   GwtLog.d(TAG, "== onPause.");
	   registerReceiver(mIntentReceiver, mIntentFilter);
	   isOnForeground = true;
	   super.onResume();
	}

	@Override
	protected void onPause() {
	   GwtLog.d(TAG, "== onPause.");
	   unregisterReceiver(mIntentReceiver);
	   isOnForeground = false;
	   super.onPause();
	}

	
	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			GwtLog.i(TAG, "== Service onDestroy.");
			//unregisterContentObservers();
			// isRunning = false;
			smsManager = null;
		} catch (Throwable e) {
			GwtLog.d(TAG, "== error unregisterContentObservers" + e.getMessage());
			// ingore error
		}
	}
	 
	 

	private OnClickListener btnCurrentLocListener = new OnClickListener() {
		public void onClick(View v) {
			phonesecLayout.setVisibility(View.GONE);
			
			String phone = phoneNumberText.getText().toString();
			if (!StringUtils.isSet(phone)) {
				Toast.makeText(getApplicationContext(),
						"Please enter phone number", Toast.LENGTH_LONG).show();
				return;
			}
			
			final PendingIntent pIntent = PendingIntent.getActivity(
					getApplicationContext(), (int) System.currentTimeMillis(),
					new Intent(getApplicationContext(),
							AdminInvokeActivity.class), 0);
			final String body = LOCATION;
			GwtLog.d(TAG, "=== sendSmsBackToSender:sendLocationWithSms number=" + phone + ", body=" + body);
			
			
			//new MessageSendingManager().sendTextMessage(smsManager, getApplicationContext(), phone, null, body, pIntent, null);
			smsManager.sendTextMessage(phone, null, body, pIntent, null);
			
			/*if (C2DMUtils.hasRegistrationId(getApplicationContext())) {
				//send message to app server bridge in turn Google C2DM server
				C2DMMessageSender.getInstance().sendMessageToServer(getApplicationContext(), phone, body);
			}
			else {
				smsManager.sendTextMessage(phone, null, body, pIntent, null);	
			}*/
			
			
			phoneNumberText.setText(phone);
			lastPhoneNum = phone;

			String sentTime = android.text.format.DateFormat.format(
					"hh:mm:ss", new java.util.Date()).toString();
			String notifyMsg = "The message is sent out for location @ " + sentTime;
			Toast.makeText(getApplicationContext(),
					notifyMsg, Toast.LENGTH_LONG).show();
			 
		}
	};

	private OnClickListener btnStartTrackListener = new OnClickListener() {
		public void onClick(View v) {
			phonesecLayout.setVisibility(View.GONE);
			EditText phoneNumberText = (EditText) findViewById(R.id.phone);
			String phone = phoneNumberText.getText().toString();
			if (!StringUtils.isSet(phone)) {
				Toast.makeText(getApplicationContext(),
						"Please enter phone number", Toast.LENGTH_LONG).show();
				return;
			}

			final PendingIntent pIntent = PendingIntent.getActivity(
					getApplicationContext(), (int) System.currentTimeMillis(),
					new Intent(getApplicationContext(),
							AdminInvokeActivity.class), 0);
			final String body = START_TRACK;
			GwtLog.d(TAG, "=== sendSmsBackToSender:sendLocationWithSms number="	+ phone + ", body=" + body);
			
			smsManager.sendTextMessage(phone, null, body, pIntent, null);
			//new MessageSendingManager().sendTextMessage(smsManager, getApplicationContext(), phone, null, body, pIntent, null);
			
			lastPhoneNum = phone;
			
			String sentTime = android.text.format.DateFormat.format(
					"hh:mm:ss", new java.util.Date()).toString();
			String notifyMsg = "The message is sent out to start remote tracking @ " + sentTime;
			Toast.makeText(getApplicationContext(),
					notifyMsg, Toast.LENGTH_LONG).show();
		}
	};

	private OnClickListener btnStopTrackListener = new OnClickListener() {
		public void onClick(View v) {
			phonesecLayout.setVisibility(View.GONE);
			EditText phoneNumberText = (EditText) findViewById(R.id.phone);
			String phone = phoneNumberText.getText().toString();
			if (!StringUtils.isSet(phone)) {
				Toast.makeText(getApplicationContext(),
						"Please enter phone number", Toast.LENGTH_LONG).show();
				return;
			}

			final PendingIntent pIntent = PendingIntent.getActivity(
					getApplicationContext(), (int) System.currentTimeMillis(),
					new Intent(getApplicationContext(),
							AdminInvokeActivity.class), 0);
			final String body = STOP_TRACK;
			
			GwtLog.d(TAG, "=== sendSmsBackToSender:sendLocationWithSms number="	+ phone + ", body=" + body);
			
			smsManager.sendTextMessage(phone, null, body, pIntent, null);
			//new MessageSendingManager().sendTextMessage(smsManager, getApplicationContext(), phone, null, body, pIntent, null);
			
			lastPhoneNum = phone;
			
			String sentTime = android.text.format.DateFormat.format(
					"hh:mm:ss", new java.util.Date()).toString();
			String notifyMsg = "The message is sent out to stop remote tracking @ " + sentTime;
			Toast.makeText(getApplicationContext(),
					notifyMsg, Toast.LENGTH_LONG).show();
		}
	};

   

	//=== showResult Address[addressLines=[0:"146 S Wesley Chapel Rd",1:"Eatonton, GA 31024",2:"USA"],
	//feature=146,admin=Georgia,sub-admin=null,locality=Eatonton,
	//thoroughfare=S Wesley Chapel Rd,postalCode=31024,countryCode=US,
	//countryName=United States,hasLatitude=true,latitude=33.331929,hasLongitude=true,
	//longitude=-83.327283,phone=null,url=null,extras=null]
	//=== showResult 146 S Wesley Chapel Rd
	/**
	 * ReverseGeocodeTask call back
	 */
	private void showResult(final AddressWraper wrapper) {
		GwtLog.d(TAG, "=== showResult ");
		
		StringBuilder sbud = convertAddressToString(wrapper);
		
	
		GwtLog.d(TAG, "=== showResult= " + sbud.toString());
		
 		//show on the map	 
		int latE6 = (int)(wrapper.latPoint*1e6);
		int lngE6 = (int)(wrapper.lngPoint*1e6);
		showMarkerOnMap(latE6, lngE6, sbud.toString());

 	}

	private StringBuilder convertAddressToString(final AddressWraper wrapper) {
		if (wrapper == null || wrapper.address == null) {
			return new StringBuilder(" ");			
		}
		
		Address result = wrapper.address;
 		
		StringBuilder sbud = new StringBuilder();
		boolean hasVal = false;
		if (StringUtils.isSet(result.getFeatureName())) {
			sbud.append(result.getFeatureName());
			hasVal = true;
		}
		if (StringUtils.isSet(result.getThoroughfare())) {
			if (hasVal) {
				sbud.append(",");
			}
			sbud.append(result.getThoroughfare());
		}
		if (StringUtils.isSet(result.getLocality())) {
			if (hasVal) {
				sbud.append(",");
			}
			sbud.append(result.getLocality());
		}
		if (StringUtils.isSet(result.getPostalCode())) {
			if (hasVal) {
				sbud.append(",");
			}
			sbud.append(result.getPostalCode());
		}
		if (StringUtils.isSet(result.getCountryName())) {
			if (hasVal) {
				sbud.append(",");
			}
			sbud.append(result.getCountryName());
		}
 
		return sbud;
	}
	
	/**
	 * showResult 146,S Wesley Chapel Rd,Eatonton,31024,United States
	 * tap action is on  DrawMarkerOverlay
	 * @param latE6
	 * @param lngE6
	 */
	private void showMarkerOnMap(int latE6, int lngE6, String address) {
	 	String latlng = latE6/1e6 + "," + lngE6/1e6;
		//textRemoteSms.setText(latlng);
	 	address += "\n(" + latlng + ")";
	 	
	 	GwtLog.d(TAG, "=== address= " + latlng);
		remoteSmsAddress.setText(address);
		mapView.setVisibility(View.VISIBLE);
		
		String mapInstruction = getResources().getString(R.string.mapInstruction);
		textRemoteSms.setText(mapInstruction);
		textRemoteSms.setTextColor(getResources().getColor(R.color.black));
		 
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.google_green_arrow);
		DrawMarkerOverlay itemizedOverlay = new DrawMarkerOverlay(drawable, this);
		
		itemizedOverlay.lat = latE6/1e6;
		itemizedOverlay.lng = lngE6/1e6;
		
	 	GeoPoint point = new GeoPoint(latE6, lngE6);
		OverlayItem overlayitem = new OverlayItem(point, "Location", address);
        
        itemizedOverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedOverlay);
        
        MapController mapController = mapView.getController();
        mapController.animateTo(point);
        mapController.setZoom(12);
       
	}
	/**
	 * 
	 * @author michael.wang
	 * 
	 */
	private class ReverseGeocodeTask extends AsyncTask<String, Void, AddressWraper> {

		@Override
		protected AddressWraper doInBackground(String... params) {
			String body = params[0];
			GwtLog.d(TAG, "=== ReverseGeocodeTask body= "+body);
			
			int qPos = body.indexOf("q=");
			if (qPos != -1) {
				body = body.substring(qPos + 2);
				String[] sp = body.split(",");
				String lat = sp[0];
				String lng = sp[1];
				double latPoint = Double.parseDouble(lat);
				double lngPoint = Double.parseDouble(lng);
				AddressWraper addressWraper = null;
				try {
					Geocoder myLocation = new Geocoder(getApplicationContext(),	Locale.getDefault());
					List<Address> myList = myLocation.getFromLocation(latPoint,	lngPoint, 1);
					 
					addressWraper = new AddressWraper(body, myList.get(0), latPoint, lngPoint);
					return addressWraper;
				} catch (Throwable e) {
					GwtLog.d(TAG, "=== ReverseGeocodeTask error= "+e.getMessage());
					addressWraper = new AddressWraper(null, null, latPoint, lngPoint);
					
					return addressWraper;
				}				
			}
			return null;
		}

		@Override
		protected void onPostExecute(AddressWraper result) {
		 
			// return the response body to the calling class
			try {
				showResult(result);
			} catch (Throwable e) {
				// not want to
				GwtLog.d(TAG, "=== ReverseGeocodeTask onPostExecute= "+e.getMessage());	
			}
		}

	}
	
	class AddressWraper {
		Address address;
		String  input;
		double latPoint;
		double lngPoint;
		
		public AddressWraper(String pInput, Address pAddress, double latPoint, double lngPoint) {
			input = pInput;
			address = pAddress;
			this.latPoint = latPoint;
			this.lngPoint = lngPoint;
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isOnForeground() {
		return isOnForeground;
	}	
	
	/**
	 * Load menu
	 */
	@Override  
	public boolean onCreateOptionsMenu(Menu menu) {  	   
		 MenuInflater inflater = getMenuInflater();
		 inflater.inflate(R.menu.admin_menu, menu);
		 		 
		 return true;
	}  

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_back:     
	        	//Toast.makeText(this, "You pressed the admin setting!", Toast.LENGTH_LONG).show();
	        	Class<?> backTo = SessionManager.getFromActivityCls();
	        	if (backTo != null) {
	        		startActivity(new Intent(this, backTo)); 
	        		SessionManager.setFromActivityCls(null);
	        	}
	        	break;
	        default:
				break;	     
	    }
	    return true;
	}
}
