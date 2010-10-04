package com.gogwt.app.booking.gwt.mvpreservation.client;

import java.util.Map;

import com.gogwt.app.booking.gwt.common.widget.populator.PopulatorManager;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.AppController;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.common.view.FooterLayoutWidget;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.common.view.HeaderLayoutWidget;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.common.view.MenuLayoutWidget;
import com.gogwt.framework.arch.navigation.AbstractEntryPoint;
import com.gogwt.framework.arch.navigation.AbstractPageConfigAccessor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;


public class ReservationMVPEntryPoint extends AbstractEntryPoint {

	@Override
	protected void preLoadModule() {}

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
	protected void postLoadModule() {
		//invoke AppController		 
	    new AppController();
	}

	@Override
	protected AbstractPageConfigAccessor obtainPageAccessor() {
		return GWT.create(ReservationMVPProcessConfig.class);	
	}

	@Override
	protected void processPopulator(Map<String, String> populatorsMap) {
		PopulatorManager.handlePopulators( populatorsMap );		
	}

 
}
