package com.gogwt.app.booking.gwt.hoteldetail.client.navigation;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.gwt.hoteldetail.client.widgets.info.presenter.HotelDetailInfoPresenter;
import com.gogwt.app.booking.gwt.hoteldetail.client.widgets.info.view.HotelDetailInfoView;
import com.gogwt.app.booking.gwt.hoteldetail.client.widgets.info.view.HotelDetailInfoViewImpl;
import com.gogwt.framework.arch.widgets.AbstractController;
import com.gogwt.framework.arch.widgets.PageMetaInfo;
 

public class HotelInfoPage extends AbstractController {
    private HotelDetailInfoView<HotelBean> theView; 
	
	
	public HotelInfoPage() {
		super();
		if (theView == null) {
			theView = new HotelDetailInfoViewImpl<HotelBean>();
		}
		new HotelDetailInfoPresenter(theView).go(this.controlPanel);
	}

	@Override
	public void process() {
		//this.pagePanel.add(new Label("test HotelInfoPage"));
		theView.retrieveHotelDetail();
	}

	@Override
	protected void fillMetaInfo(PageMetaInfo pageInfo) {
		// TODO Auto-generated method stub
		
	}
}
