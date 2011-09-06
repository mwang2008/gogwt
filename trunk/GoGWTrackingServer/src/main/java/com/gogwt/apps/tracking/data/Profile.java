package com.gogwt.apps.tracking.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "profile")
public class Profile {
	private String groupId;
	private String displayName; 
	private boolean rememberMe;
    private String phoneNumber;
    
	private String serverUsername;
	private String serverFirstName;
	private String serverLastName;
	private String serverEmail;
	
	public Profile() {
		super();		 
	}

	public Profile(String groupId, String displayName,boolean rememberMe) {
		super();
	    this.groupId = groupId;
	    this.displayName = displayName;
		this.rememberMe = rememberMe;
	}
	
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String getServerUsername() {
		return serverUsername;
	}

	public void setServerUsername(String serverUsername) {
		this.serverUsername = serverUsername;
	}

	public String getServerFirstName() {
		return serverFirstName;
	}

	public void setServerFirstName(String serverFirstName) {
		this.serverFirstName = serverFirstName;
	}

	public String getServerLastName() {
		return serverLastName;
	}

	public void setServerLastName(String serverLastName) {
		this.serverLastName = serverLastName;
	}

	public String getServerEmail() {
		return serverEmail;
	}

	public void setServerEmail(String serverEmail) {
		this.serverEmail = serverEmail;
	}
	
	public String toString() {

		StringBuilder sbuf = new StringBuilder();
		sbuf.append(Setting.class.getSimpleName() + "=[");
		sbuf.append("groupId=" + groupId);
		sbuf.append(",displayName=" + displayName);
		sbuf.append(",rememberMe=" + rememberMe);
		sbuf.append(",phoneNumber=" + phoneNumber);
		sbuf.append(",serverUsername=" + serverUsername);
		sbuf.append(",serverFirstName=" + serverFirstName);
		sbuf.append(",serverLastName=" + serverLastName);
		sbuf.append(",serverEmail=" + serverEmail);
	 	sbuf.append("]");

		return sbuf.toString();

	}
 

}
