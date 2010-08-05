package com.gwtview.test.client.enroll;

import com.google.gwt.user.client.ui.Label;
import com.gwtview.arch.widgets.AbstractView;
import com.gwtview.arch.widgets.ActionForm;

public class RegisterView extends AbstractView  {
	 

	@Override
	public void process(ActionForm actionForm) {
		final Label l = new Label("Welcome");
		viewPanel.add(l);
		
			
		 
	}

	 
}
