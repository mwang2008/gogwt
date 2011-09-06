package com.gogwt.apps.tracking.data;


public class GResponse {
	private Status status;
	private Setting setting;

	public GResponse() {}
	
	public GResponse(Status status, Setting setting) {
		super();
		this.status = status;
		this.setting = setting;
	}

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
	
	public String toString() {

		StringBuilder sbuf = new StringBuilder();
		sbuf.append(GResponse.class.getSimpleName() + "=[");
		if (status != null) {
		   sbuf.append("status" + status.toString());
		}
		if (setting != null) {
		   sbuf.append("setting" + setting.toString());
		}
		
		sbuf.append("]");

		return sbuf.toString();

	}

}
