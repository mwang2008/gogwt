package com.gogwt.app.booking.gwt.reservation.client.navigation;

import com.gogwt.framework.arch.widgets.AbstractPage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;

public class TestPageView extends AbstractPage {

	@Override
	public void process() {
		this.pagePanel.add(new Label("Example of Test Page with token of testpage"));	
		Window.setTitle("your title");
	}

}
