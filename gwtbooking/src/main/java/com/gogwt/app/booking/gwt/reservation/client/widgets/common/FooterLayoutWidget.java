package com.gogwt.app.booking.gwt.reservation.client.widgets.common;

import com.gogwt.framework.arch.widgets.BaseWidget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

public class FooterLayoutWidget extends BaseWidget {
	 
	private Panel layoutPanel = new FlowPanel();

	public FooterLayoutWidget() {
		super();
		initWidget(layoutPanel);
		
		displayHeader();
	}

	private void displayHeader() {
		
		
		layoutPanel.add(new Label("Footer"));
	}

}
