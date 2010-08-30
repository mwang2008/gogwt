package com.gogwt.app.booking.gwt.mvpreservation.client.navigation;

import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.view.HomeLayoutViewImpl;
import com.gogwt.app.booking.gwt.reservation.client.widgets.home.HomeLayoutWidget;
import com.gogwt.framework.arch.widgets.AbstractPage;
import com.google.gwt.user.client.ui.Label;

public class HomePage extends AbstractPage {
	private HomeLayoutViewImpl homeView; 
	
	public HomePage() {
		homeView = new HomeLayoutViewImpl();
		
		//add homeWidget to home pagePanel
		this.pagePanel.add(homeView);		
	}
	
	@Override
	public void process() {
		//this.pagePanel.add(new Label("HomePage"));
		homeView.prepareEntryLayout();
	}

}
