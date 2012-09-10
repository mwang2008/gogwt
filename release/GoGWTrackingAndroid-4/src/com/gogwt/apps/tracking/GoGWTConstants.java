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
    
    public final static String CURRENT_LOCATION = "current_location";
    
    //fake message body
    public final static String FAKE_MESSGE_BODY = "XXDisableXX";
    
    //QA
    public final static String REST_URL_PREFIX = "http://10.0.121.162/tracking/en-us/";
    public final static String REST_SIGNIN_URL = "http://10.0.121.162/tracking/en-us/restlogin";
    public final static String REST_LOCATION_URL = "http://10.0.121.162/tracking/en-us/restlocation";
    
    public final static String FROM_START_RECEIVER = "fromStartReceiver";
    
    //Prod
    
  
    //SMS
    //used for internal
    public static final String START_TRACK = "*istarttrack#";
    public static final String LOCATION = "*ilocation#";  
    public static final String STOP_TRACK = "*istoptrack#";	
    
    public static final int SMS_GOGWT = 1;
    public static final int SMS_USER = 2;
    public static final int SMS_NONE = 0;
    
    //user activity
    public static final String START_TRACK1 = "*starttrack#";
    public static final String START_TRACK2 = "*startrack#";
    public static final String START_TRACK3 = "#starttrack*";
    public static final String START_TRACK4 = "#startrack*";

    public static final String START_TRACKING1 = "*starttracking#";
    public static final String START_TRACKING2 = "*startracking#";	
    public static final String START_TRACKING3 = "#starttracking*";
    public static final String START_TRACKING4 = "#startracking*";	
    
    public static final String LOCATION1 = "*location#";
    public static final String LOCATION2 = "#location*";
     
	public static final String STOP_TRACK1 = "*stoptrack#";	
	public static final String STOP_TRACK2 = "*stoptracking#";	
	public static final String STOP_TRACK3 = "#stoptrack*";	
	public static final String STOP_TRACK4 = "#stoptracking*";	
	
	
	public static final String CONTENT_URI = "content://sms";
	
	public static final String GOGWT_TAG = "GoGWT:";
	public static final String ADMIN = "admin:";
	
	
}
