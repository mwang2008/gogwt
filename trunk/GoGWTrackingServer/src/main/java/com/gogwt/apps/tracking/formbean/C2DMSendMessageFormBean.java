package com.gogwt.apps.tracking.formbean;

import java.io.Serializable;
import java.util.List;

public class C2DMSendMessageFormBean implements Serializable {
	private List<String> recipientList;
	private String message;
	 
	public List<String> getRecipientList() {
		return recipientList;
	}
	public void setRecipientList(List<String> recipientList) {
		this.recipientList = recipientList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
