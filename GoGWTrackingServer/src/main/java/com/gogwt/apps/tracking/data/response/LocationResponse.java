package com.gogwt.apps.tracking.data.response;

import com.gogwt.apps.tracking.data.Setting;
import com.gogwt.apps.tracking.data.Status;

public class LocationResponse {
	private Status status;
    private Setting setting;
    
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}
	
	
}
