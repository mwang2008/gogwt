package com.gogwt.app.booking.gwt.mvpreservation.client.navigation;

import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.presenter.GuestInfoPresenter;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.view.GuestInfoViewImpl;
import com.gogwt.framework.arch.widgets.AbstractController;
import com.gogwt.framework.arch.widgets.PageMetaInfo;

public class GuestInfoPage extends AbstractController {
    private GuestInfoViewImpl guestinfoView; 
	
	public GuestInfoPage() {
	    if (guestinfoView == null) {
	    	guestinfoView = new GuestInfoViewImpl();           
         }
 	}
	
	@Override
	public void process() {
		
		new GuestInfoPresenter(guestinfoView).go(controlPanel);
		//this.pagePanel.add(new Label("GuestInfoPage"));
	}

	@Override
	protected void fillMetaInfo(PageMetaInfo pageInfo) {
		// TODO Auto-generated method stub
		
	}

}
