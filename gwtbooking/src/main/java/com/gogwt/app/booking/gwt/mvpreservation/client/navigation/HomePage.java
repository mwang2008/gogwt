package com.gogwt.app.booking.gwt.mvpreservation.client.navigation;

import com.gogwt.app.booking.gwt.common.utils.GoogleUtils;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.AppController;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.presenter.HomePresenter;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.view.HomeLayoutViewImpl;
import com.gogwt.framework.arch.widgets.AbstractPage;
import com.google.gwt.event.shared.HandlerManager;

public class HomePage extends AbstractPage {
	private HomeLayoutViewImpl homeView; 
	
	public HomePage() {
	    if (homeView == null) {
	    	homeView = new HomeLayoutViewImpl();           
         }
         new HomePresenter(homeView).go(pagePanel);
         
 	}
	
	@Override
	public void process() {
		//this.pagePanel.add(new Label("HomePage"));
		homeView.prepareEntryLayout();
	}

	@Override
	public void postProcess() {
		GoogleUtils.loadGoogleMapKey();
	}
}
