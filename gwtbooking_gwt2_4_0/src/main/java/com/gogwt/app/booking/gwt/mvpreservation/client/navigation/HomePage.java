package com.gogwt.app.booking.gwt.mvpreservation.client.navigation;

import com.gogwt.app.booking.gwt.common.utils.GoogleUtils;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.presenter.HomePresenter;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.view.HomeLayoutViewImpl;
import com.gogwt.framework.arch.widgets.AbstractController;
import com.gogwt.framework.arch.widgets.PageMetaInfo;

public class HomePage extends AbstractController {
	private HomeLayoutViewImpl homeView; 
	
	public HomePage() {
	    if (homeView == null) {
	    	homeView = new HomeLayoutViewImpl();           
         }
         new HomePresenter(homeView).go(controlPanel);         
 	}
	
	@Override
	public void process() {
		//this.pagePanel.add(new Label("HomePage"));
		homeView.prepareEntryLayout();
	}

	@Override
	public void postProcess() {
		//todo: revisit
		GoogleUtils.loadGoogleMapKey();
	}

	@Override
	protected void fillMetaInfo(PageMetaInfo pageInfo) {
		pageInfo.setTitle("Hotel Search");
		
	}
}
