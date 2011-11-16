package com.gogwt.apps.tracking.dataaccess.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.util.Log;

import com.gogwt.apps.tracking.data.Profile;

public class StopTracking {
	protected static final String TAG = SendLocation.class.getSimpleName();
	protected int SOCKET_TIMEOUT = 5000;
	
	public void httpPost(Profile profile) {
		try {

			 
	 		MediaType mediaType = MediaType.APPLICATION_JSON;
			 
			final String url = ServerURLFactory.stopTracking();
			Log.d(TAG, " ---  url=" + url);
			
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setContentType(mediaType);

			HttpEntity<Profile> requestEntity = new HttpEntity<Profile>(
					profile, requestHeaders);

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setReadTimeout(SOCKET_TIMEOUT);
			
			RestTemplate restTemplate = new RestTemplate(requestFactory);
			
			restTemplate.exchange(url, HttpMethod.POST, requestEntity, Profile.class);
			
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			
		}
	}
}
