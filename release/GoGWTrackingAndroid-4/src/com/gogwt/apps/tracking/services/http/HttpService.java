package com.gogwt.apps.tracking.services.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.os.AsyncTask;

import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.utils.SessionManager;

public class HttpService {
	protected static int SOCKET_TIMEOUT = 5000;

	public void stopTracking(Context context) {
		Profile profile = SessionManager.getProfile(context);
		new SendStopTask().execute(profile);
	}

	public void executeStopTracking(Profile profile) throws Throwable {
		// stop with different thread

		final String url = ServerURLFactory.stopTracking();

		HttpHeaders requestHeaders = new HttpHeaders();

		MediaType mediaType = MediaType.APPLICATION_JSON;
		requestHeaders.setContentType(mediaType);

		HttpEntity<Profile> requestEntity = new HttpEntity<Profile>(profile,
				requestHeaders);

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setReadTimeout(SOCKET_TIMEOUT);

		RestTemplate restTemplate = new RestTemplate(requestFactory);

		// ResponseEntity response =
		restTemplate.exchange(url, HttpMethod.POST, requestEntity, null);

		// return response.getBody();

	}

	private class SendStopTask extends AsyncTask<Profile, Void, Void> {

		@Override
		protected Void doInBackground(Profile... profileArray) {
			Profile profile = profileArray[0];
			try {
				executeStopTracking(profile);
			} catch (Throwable e) {
				// call one more time.
				try {
					executeStopTracking(profile);
				} catch (Throwable et) {
					//
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

		}
	}

}
