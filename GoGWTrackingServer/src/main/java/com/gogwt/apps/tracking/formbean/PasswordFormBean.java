package com.gogwt.apps.tracking.formbean;

import java.io.Serializable;

public class PasswordFormBean implements Serializable {
	private String customerId;
	private String groupId;
	private String userName;
	
	private String oldPass;
	private String newPass;
	private String newPassConfirm;
    private String email;
    
	public String getCustomerId() {
		return customerId;
	}
 
	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getNewPassConfirm() {
		return newPassConfirm;
	}

	public void setNewPassConfirm(String newPassConfirm) {
		this.newPassConfirm = newPassConfirm;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
