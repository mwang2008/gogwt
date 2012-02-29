package com.gogwt.apps.tracking.activities;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gogwt.apps.tracking.AbstractAsyncActivity;
import com.gogwt.apps.tracking.GwtConfig;
import com.gogwt.apps.tracking.R;
import com.gogwt.apps.tracking.data.EnrollCustomerFormBean;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.response.EnrollResponse;
import com.gogwt.apps.tracking.services.http.ServerURLFactory;
import com.gogwt.apps.tracking.utils.GwtLog;
import com.gogwt.apps.tracking.utils.SessionManager;
import com.gogwt.apps.tracking.utils.StringUtils;

public class EnrollActivity extends AbstractAsyncActivity {
	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-']+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]*(\\.[A-Za-z0-9-]+)*[A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)";
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enroll_layout);
		
		
		// Initiate the POST request when the button is selected
		final Button enrollBtn = (Button) findViewById(R.id.enroll);
		enrollBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// validate inputs
				EnrollCustomerFormBean bean = validate();
				if (bean != null) {
						new SendEnrollTask().execute(bean);
					}
				}
			}
		);		
    }	
    
	// ***************************************
	// Private method: call back

	// ***************************************
	private void showResult(EnrollResponse result) {

		GwtLog.i(TAG, " --- result from server " + result.getMyStr() + 
				",status="+result.getStatus().getCode());
		if (result.getResult() != null) {
			GwtLog.i(TAG, "  -- groupId=" + result.getResult().getGroupId());
			GwtLog.i(TAG, "  -- username=" + result.getResult().getUserName());
			GwtLog.i(TAG, "  -- firstname=" + result.getResult().getFirstName());
			GwtLog.i(TAG, "  -- lastname=" + result.getResult().getLastName());
			GwtLog.i(TAG, "  -- isLogin=" + result.getResult().isLogin());
		}
		
 
		if (result.getStatus().getCode() == 200) {
			Toast.makeText(this, "Success logged in", Toast.LENGTH_LONG).show();

		 	Profile profile = new Profile();
		 	profile.setGroupId(result.getResult().getGroupId());
		 	profile.setRememberMe(true);
		 	profile.setServerFirstName(result.getResult().getFirstName());
		 	profile.setServerLastName(result.getResult().getLastName());
		 	
			//for quick access, use session
			SessionManager.saveProfile(getApplicationContext(), profile);
			 
		 
			// redirect to MainActive
			Intent toMainIntent = new Intent(getApplicationContext(),
					MainMenuActivity.class);			 
			startActivity(toMainIntent);
			
		} else {
			//Toast.makeText(this, result.getStatus().getMsg(), Toast.LENGTH_LONG).show();
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
    
    private class SendEnrollTask extends AsyncTask<EnrollCustomerFormBean, Void, EnrollResponse> {

		/**
		 * Validate the input
		 */
		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog("Enrollment, Please wait... ");

		}

		@Override
		protected EnrollResponse doInBackground(EnrollCustomerFormBean... theArray) {
			// MediaType.APPLICATION_JSON
			try {

				EnrollCustomerFormBean enrollBean = theArray[0];
				
				MediaType mediaType = MediaType.APPLICATION_JSON;
				 
				final String url = ServerURLFactory.mobileEnrollURL();  
				Log.i(TAG, " ---  url=" + url);
				
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setContentType(mediaType);

				HttpEntity<EnrollCustomerFormBean> requestEntity = new HttpEntity<EnrollCustomerFormBean>(enrollBean, requestHeaders);

				HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
				requestFactory.setReadTimeout(SOCKET_TIMEOUT);
 			
				RestTemplate restTemplate = new RestTemplate(requestFactory);
				
				ResponseEntity<EnrollResponse> response = restTemplate.exchange(
						url, HttpMethod.POST, requestEntity,
						EnrollResponse.class);

				return response.getBody();

			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
				if (GwtConfig.DEBUG) {
				   e.printStackTrace();
				}
			}
			
			//server error 
			String message = "Server has problem at this moment, please try again";
			
			EnrollResponse  response = new EnrollResponse();
			com.gogwt.apps.tracking.data.Status status = new com.gogwt.apps.tracking.data.Status();
			status.setCode(602);
			status.setMsg(message);
			response.setStatus(status);	
			
			return response;
		}

		@Override
		protected void onPostExecute(EnrollResponse result) {
			// after the network request completes, hid the progress indicator
			dismissProgressDialog();

			// return the response body to the calling class
			showResult(result);
		}
	}
    
    
    /**
     * 
     * @return
     */
	private EnrollCustomerFormBean validate() {
		
		EditText groupIdText = (EditText) findViewById(R.id.groupId);
		EditText groupnameText = (EditText) findViewById(R.id.groupname);
		EditText usernameText = (EditText) findViewById(R.id.username);
		EditText firstnameText = (EditText) findViewById(R.id.firstname);
		EditText lastnameText = (EditText) findViewById(R.id.lastname);
		
		EditText phoneText = (EditText) findViewById(R.id.phone);
		EditText emailText = (EditText) findViewById(R.id.email);
		EditText passwordText = (EditText) findViewById(R.id.password);
		EditText passwordconfirmText = (EditText) findViewById(R.id.passwordconfirm);
		 
		EnrollCustomerFormBean bean = new EnrollCustomerFormBean();
		
		String groupId = groupIdText.getText().toString(); // .toString();
		String groupname = groupnameText.getText().toString();
		String username = usernameText.getText().toString();
		String firstname = firstnameText.getText().toString();
		String lastname = lastnameText.getText().toString(); //"fake" ;//lastnameText.getText().toString();
		String phone = phoneText.getText().toString();
		String email = emailText.getText().toString();
		String password = passwordText.getText().toString();
		String passwordconfirm = passwordconfirmText.getText().toString();
	
		// validate
		boolean hasError = false;
		
		StringBuilder sbud = new StringBuilder();
		if (groupId != null) {
			groupId = groupId.trim();
		}
		
		if (!StringUtils.isSet(groupId)) {
			hasError = true;
			sbud.append("Please enter Group Id");
		}
		 
		if (!StringUtils.isSet(groupname)) {
			if (hasError) {
				sbud.append("\n");
			}
			sbud.append("Please enter Group Name");
			hasError = true;		
		}

		if (!StringUtils.isSet(username)) {
			if (hasError) {
				sbud.append("\n");
			}
			sbud.append("Please enter User Name");
			hasError = true;		
		}

		if (!StringUtils.isSet(firstname)) {
			if (hasError) {
				sbud.append("\n");
			}
			sbud.append("Please enter First Name");
			hasError = true;		
		}

		if (!StringUtils.isSet(lastname)) {
			if (hasError) {
				sbud.append("\n");
			}
			sbud.append("Please enter Last Name");
			hasError = true;		
		}
		
		if (!StringUtils.isSet(phone)) {
			if (hasError) {
				sbud.append("\n");
			}
			sbud.append("Please enter Phone Number");
			hasError = true;	
		}
		
		if (!StringUtils.isSet(email)) {
			if (hasError) {
				sbud.append("\n");
			}
			sbud.append("Please enter Email");
			hasError = true;		
		}
		else {
			//check email
			if (!isValidEmailFormat(email)) {
				sbud.append("Please enter valid Email");
				hasError = true;	
			}
		}
        

		if (!StringUtils.isSet(password)) {
			if (hasError) {
				sbud.append("\n");
			}
			sbud.append("Please enter Password Name");
			hasError = true;		
		}

		if (!StringUtils.isSet(passwordconfirm)) {
			if (hasError) {
				sbud.append("\n");
			}
			sbud.append("Please enter Confirm Password");
			hasError = true;		
		}

		if (!StringUtils.equalsIgnoreCase(password, passwordconfirm)) {
			sbud.append("Password and Confirm Password is not matched");
			hasError = true;					
		}

		if (hasError) {
			Toast.makeText(getApplicationContext(), sbud.toString(),
					Toast.LENGTH_LONG).show();
			return null;
		}

		bean.setGroupId(groupId);
		bean.setGroupName(groupname);
		bean.setUserName(username);
		bean.setFirstName(firstname);
		bean.setLastName(lastname);
		bean.setPhoneNumber(phone);
		bean.setEmail(email);
		bean.setPassword(password);
		
		return bean;

	}
	
 
	protected boolean isValidEmailFormat(final String fieldValue) {
 		if (fieldValue != null && !(fieldValue.matches(EMAIL_REGEX))) {
			return false;
		}
		return true;
	} 
}
