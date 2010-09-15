package com.gogwt.app.booking.gwt.mvpreservation.client;

import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.AppController;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.common.view.FooterLayoutWidget;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.common.view.HeaderLayoutWidget;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.common.view.MenuLayoutWidget;
import com.gogwt.framework.arch.navigation.AbstractMVPEntryPoint;
import com.gogwt.framework.arch.navigation.AbstractPageConfigAccessor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;


public class ReservationMVPEntryPoint extends AbstractMVPEntryPoint {

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

	/*public void onModuleLoad() {
		RootPanel.get().add(new Label("Hello, from MyFirstMVPEntryPoint")); 

	}*/

	
}