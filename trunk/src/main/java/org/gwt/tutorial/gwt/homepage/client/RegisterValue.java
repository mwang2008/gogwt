package org.gwt.tutorial.gwt.homepage.client;

import com.gwtview.arch.widgets.ActionValue;

/**
 * 
 * @author WangM
 *
 */
public class RegisterValue extends ActionValue {
	private String firstname; 
	private String lastname;
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	} 
	
	
}
