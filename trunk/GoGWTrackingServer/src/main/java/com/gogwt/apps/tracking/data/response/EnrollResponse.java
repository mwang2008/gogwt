package com.gogwt.apps.tracking.data.response;

import com.gogwt.apps.tracking.data.EnrollResult;
import com.gogwt.apps.tracking.data.Status;


public class EnrollResponse {
	 
	private Status status;
	//private CustomerProfile customerProfile;
	private EnrollResult result; 
	
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
