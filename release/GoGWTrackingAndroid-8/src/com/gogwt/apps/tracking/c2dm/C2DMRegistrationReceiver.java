package com.gogwt.apps.tracking.c2dm;

import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;

import com.gogwt.apps.tracking.C2DMClientTestActivity;
import com.gogwt.apps.tracking.GwtConfig;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.utils.GwtLog;
import com.gogwt.apps.tracking.utils.MyAndroidUtils;
import com.gogwt.apps.tracking.utils.SessionManager;

public class C2DMRegistrationReceiver extends BroadcastReceiver {
	protected static int SOCKET_TIMEOUT = 5000;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		GwtLog.d("C2DM", "Registration Receiver called");
		
		if ("com.google.android.c2dm.intent.REGISTRATION".equals(action)) {
			GwtLog.d("C2DM", "Received registration ID - 4");
			final String registrationId = intent.getStringExtra("registration_id");
			final String error = intent.getStringExtra("error");
			final String unregistration = intent.getStringExtra("unregistered");

			if (error != null) {
				onError(context, error);
			} else if (unregistration != null) {
				onUnregistration(context);
			} else if (registrationId != null) {
				onRegistration(context, registrationId);
			}
 		}		
	}
	
	private void onRegistration(Context context, String registrationId) {
		GwtLog.d("C2DM", "onRegistration");
		String deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		GwtLog.d("C2DM", "deviceId="+deviceId + ",registrationId="+registrationId);
		
		Profile profile = SessionManager.getProfile(context);
		
		GwtLog.d("C2DM", "phone="+profile.getPhoneNumber());
		
		//send back to app server to remember
		sendRegistrationIdToServer(deviceId, registrationId, profile);
		
		//save it in the preference to be able to show it later
		saveRegistrationId(context, registrationId);
	}
 	
	private void onError(Context context, String error) {
		GwtLog.d("C2DM", "fail to call register error="+error);
	}
	
	private void onUnregistration(Context context) {
		String phone = MyAndroidUtils.getMyPhoneNumber(context);
		sendUnRegistrationIdToServer(phone);
		
		C2DMUtils.removeRegistrationId(context);		
	} 
	
	
	private void saveRegistrationId(Context context, String registrationId) {
		C2DMUtils.saveRegistrationId(context, registrationId);
		/*
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor edit = prefs.edit();
		edit.putString(C2DMClientTestActivity.AUTH, registrationId);
		edit.commit();
		*/
	}
	
	/**
	 * Sync with app server by removing phone registerId record.
	 * @param profile
	 */
	private void sendUnRegistrationIdToServer(String phoneNumber) {
		C2DMRegisterBean registerBean = new C2DMRegisterBean();		 
		registerBean.setPhone(phoneNumber);
		registerBean.setCreateDate(new Date());
		registerBean.setRegister(false);
		
		new SendRegistrationToServerTask().execute(registerBean);	
	}
	
	/**
	 * Sync with app server by add/update current registerid
	 * @param deviceId
	 * @param registrationId
	 * @param profile
	 */
	private void sendRegistrationIdToServer(String deviceId, String registrationId, Profile profile) {
		C2DMRegisterBean registerBean = new C2DMRegisterBean();
		registerBean.setDeviceid(deviceId);
		registerBean.setRegistrationid(registrationId);
		registerBean.setGroupId(profile.getGroupId());
		registerBean.setPhone(profile.getPhoneNumber());
		registerBean.setCreateDate(new Date());
		registerBean.setRegister(true); 
		
		new SendRegistrationToServerTask().execute(registerBean);			
	}
	
	
	/**
	 * Async SendRegistrationToServerTask call
	 * @author Michael.Wang
	 *
	 */
	private class SendRegistrationToServerTask extends AsyncTask<C2DMRegisterBean, Void, String> {

		@Override
		protected String doInBackground(C2DMRegisterBean... registerBeanArray) {
			C2DMRegisterBean registerBean = registerBeanArray[0];
			try {
				GwtLog.d("C2DM", "--- first time");
				return executeRegister(registerBean);
			} catch (Throwable e) {
				// call one more time.
				try {
					GwtLog.d("C2DM", "--- second time");
					return executeRegister(registerBean);
				} catch (Throwable et) {
					et.printStackTrace();
					GwtLog.d("C2DM", "Registion error with tracking/en-us/c2dmreg");
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			GwtLog.d("C2DM", "App server response code=="+result); 
		}
		
		public String executeRegister(C2DMRegisterBean registerBean) throws Throwable {
			// stop with different thread

			final String url = GwtConfig.getHost()+"tracking/en-us/c2dmreg";
			GwtLog.d("C2DM", "app server url="+url);
			
			HttpHeaders requestHeaders = new HttpHeaders();

			MediaType mediaType = MediaType.APPLICATION_JSON;
			requestHeaders.setContentType(mediaType);

			HttpEntity<C2DMRegisterBean> requestEntity = new HttpEntity<C2DMRegisterBean>(registerBean,
					requestHeaders);

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setReadTimeout(SOCKET_TIMEOUT);

			RestTemplate restTemplate = new RestTemplate(requestFactory);

			// ResponseEntity response =
		    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

		    return response.getBody();

		}
	}
	 

	 
	 
}
