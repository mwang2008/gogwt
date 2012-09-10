package com.gogwt.apps.tracking.data.response;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.EnrollResult;
import com.gogwt.apps.tracking.data.Status;


public class EnrollResponse {
	private String myStr;
	private Status status;
	private EnrollResult result; 
	
	//private CustomerProfile customerProfile;
	
	public String getMyStr() {
		return myStr;
	}

	public void setMyStr(String myStr) {
		this.myStr = myStr;
	}
		
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public EnrollResult getResult() {
		return result;
	}

	public void setResult(EnrollResult result) {
		this.result = result;
	}	 

	
	/*
	public CustomerProfile getCustomerProfile() {
		return customerProfile;
	}

	public void setCustomerProfile(CustomerProfile customerProfile) {
		this.customerProfile = customerProfile;
	}
    */
	 
}
