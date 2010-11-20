package com.gogwt.demo.gwt.mvp.client.dataobject;

import java.io.Serializable;


public class FormBean implements Serializable {
	private String detail;
    private String title;
    private String firstName;
    private String lastName;
    
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String toString() {
		StringBuilder sbuf = new StringBuilder();
		sbuf.append("user input=[");
		sbuf.append(" detail="+this.getDetail());
		sbuf.append(", title="+this.getTitle());
		sbuf.append(", firstName="+this.getFirstName());
		sbuf.append(", lastName="+this.getLastName());
		
		sbuf.append("]");
		
		return sbuf.toString();
	}
}
