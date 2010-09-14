package com.gogwt.app.booking.gwt.mvpreservation.client.navigation;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.presenter.SearchResultPresenter;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view.SearchResultView;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view.SearchResultViewImpl;
import com.gogwt.framework.arch.widgets.AbstractPage;

public class SearchResultPage extends AbstractPage  {
	private SearchResultView<HotelBean> searchResultView;

	public SearchResultPage() {
		if (searchResultView == null) {
			searchResultView = new SearchResultViewImpl();
		}
		new SearchResultPresenter(searchResultView).go(pagePanel);
	}

	@Override
	public void process() {
		//this.pagePanel.add(new Label("SearchResultPage"));
		
		searchResultView.process();
		
/*		final ReservationContainerBean currentContainer = GWTSession
				.getCurrentReservationContainer();
   	if (currentContainer != null
				&& currentContainer.getHotelSearchRequest() != null
				&& currentContainer.getHotelSearchResponse() != null) {
			processDisplayHotelItems(currentContainer.getHotelSearchResponse());
		} else {
			// could not find, call session backup.
 			RPCReservationProxy.getInstance().getReservationContainerBeanFromSession(ProcessStatusEnum.SEARCH_RESULT,
					new CommandBean(), this);
		}	*/	 
	}



	 
}
