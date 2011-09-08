package com.gogwt.apps.tracking.dataaccess.http;

import com.gogwt.apps.tracking.utils.StringUtils;

public class ServerURLFactory {
    private static String env = "QA";
    
    public final static String REST_SIGNIN_URL = "http://10.0.121.162/tracking/en-us/restlogin";
    public final static String REST_LOCATION_URL = "http://10.0.121.162/tracking/en-us/restlocation";
  
    public static String getHost() {
    	if (StringUtils.equalsIgnoreCase(env, "QA")) {
    		return "http://10.0.122.7/";
    	}
    	return "http://10.0.122.7/";
    }
    
	public static String getSendLocationURL() {
	   	 return  getHost() + "tracking/en-us/mobilelocation";
	}
	
	public static String getLoginURL() {
		return  getHost() + "tracking/en-us/mobilelogin";
	}
	
	public static String stopTracking() {
		return getHost() + "tracking/en-us/stoptracking";
	}
}
