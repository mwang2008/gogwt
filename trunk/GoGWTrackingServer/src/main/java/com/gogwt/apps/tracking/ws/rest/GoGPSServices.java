package com.gogwt.apps.tracking.ws.rest;

import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.Status;
import com.gogwt.apps.tracking.data.response.LoginResponse;
import com.gogwt.apps.tracking.utils.StringUtils;

/**
 * 
 * @author michael.wang
 * @deprecated 
 */
public class GoGPSServices {
	public LoginResponse processLogin(Profile profile) {
		
		
		Status status = new Status();
		
	    if (StringUtils.equalsIgnoreCase(profile.getGroupId(), "michael")) {
	    	//current
	    	status.setCode(200);
	    	status.setMsg("Success logged in");
	    }
	    else {
	    	status.setCode(500);
	    	status.setMsg("Fail to logged in, please check CompanyId, Username and Password");	    	
	    }
	    
	    LoginResponse response = new LoginResponse(status, profile);
	    
		return response;
	}
}
