package com.gogwt.apps.tracking.activities;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gogwt.apps.tracking.AbstractAsyncActivity;
import com.gogwt.apps.tracking.R;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.response.LoginResponse;
import com.gogwt.apps.tracking.services.http.ServerURLFactory;
import com.gogwt.apps.tracking.utils.GwtLog;
import com.gogwt.apps.tracking.utils.SessionManager;
import com.gogwt.apps.tracking.utils.StringUtils;
import com.gogwt.apps.tracking.utils.secure.HttpUtils;

public class LoginActivity extends AbstractAsyncActivity {
	protected static final String TAG = LoginActivity.class.getSimpleName();
	private TextView helpView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
  
		// Initiate the POST request when the button is selected
		final Button buttonxml = (Button) findViewById(R.id.login);
		buttonxml.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// validate inputs
				Profile profile = validate();
				if (profile != null) {
					new SendLoginTask().execute(profile);
				}
			}
		});		
		
		final Button singupBtn = (Button) findViewById(R.id.signup);
		singupBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent toLoginIntent = new Intent(getApplicationContext(), EnrollActivity.class);        
		        startActivity(toLoginIntent);
			}
		});	
		
		helpView = (TextView)findViewById(R.id.help);
		helpView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View paramView) {
				//Toast.makeText(getApplicationContext(), "Help/Demo", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(getApplicationContext(), HelpActivity.class));
			}			
		});
		
	
    }
    
	// ***************************************
	// Private method: call back

	// ***************************************
	private void showResult(LoginResponse result) {

		Log.i(TAG, " ---  " + result.toString());

		if (result.getStatus().getCode() == 200) {
			Toast.makeText(this, "Success logged in", Toast.LENGTH_LONG).show();

/*			if (result.getProfile().isRememberMe()) {
				result.getProfile().setPhoneNumber(getMyPhoneNumber());								
			}*/

			result.getProfile().setPhoneNumber(getMyPhoneNumber());	
			
			//for quick access, use session
			SessionManager.saveProfile(getApplicationContext(), result.getProfile());

			// redirect to MainActive
			Intent toMainIntent = new Intent(getApplicationContext(),
					MainMenuActivity.class);
			// startActivityForResult(myIntent, 0);
			startActivity(toMainIntent);
		} else {
			//Toast.makeText(this, result.getStatus().getMsg(), Toast.LENGTH_LONG).show();
			//show error message
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Error Message");
			alertDialog.setMessage(result.getStatus().getMsg());
			
			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
			      public void onClick(DialogInterface dialog, int which) {
			    	  Toast.makeText(getApplicationContext(), "Continue", Toast.LENGTH_SHORT)
						.show();   
                  } 
			});
	 
			alertDialog.show();
		}
	}

	private Profile validate() {
		EditText groupIdText = (EditText) findViewById(R.id.groupId);
		EditText displayNameText = (EditText) findViewById(R.id.displayName);
	 	CheckBox remembermeBox = (CheckBox) findViewById(R.id.rememberme);

		String groupId = groupIdText.getText().toString(); // .toString();
		String displayName = displayNameText.getText().toString();
		boolean rememberMe = remembermeBox.isChecked();

		// validate
		StringBuilder sbud = new StringBuilder();
		if (groupId != null) {
			groupId = groupId.trim();
		}
		boolean hasError = false;
		if (!StringUtils.isSet(groupId)) {
			hasError = true;
			sbud.append("Please enter GroupId");
		}
		
		if (displayName != null) {
			displayName = displayName.trim();
		}
		if (!StringUtils.isSet(displayName)) {
			if (hasError) {
				sbud.append("\n");
			}
			sbud.append("Please enter Display Name. (ex. Joe)");
			hasError = true;
			
		}

	 

		if (hasError) {
			Toast.makeText(getApplicationContext(), sbud.toString(),
					Toast.LENGTH_LONG).show();
			return null;
		}

		Profile profile = new Profile(groupId, displayName, rememberMe);

		return profile;

	}

	private class SendLoginTask extends AsyncTask<Profile, Void, LoginResponse> {

		/**
		 * Validate the input
		 */
		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog("Logging in, Please wait... ");

		}

		@Override
		protected LoginResponse doInBackground(Profile... profileArray) {
			// MediaType.APPLICATION_JSON
			try {

				Profile profile = profileArray[0];
				
				MediaType mediaType = MediaType.APPLICATION_JSON;
				 
				final String url = ServerURLFactory.getLoginURL(); //REST_SIGNIN_URL;
				GwtLog.i(TAG, " ---  url=" + url);
				
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setContentType(mediaType);

				HttpEntity<Profile> requestEntity = new HttpEntity<Profile>(
						profile, requestHeaders);

				HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(HttpUtils.getNewHttpClient());
				requestFactory.setReadTimeout(SOCKET_TIMEOUT);
 			
				RestTemplate restTemplate = new RestTemplate(requestFactory);
				 
				ResponseEntity<LoginResponse> response = restTemplate.exchange(
						url, HttpMethod.POST, requestEntity,
						LoginResponse.class);

				return response.getBody();

			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
				
			}
			
			//server error 
			String message = "Server has problem at this moment, please try again";
			
			LoginResponse loginResponse = new LoginResponse();
			com.gogwt.apps.tracking.data.Status status = new com.gogwt.apps.tracking.data.Status();
			status.setCode(602);
			status.setMsg(message);
			loginResponse.setStatus(status);
			
			return loginResponse;
		}

		@Override
		protected void onPostExecute(LoginResponse result) {
			// after the network request completes, hid the progress indicator
			dismissProgressDialog();

			// return the response body to the calling class
			try {
			   showResult(result);
			}
			catch (Throwable e) {
				//not want to 
			}
		}
	}
	
	private String getMyPhoneNumber(){
	    TelephonyManager mTelephonyMgr;
	    mTelephonyMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); 
	    return mTelephonyMgr.getLine1Number();
	}
}