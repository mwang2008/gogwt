package com.gogwt.apps.tracking.services.http;

import com.gogwt.apps.tracking.GwtConfig;

public class ServerURLFactory {
        
	public static String getSendLocationURL() {
	   	 return  GwtConfig.getHost() + "tracking/en-us/mobilelocation";
	}
	
	public static String getLoginURL() {
		return  GwtConfig.getHost() + "tracking/en-us/mobilelogin";
	}
	
	public static String stopTracking() {
		return GwtConfig.getHost() + "tracking/en-us/stoptracking";
	}
	
	public static String mobileEnrollURL() {
		return GwtConfig.getHost() + "tracking/en-us/mobileenroll";
	}
}
