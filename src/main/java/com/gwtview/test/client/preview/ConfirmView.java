package com.gwtview.test.client.preview;

import com.google.gwt.user.client.ui.Label;
import com.gwtview.arch.widgets.AbstractView;
import com.gwtview.arch.widgets.ActionForm;

public class ConfirmView extends AbstractView {
	
	public void process(ActionForm actionForm) {
		final Label l = new Label("Success register");
		viewPanel.add(l);
 		 
	}

}
