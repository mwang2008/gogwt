package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.presenter;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.AppHandlerManager;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.common.presenter.Presenter;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.event.HotelSelectEvent;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view.SearchResultView;
import com.google.gwt.user.client.ui.HasWidgets;

public class SearchResultPresenter implements Presenter, SearchResultView.Presenter<HotelBean> {
	private final SearchResultView<HotelBean> view; 
	
	public SearchResultPresenter(SearchResultView<HotelBean> view) {
		this.view = view;
		this.view.setPresenter(this);		
		 
	}
	
	public void go(HasWidgets container) {
	    container.clear();
	    container.add(view.asWidget());
 	}

	/**
	 * Process select hotel
	 */
	public void doSelect(int index, HotelBean selectHotel) {
	    //to guestinfo page
		AppHandlerManager.getEventBus().fireEvent(new HotelSelectEvent());			
	}
}
