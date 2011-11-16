package com.gogwt.apps.tracking.data.response;

import com.gogwt.apps.tracking.data.Status;
import com.gogwt.apps.tracking.data.Profile;

public class LoginResponse {
	private Status status;
	private Profile profile; 

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
