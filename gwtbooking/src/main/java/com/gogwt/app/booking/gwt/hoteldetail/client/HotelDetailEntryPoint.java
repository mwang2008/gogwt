package com.gogwt.app.booking.gwt.hoteldetail.client;

import java.util.Map;

import com.gogwt.app.booking.gwt.hoteldetail.client.widgets.common.view.FooterLayoutWidget;
import com.gogwt.app.booking.gwt.hoteldetail.client.widgets.common.view.HeaderLayoutWidget;
import com.gogwt.app.booking.gwt.hoteldetail.client.widgets.common.view.MenuLayoutWidget;
import com.gogwt.framework.arch.navigation.AbstractEntryPoint;
import com.gogwt.framework.arch.navigation.AbstractPageConfigAccessor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class HotelDetailEntryPoint extends AbstractEntryPoint {

	@Override
	protected void loadModule() {
		// header
		RootPanel.get("header").add(new HeaderLayoutWidget());
		
		// menu 
		RootPanel.get("menu").add(new MenuLayoutWidget());

		// show middle part of GWT body, wrapperContent is defined in JSP.
		addPageManagerToRootPanel("wrapperContent");
		
		// footer
		RootPanel.get("footer").add(new FooterLayoutWidget());

		
	}

	@Override
	protected AbstractPageConfigAccessor obtainPageAccessor() {
		return GWT.create(HotelDetailMVPProcessConfig.class);
	}

	@Override
	protected void processPopulator(Map<String, String> populatorsMap) {
		// TODO Auto-generated method stub
		
	}

/*	public void onModuleLoad() {
		RootPanel.get().add(new Label(" first test"));
	}
*/
}
