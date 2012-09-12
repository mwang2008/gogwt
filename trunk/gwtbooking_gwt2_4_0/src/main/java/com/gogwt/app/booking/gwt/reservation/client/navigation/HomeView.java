package com.gogwt.app.booking.gwt.reservation.client.navigation;

import com.gogwt.app.booking.gwt.reservation.client.widgets.home.HomeLayoutWidget;
import com.gogwt.framework.arch.widgets.AbstractController;
import com.gogwt.framework.arch.widgets.PageMetaInfo;
 

public class HomeView extends AbstractController {
 	 
	/**
	 * Entry point to process the home view
	 * Invoked by AbstractEntry
	 */
	public void process() {
 		
        controlPanel.clear();
		
        HomeLayoutWidget homeWidget = new HomeLayoutWidget();
		this.controlPanel.add(homeWidget);	
		
		homeWidget.prepareEntryLayout();	
		
	}


	@Override
	protected void fillMetaInfo(PageMetaInfo pageInfo) {
		pageInfo.setTitle("Hotel Search");
		
	}
  
 }
