package com.gogwt.apps.tracking;
 
public final class GoGWTConstants {
    public final static String USER_PRFERENCE = "userPreference";
    public final static String GROUP_ID = "groupId";
    public final static String DISPLAY_NAME = "displayname";
    public final static String PHONE_NUMBER = "phonenumber";
    public final static String SERVER_USER_NAME = "server.username";
    public final static String SERVER_FIRST_NAME = "server.firstname";
    public final static String SERVER_LAST_NAME = "server.lastname";
    public final static String SERVER_EMAIL = "server.email";
    public final static String SERVER_PHONE = "server.phone";
    
    public final static String UNKNOWN = "unknown";
    public final static String PASSWORD = "password";
   
    //app preference
    public final static String APP_PREFERENCE_SETTING = "appPreferenceSetting";
    public final static String UNIT = "prefreed_unit";
    public final static String INTERVAL = "server_interval";
    public final static String AUTO_START = "auto_start";
    public final static String SEND_BODY = "send_body";
    
    //fake message body
    public final static String FAKE_MESSGE_BODY = "XXDisableXX";
    
    //QA
    public final static String REST_URL_PREFIX = "http://10.0.121.162/tracking/en-us/";
    public final static String REST_SIGNIN_URL = "http://10.0.121.162/tracking/en-us/restlogin";
    public final static String REST_LOCATION_URL = "http://10.0.121.162/tracking/en-us/restlocation";
    
    public final static String FROM_START_RECEIVER = "fromStartReceiver";
    
    //Prod
    
    //SMS
    public static final String START_TRACK1 = "*starttrack#";
    public static final String START_TRACK2 = "*startrack#";
    public static final String START_TRACKING1 = "*starttracking#";
    public static final String START_TRACKING2 = "*startracking#";	
	public static final String LOCATION = "*location#";	
	public static final String STOP_TRACK1 = "*stoptrack#";	
	public static final String STOP_TRACK2 = "*stoptracking#";	
	public static final String CONTENT_URI = "content://sms";
	
}
