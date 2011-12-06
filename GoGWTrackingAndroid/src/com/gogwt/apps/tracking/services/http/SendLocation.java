package com.gogwt.apps.tracking.services.http;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.util.Log;

import com.gogwt.apps.tracking.data.request.LocationRequest;
import com.gogwt.apps.tracking.data.response.LocationResponse;
import com.gogwt.apps.tracking.utils.GwtLog;


public class SendLocation {
	protected static final String TAG = SendLocation.class.getSimpleName();
	protected int SOCKET_TIMEOUT = 5000;
	
	public LocationResponse httpPost(LocationRequest locationRequest) {
		try {

			LocationRequest request = locationRequest;
	 		MediaType mediaType = MediaType.APPLICATION_JSON;
			 
			final String url = ServerURLFactory.getSendLocationURL();
			GwtLog.d(TAG, " ---  url=" + url);
			
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setContentType(mediaType);

			HttpEntity<LocationRequest> requestEntity = new HttpEntity<LocationRequest>(
					request, requestHeaders);

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setReadTimeout(SOCKET_TIMEOUT);
			
			RestTemplate restTemplate = new RestTemplate(requestFactory);
			
			ResponseEntity<LocationResponse> response = restTemplate.exchange(
					url, HttpMethod.POST, requestEntity,
					LocationResponse.class);

			return response.getBody();

		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			
		}
		
		//server error 
		String message = "Server has problem at this moment, please try again";
		
		LocationResponse response = new LocationResponse();
		com.gogwt.apps.tracking.data.Status status = new com.gogwt.apps.tracking.data.Status();
		status.setCode(602);
		status.setMsg(message);
		response.setStatus(status);
		
		return response;
	} 
}
