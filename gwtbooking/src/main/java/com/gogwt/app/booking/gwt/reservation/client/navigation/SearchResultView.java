package com.gogwt.app.booking.gwt.reservation.client.navigation;

import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.ProgressBarWidget;
import com.gogwt.app.booking.gwt.reservation.client.widgets.searchresult.SearchResultLayoutWidget;
import com.gogwt.framework.arch.widgets.AbstractView;
import com.google.gwt.user.client.ui.Label;

public class SearchResultView extends AbstractView {
	//private SearchResultLayoutWidget layoutWidget = new SearchResultLayoutWidget();
	
	@Override
	public void process() {
		 this.viewPanel.add(new Label("SearchResultView "));
		 
		 viewPanel.clear();
		 
		 final HotelSearchResponseBean hotelSearchResponseBean = GWTSession.getCurrentReservationContainer().getHotelSearchResponseBean();
		  
		 SearchResultLayoutWidget layoutWidget = new SearchResultLayoutWidget();
		 layoutWidget.prepareEntryLayout(hotelSearchResponseBean);
		 
		 //1. add progress bar
		 ProgressBarWidget progressBar = new ProgressBarWidget();
		 progressBar.processDisplayProgressBar(ProcessStatusEnum.SEARCH_RESULT);
		 viewPanel.add(progressBar);
		 
		 //2. add layout
		 viewPanel.add(layoutWidget);
	}

}
