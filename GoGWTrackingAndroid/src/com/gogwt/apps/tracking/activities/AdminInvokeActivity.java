package com.gogwt.apps.tracking.activities;

import static com.gogwt.apps.tracking.GoGWTConstants.CONTENT_URI;
import static com.gogwt.apps.tracking.GoGWTConstants.GOGWT_TAG;
import static com.gogwt.apps.tracking.GoGWTConstants.*;
import static com.gogwt.apps.tracking.GoGWTConstants.START_TRACK1;
import static com.gogwt.apps.tracking.GoGWTConstants.STOP_TRACK1;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gogwt.apps.tracking.R;
import com.gogwt.apps.tracking.data.GSmsData;
import com.gogwt.apps.tracking.utils.GwtLog;
import com.gogwt.apps.tracking.utils.StringUtils;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

/**
 * Used by group owner
 * @author michael.wang
 *
 */
public class AdminInvokeActivity extends MapActivity {
	private static final String TAG = AdminInvokeActivity.class.getSimpleName();

	public static final String ACTION_NAME = AdminInvokeActivity.class.getSimpleName();
	
	private Button btnCurrentLoc, btnStartTrack, btnStopTrack;
	private TextView textRemoteSms, remoteSmsAddress;
	private EditText phoneNumberText;
	private SmsManager smsManager;
	private SmsContentObserver smsContentObserver = null;
	private MapView mapView;
	private static boolean isOnForeground;
	private IntentFilter mIntentFilter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// We don't need a window title bar:
	    //requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTitle("Remote Activity");

		setContentView(R.layout.admin_invoke_layout);
		
		// setup map view
	    mapView = (MapView) findViewById(R.id.mapviewX);		 
		mapView.setBuiltInZoomControls(true);
		mapView.setVisibility(View.INVISIBLE);
		
		GwtLog.i(TAG, "==== Service onCreate start.");
		//registerContentObservers();  

		textRemoteSms = (TextView) findViewById(R.id.remoteSms);
		remoteSmsAddress = (TextView) findViewById(R.id.remoteSmsAddress);
		
		phoneNumberText = (EditText) findViewById(R.id.phone);
		
		smsManager = SmsManager.getDefault();

		btnCurrentLoc = (Button) findViewById(R.id.currentLoc);
		btnStartTrack = (Button) findViewById(R.id.startTrack);
		btnStopTrack = (Button) findViewById(R.id.stopTrackBtn);

		btnCurrentLoc.setOnClickListener(btnCurrentLocListener);
		btnStartTrack.setOnClickListener(btnStartTrackListener);
		btnStopTrack.setOnClickListener(btnStopTrackListener);

	    mIntentFilter = new IntentFilter();
	    mIntentFilter.addAction(ACTION_NAME);

		//todo: test remove it
		/*
		int latE6 = (int)(33.329998333333336*1e6);
		int lngE6 = (int)(-83.32999833333334*1e6);		
		String address = "146,S Wesley Chapel Rd,Eatonton,31024,United States";
		showMarkerOnMap(latE6, lngE6, address);
        */
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
			GwtLog.d(TAG, "=== sendSmsBackToSender:sendLocationWithSms number="
					+ phone + ", body=" + body);
			smsManager.sendTextMessage(phone, null, body, pIntent, null);
			
			phoneNumberText.setText(phone);
 
		}
	};

	private OnClickListener btnStartTrackListener = new OnClickListener() {
		public void onClick(View v) {
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
			GwtLog.d(TAG, "=== sendSmsBackToSender:sendLocationWithSms number="
					+ phone + ", body=" + body);
			smsManager.sendTextMessage(phone, null, body, pIntent, null);
		}
	};

	private OnClickListener btnStopTrackListener = new OnClickListener() {
		public void onClick(View v) {
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
			GwtLog.d(TAG, "=== sendSmsBackToSender:sendLocationWithSms number="
					+ phone + ", body=" + body);
			smsManager.sendTextMessage(phone, null, body, pIntent, null);
		}
	};

	/**
	 * monitor the changes of content://sms
	 * @deprecated
	 */
	private void registerContentObservers() {
		GwtLog.i(TAG, "registerContentObservers");

		if (smsContentObserver == null) {
			ContentResolver contentResolver = getContentResolver();
			smsContentObserver = new SmsContentObserver(new Handler());
			Uri smsUri = Uri.parse(CONTENT_URI);
			contentResolver.registerContentObserver(smsUri, true,
					smsContentObserver);
		}
	}

	/**
	 * @deprecated
	 */
	private void unregisterContentObservers() {
		Log.i(TAG, "unregisterContentObservers");
		if (smsContentObserver != null) {
			ContentResolver contentResolver = getContentResolver();
			contentResolver.unregisterContentObserver(smsContentObserver);
			smsContentObserver = null;
		}
	}

	/**
	 * Invoked whenever user send/receive sms
	 * 
	 * @author michael.wang
	 * @deprecated
	 */
	class SmsContentObserver extends ContentObserver {
		public SmsContentObserver(Handler h) {
			super(h);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Log.i(TAG, "SmsContentObserver.onChange( " + selfChange + ")");

			// catch all exception, do not want SMS function to affect others.
			// as SMS function is undocument.
			try {
				chechSMS();
			} catch (Throwable e) {
				// skip, nothing
				GwtLog.d(TAG, "onChange " + e.getMessage());
			}
		}

		@Override
		public boolean deliverSelfNotifications() {
			return true;
		}
	}

	/**
	 * whereBuf.append("(type="+TYPE_SENT); whereBuf.append(" or ");
	 * whereBuf.append("type="+TYPE_RECEIVE + ") and date > " +
	 * lastTimeSMSRetrive);
	 * 
	 * whereBuf.append(" (type="+TYPE_RECEIVE); whereBuf.append(" and ");
	 * whereBuf.append(" read="+READ_READ +")");
	
	 * filter on when sent event or read sms action. read [0:1] 0 -- new, 1 --
	 * read type [1:2] 1 -- receive(inbox), 2 -- send
	 * @deprecated
	 */
	private void chechSMS() throws Throwable {
		final int TYPE_RECEIVE = 1;
		final int TYPE_SENT = 2;
		final int READ_READ = 1;
		final int READ_UNREAD = 0;

		ContentResolver contentResolver = getContentResolver();

		// type 1=inbox : 2=send
		// read 0=new : 1=read
		String[] projection = { "address", "date", "read", "type", "body" };
		StringBuilder whereBuf = new StringBuilder();
		whereBuf.append("type=" + TYPE_RECEIVE);
 
		Cursor mCurSms = contentResolver.query(Uri.parse(CONTENT_URI),
				projection, whereBuf.toString(), null, "DATE desc");

		int addressCol = mCurSms.getColumnIndex("address");
		int dateCol = mCurSms.getColumnIndex("date");
		int readCol = mCurSms.getColumnIndex("read");
		int typeCol = mCurSms.getColumnIndex("type");
		int bodyCol = mCurSms.getColumnIndex("body"); // "body"
		mCurSms.moveToFirst();
		long currItemDate;
		if (mCurSms.getCount() > 0) {
			do {
				currItemDate = mCurSms.getLong(dateCol);

				GSmsData smsData = new GSmsData();

				smsData.address = mCurSms.getString(addressCol);
				smsData.date = new Date(currItemDate);
				smsData.read = mCurSms.getInt(readCol);
				smsData.type = mCurSms.getInt(typeCol);

				smsData.body = mCurSms.getString(bodyCol);

				sendSmsBackToSenderXXX(smsData);

				String str = "=== address: " + smsData.getAddress();
				str += " date: " + smsData.getDate() + " : ";
				str += mCurSms.getLong(dateCol);
				str += " read: " + smsData.getRead();
				str += " type: " + smsData.getType();
				str += " body: " + smsData.getBody();
				str += " startTime: " + smsData.getStartTime();

				GwtLog.d(TAG, "==== AdminInvokeActivity " + str);

			} while (mCurSms.moveToNext());
		}
		mCurSms.close();
	}

	/**
	 * Display message from remote
	 * 
	 * @param smsData
	 * @deprecated
	 */
	private synchronized void sendSmsBackToSenderXXX(GSmsData smsData)
			throws Throwable {
		String body = smsData.getBody();
		if (!StringUtils.isSet(body)) {
			return;
		}

		if (body.startsWith(ADMIN)) {
			if (body.contains("maps.google.com")) {
				// display google map			
				new ReverseGeocodeTask().execute(body);
			}
			else {
				mapView.setVisibility(View.INVISIBLE);
				textRemoteSms.setText(smsData.getBody());
				textRemoteSms.setTextColor(getResources().getColor(R.color.white));
				remoteSmsAddress.setText("");
			}		 
		}
	}

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
		
		Address result = wrapper.address;
		if (result == null) {
			return;
		}
		
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
 
		GwtLog.d(TAG, "=== showResult " + sbud.toString());
		
 		//show on the map	 
		int latE6 = (int)(wrapper.latPoint*1e6);
		int lngE6 = (int)(wrapper.lngPoint*1e6);
		showMarkerOnMap(latE6, lngE6, sbud.toString());

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
				try {
					Geocoder myLocation = new Geocoder(getApplicationContext(),	Locale.getDefault());
					List<Address> myList = myLocation.getFromLocation(latPoint,	lngPoint, 1);
					 
					AddressWraper addressWraper = new AddressWraper(body, myList.get(0), latPoint, lngPoint);
					return addressWraper;
				} catch (Throwable e) {
					GwtLog.d(TAG, "=== ReverseGeocodeTask error= "+e.getMessage());			
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
}
