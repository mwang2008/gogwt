package org.gwt.tutorial.gwt.homepage.client;

import com.google.gwt.user.client.ui.Label;
import com.gwtview.arch.widgets.AbstractView;
import com.gwtview.arch.widgets.ActionForm;

public class ConfirmView extends AbstractView {
	
	public void process() {
		final Label l = new Label("Success register");
		viewPanel.add(l);
 		 
	}
	
	public void process(ActionForm actionForm) {
		final Label l = new Label("Success register");
		viewPanel.add(l);
 		 
	}
}
