package com.gwtview.test.client.enroll;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtview.arch.widgets.ActionForm;

public class RegisterFormEntry extends ActionForm implements ClickListener {

	private TextBox firstname; // = new TextBox();
	private TextBox lastname; // =; new TextBox();
	private Button submitBtn; // = new Button("Submit");
	
	public void onClick(Widget arg0) {
		// TODO Auto-generated method stub
		
	}

	public TextBox getFirstname() {
		return firstname;
	}

	public void setFirstname(TextBox firstname) {
		this.firstname = firstname;
	}

	public TextBox getLastname() {
		return lastname;
	}

	public void setLastname(TextBox lastname) {
		this.lastname = lastname;
	}

	
}
