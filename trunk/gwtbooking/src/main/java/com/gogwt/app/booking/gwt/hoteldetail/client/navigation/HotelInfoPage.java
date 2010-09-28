package com.gogwt.app.booking.gwt.hoteldetail.client.navigation;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.gwt.hoteldetail.client.widgets.info.presenter.HotelDetailInfoPresenter;
import com.gogwt.app.booking.gwt.hoteldetail.client.widgets.info.view.HotelDetailInfoView;
import com.gogwt.app.booking.gwt.hoteldetail.client.widgets.info.view.HotelDetailInfoViewImpl;
import com.gogwt.framework.arch.widgets.AbstractPage;
 

public class HotelInfoPage extends AbstractPage {
    private HotelDetailInfoView<HotelBean> theView; 
	
	
	public HotelInfoPage() {
		super();
		if (theView == null) {
			theView = new HotelDetailInfoViewImpl<HotelBean>();
		}
		new HotelDetailInfoPresenter(theView).go(this.pagePanel);
	}

	@Override
	public void process() {
		//this.pagePanel.add(new Label("test HotelInfoPage"));
		theView.retrieveHotelDetail();
	}
}
