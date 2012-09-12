package com.gogwt.app.booking.config.runtime;

import java.io.Serializable;

public class AppRuntimeConfig implements Serializable {
	private String emailServer;
	private int securePort;
	
	
	public String getEmailServer() {
		return emailServer;
	}
	public void setEmailServer(String emailServer) {
		this.emailServer = emailServer;
	}
	public int getSecurePort() {
		return securePort;
	}
	public void setSecurePort(int securePort) {
		this.securePort = securePort;
	}
	

	
}
