package com.gogwt.app.booking.gwt.reservation.client.navigation;

import com.gogwt.framework.arch.widgets.AbstractController;
import com.gogwt.framework.arch.widgets.PageMetaInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;

public class TestPageView extends AbstractController {

	@Override
	public void process() {
		this.controlPanel.add(new Label("Example of Test Page with token of testpage"));	
		Window.setTitle("your title");
	}

	@Override
	protected void fillMetaInfo(PageMetaInfo pageInfo) {
		// TODO Auto-generated method stub
		
	}

}
