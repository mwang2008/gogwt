package com.gogwt.apps.tracking.c2md;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.gogwt.apps.tracking.controllers.AddLocationController;

public class MessageUtil {
	private static Logger logger = Logger.getLogger(AddLocationController.class);
	
	public static final String PARAM_REGISTRATION_ID = "registration_id";
	public static final String PARAM_DELAY_WHILE_IDLE = "delay_while_idle";
	public static final String PARAM_COLLAPSE_KEY = "collapse_key";
	private static final String UTF8 = "UTF-8";
	private static final String UPDATE_CLIENT_AUTH = "Update-Client-Auth";
	
	public static C2DMResponse sendMessage(String auth_token, String registrationId,
			final String fromPhone, String message) throws IOException {

		StringBuilder postDataBuilder = new StringBuilder();
		postDataBuilder.append(PARAM_REGISTRATION_ID).append("=")
				.append(registrationId);
		postDataBuilder.append("&").append(PARAM_COLLAPSE_KEY).append("=")
				.append("0");
		
		StringBuilder payLoad = new StringBuilder();
		payLoad.append("from="+fromPhone);
		payLoad.append("|");
		payLoad.append("body="+message);
		
		postDataBuilder.append("&").append("data.payload").append("=")
				.append(URLEncoder.encode(payLoad.toString(), UTF8));

		byte[] postData = postDataBuilder.toString().getBytes(UTF8);

		// Hit the dm URL.

		URL url = new URL("https://android.clients.google.com/c2dm/send");
		HttpsURLConnection
				.setDefaultHostnameVerifier(new CustomizedHostnameVerifier());
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		conn.setRequestProperty("Content-Length",
				Integer.toString(postData.length));
		conn.setRequestProperty("Authorization", "GoogleLogin auth="
				+ auth_token);

		OutputStream out = conn.getOutputStream();
		out.write(postData);
		out.close();

		int responseCode = conn.getResponseCode();
		
		C2DMResponse response = new C2DMResponse();
		response.setResponseCode(responseCode);
		
		if (responseCode == HttpServletResponse.SC_UNAUTHORIZED ||
	                responseCode == HttpServletResponse.SC_FORBIDDEN) {
			logger.debug(message); 
			//todo: serverConfig.invalidateCachedToken();
            return response;
		}
		
		  // Check for updated token header
        String updatedAuthToken = conn.getHeaderField(UPDATE_CLIENT_AUTH);
        if (updatedAuthToken != null && !auth_token.equals(updatedAuthToken)) {
        	logger.debug("Got updated auth token from datamessaging servers: " +
                    updatedAuthToken);
        	response.setUpdatedAuthToken(updatedAuthToken);
        }
        
		return response;
	}

	private static class CustomizedHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
}
