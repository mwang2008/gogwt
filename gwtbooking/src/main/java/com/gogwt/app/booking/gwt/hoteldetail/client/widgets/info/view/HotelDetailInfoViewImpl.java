package com.gogwt.app.booking.gwt.hoteldetail.client.widgets.info.view;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.framework.arch.widgets.AbstractRequestWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

public class HotelDetailInfoViewImpl<T> extends AbstractRequestWidget implements HotelDetailInfoView <T> {
	private Presenter<T> presenter;
	
	@UiTemplate("HotelDetailInfoView.ui.xml")
	interface HotelDetailInfoViewUiBinder extends UiBinder<Widget, HotelDetailInfoViewImpl> {}
	private static HotelDetailInfoViewUiBinder uiBinder = GWT.create(HotelDetailInfoViewUiBinder.class);

  
	@UiField SubHotelDetailView subDetailView;
	
	 
	/**
	 * Constructor
	 */
	public HotelDetailInfoViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setPresenter(
			com.gogwt.app.booking.gwt.hoteldetail.client.widgets.info.view.HotelDetailInfoView.Presenter<T> presenter) {
		this.presenter = presenter;
		
	}

	public Widget asWidget() {
		return this;
	}

	/**
	 * forward retrieve action to presenter.
	 */
	public void retrieveHotelDetail() {
		presenter.retrieveHotelDetail();
	}

	public void displayHotelDetail(HotelBean hotelBean) {
		//name.setInnerHTML(hotelBean.getName());
		 	
		subDetailView.displayHotelDetail(hotelBean);
	}

	 
	// Add a UI Factory method for the sub-widget & pass hotelBean object
/*	  @UiFactory
	  SubHotelDetailView createSubWidget() {
	    return new SubHotelDetailView(hotelBean);
	  }*/
}
