package com.gogwt.app.booking.gwt.mvpreservation.client.navigation;

import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.presenter.GuestInfoPresenter;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.view.GuestInfoViewImpl;
import com.gogwt.framework.arch.widgets.AbstractPage;
import com.google.gwt.user.client.ui.Label;

public class GuestInfoPage extends AbstractPage {
    private GuestInfoViewImpl guestinfoView; 
	
	public GuestInfoPage() {
	    if (guestinfoView == null) {
	    	guestinfoView = new GuestInfoViewImpl();           
         }
 	}
	
	@Override
	public void process() {
		
		new GuestInfoPresenter(guestinfoView).go(pagePanel);
		//this.pagePanel.add(new Label("GuestInfoPage"));
	}

}
