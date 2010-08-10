package com.gogwt.app.booking.gwt.reservation.client.navigation;

import com.gogwt.app.booking.gwt.reservation.client.widgets.home.HomeLayoutWidget;
import com.gogwt.framework.arch.widgets.AbstractView;

public class HomeView extends AbstractView {
	private HomeLayoutWidget homeWidget;
	
	public HomeView() {
		homeWidget = new HomeLayoutWidget();			
	}
	
	 
	/**
	 * Entry point to process the home view
	 * Invoked by AbstractEntry
	 */
	public void process() {
		 
		
		//add homeWidget to home viewPanel
		viewPanel.add(homeWidget);		
 		
		homeWidget.prepareEntryLayout();		
		
	}
  
 }
