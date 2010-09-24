package com.gogwt.app.booking.gwt.reservation.client.navigation;

import com.gogwt.app.booking.gwt.reservation.client.widgets.home.HomeLayoutWidget;
import com.gogwt.framework.arch.widgets.AbstractPage;
 

public class HomeView extends AbstractPage {
	private HomeLayoutWidget homeWidget;
	
	public HomeView() {
		homeWidget = new HomeLayoutWidget();
		
		//add homeWidget to home viewPanel
		pagePanel.add(homeWidget);		
	}
	
	 
	/**
	 * Entry point to process the home view
	 * Invoked by AbstractEntry
	 */
	public void process() {
	
 		homeWidget.prepareEntryLayout();		
		
	}
  
 }
