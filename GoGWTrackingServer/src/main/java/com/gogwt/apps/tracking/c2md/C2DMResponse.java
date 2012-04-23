package com.gogwt.apps.tracking.c2md;

public class C2DMResponse {
	public static final int SC_OK = 200;

	private int responseCode;
	private String updatedAuthToken;

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getUpdatedAuthToken() {
		return updatedAuthToken;
	}

	public void setUpdatedAuthToken(String updatedAuthToken) {
		this.updatedAuthToken = updatedAuthToken;
	}

}
