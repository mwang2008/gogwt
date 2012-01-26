package com.gogwt.apps.tracking.activities;

import static com.gogwt.apps.tracking.GoGWTConstants.CONTENT_URI;
import static com.gogwt.apps.tracking.GoGWTConstants.LOCATION;
import static com.gogwt.apps.tracking.GoGWTConstants.START_TRACK1;
import static com.gogwt.apps.tracking.GoGWTConstants.STOP_TRACK1;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
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

public class AdminInvokeActivity extends MapActivity {
	private static final String TAG = AdminInvokeActivity.class.getSimpleName();

	private Button btnCurrentLoc, btnStartTrack, btnStopTrack;
	private TextView textRemoteSms, remoteSmsAddress;
	private SmsManager smsManager;
	private SmsContentObserver smsContentObserver = null;
	private MapView mapView;
	
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
		registerContentObservers();

		textRemoteSms = (TextView) findViewById(R.id.remoteSms);
		remoteSmsAddress = (TextView) findViewById(R.id.remoteSmsAddress);
		
		smsManager = SmsManager.getDefault();

		btnCurrentLoc = (Button) findViewById(R.id.currentLoc);
		btnStartTrack = (Button) findViewById(R.id.startTrack);
		btnStopTrack = (Button) findViewById(R.id.stopTrackBtn);

		btnCurrentLoc.setOnClickListener(btnCurrentLocListener);
		btnStartTrack.setOnClickListener(btnStartTrackListener);
		btnStopTrack.setOnClickListener(btnStopTrackListener);


		//todo: test remove it
		int latE6 = (int)(33.329998333333336*1e6);
		int lngE6 = (int)(-83.32999833333334*1e6);		
		String address = "146,S Wesley Chapel Rd,Eatonton,31024,United States";
		showMarkerOnMap(latE6, lngE6, address);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			GwtLog.i(TAG, "== Service onDestroy.");
			unregisterContentObservers();
			// isRunning = false;
			smsManager = null;
		} catch (Throwable e) {
			// ingore error
		}
	}

	private OnClickListener btnCurrentLocListener = new OnClickListener() {
		public void onClick(View v) {
			EditText phoneNumberText = (EditText) findViewById(R.id.phone);
			String phone = phoneNumberText.getText().toString();
			if (!StringUtils.isSet(phone)) {
				Toast.makeText(getApplicationContext(),
						"Please enter phone number", Toast.LENGTH_LONG).show();
				return;
			}

			/* todo: 
			final PendingIntent pIntent = PendingIntent.getActivity(
					getApplicationContext(), (int) System.currentTimeMillis(),
					new Intent(getApplicationContext(),
							AdminInvokeActivity.class), 0);
			final String body = LOCATION;
			GwtLog.d(TAG, "=== sendSmsBackToSender:sendLocationWithSms number="
					+ phone + ", body=" + body);
			smsManager.sendTextMessage(phone, null, body, pIntent, null);
			*/
			
			//todo: test remove it
			int latE6 = (int)(33.329998333333336*1e6);
			int lngE6 = (int)(-83.32999833333334*1e6);
			showMarkerOnMap(latE6, lngE6, "sbud.toString()");
			
			//test reverse geocode
			//String str = "http://map.google.com/?q=33.329998333333336,-83.32999833333334";
			//new ReverseGeocodeTask().execute(str);			
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
			final String body = START_TRACK1;
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
			final String body = STOP_TRACK1;
			GwtLog.d(TAG, "=== sendSmsBackToSender:sendLocationWithSms number="
					+ phone + ", body=" + body);
			smsManager.sendTextMessage(phone, null, body, pIntent, null);
		}
	};

	/**
	 * monitor the changes of content://sms
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
	 * 
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
	 * filter on when sent event or read sms action. read [0:1] 0 -- new, 1 --
	 * read type [1:2] 1 -- receive(inbox), 2 -- send
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

		/*
		 * whereBuf.append("(type="+TYPE_SENT); whereBuf.append(" or ");
		 * whereBuf.append("type="+TYPE_RECEIVE + ") and date > " +
		 * lastTimeSMSRetrive);
		 * 
		 * whereBuf.append(" (type="+TYPE_RECEIVE); whereBuf.append(" and ");
		 * whereBuf.append(" read="+READ_READ +")");
		 */

		Cursor mCurSms = contentResolver.query(Uri.parse(CONTENT_URI),
				projection, whereBuf.toString(), null, "DATE desc");

		// Cursor mCurSms =
		// contentResolver.query(Uri.parse(CONTENT_URI),null,null,null,"DATE desc");

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

				sendSmsBackToSender(smsData);

				String str = "=== address: " + smsData.getAddress();
				str += " date: " + smsData.getDate() + " : ";
				str += mCurSms.getLong(dateCol);
				str += " read: " + smsData.getRead();
				str += " type: " + smsData.getType();
				str += " body: " + smsData.getBody();
				str += " startTime: " + smsData.getStartTime();

				GwtLog.d("====== MySmsService testSMS ", str);

			} while (mCurSms.moveToNext());
		}
		mCurSms.close();
	}

	/**
	 * Display message from remote
	 * 
	 * @param smsData
	 */
	private synchronized void sendSmsBackToSender(GSmsData smsData)
			throws Throwable {
		String body = smsData.getBody();
		if (!StringUtils.isSet(body)) {
			return;
		}

		if (body.contains("maps.google.com")) {
			// display google map
			new ReverseGeocodeTask().execute(body);

			return;
		}

		
	}

	//=== showResult Address[addressLines=[0:"146 S Wesley Chapel Rd",1:"Eatonton, GA 31024",2:"USA"],
	//feature=146,admin=Georgia,sub-admin=null,locality=Eatonton,
	//thoroughfare=S Wesley Chapel Rd,postalCode=31024,countryCode=US,
	//countryName=United States,hasLatitude=true,latitude=33.331929,hasLongitude=true,
	//longitude=-83.327283,phone=null,url=null,extras=null]
	//=== showResult 146 S Wesley Chapel Rd
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
		
       
		/*
		//show on the map
		textGoogleMapLink.setText(wrapper.input);
		textGoogleMapLink.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toIntent = new Intent(getApplicationContext(), ShowMarkerOnMapActivity.class);
				toIntent.putExtra("lat", wrapper.latPoint);
				toIntent.putExtra("lng", wrapper.lngPoint);
				
				startActivity(toIntent);
			}
		});*/
		
		int latE6 = (int)(wrapper.latPoint*1e6);
		int lngE6 = (int)(wrapper.lngPoint*1e6);
		showMarkerOnMap(latE6, lngE6, sbud.toString()+2);

 	}

	/**
	 *  showResult 146,S Wesley Chapel Rd,Eatonton,31024,United States
	 * @param latE6
	 * @param lngE6
	 */
	private void showMarkerOnMap(int latE6, int lngE6, String address) {
	 	//String latlng = latE6/1e6 + "," + lngE6/1e6;
		//textRemoteSms.setText(latlng);
		remoteSmsAddress.setText(address);
		mapView.setVisibility(View.VISIBLE);
		
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

}
