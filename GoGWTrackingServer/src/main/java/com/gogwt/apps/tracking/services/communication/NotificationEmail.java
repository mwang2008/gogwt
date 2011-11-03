package com.gogwt.apps.tracking.services.communication;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.services.domain.ProfileBusinessDomainService;

public class NotificationEmail {
	private static Logger logger = Logger.getLogger(ProfileBusinessDomainService.class);
	
	private static final String emailURL = "http://gogwtengine.appspot.com/notifyemail";
	
    public static void sendEnrollEmail(final CustomerProfile profile) {
    	logger.debug("=sendEnrollEmail");
    	
    	HttpClient client = new HttpClient();
    	PostMethod method = new PostMethod(emailURL);
    	
    	NameValuePair[] data = {
    	    new NameValuePair("to", profile.getEmail()),
    	    new NameValuePair("subject", "Welcome to enroll to gogwt mobile app"),
    	    new NameValuePair("body", "Success Enroll")
    	};
    	method.setRequestBody(data);
    	
    	try {
			int statusCode = client.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        finally {
           method.releaseConnection();
        }
    }
}
