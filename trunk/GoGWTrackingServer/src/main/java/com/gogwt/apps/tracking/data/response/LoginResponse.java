package com.gogwt.apps.tracking.data.response;

import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.Status;


public class LoginResponse {
	private Status status;
	private Profile profile;

	
	public LoginResponse() {
		super();
		status = new Status();
		profile = new Profile();
	}

	public LoginResponse(Status status, Profile profile) {
		super();
		this.status = status;
		this.profile = profile;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
