package com.gogwt.app.booking.gwt.mvpreservation.client.navigation;

import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.VIEW_HOME;

import com.gogwt.app.booking.dto.dataObjects.BaseBean;
import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.presenter.SearchResultPresenter;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view.SearchResultView;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view.SearchResultViewImpl;
import com.gogwt.app.booking.rpc.proxy.RPCProxyInterface;
import com.gogwt.framework.arch.widgets.AbstractPage;

public class SearchResultPage extends AbstractPage implements RPCProxyInterface<BaseBean> {
	private SearchResultView<HotelBean> searchResultView;

	public SearchResultPage() {
		if (searchResultView == null) {
			searchResultView = new SearchResultViewImpl<HotelBean>();
		}
		new SearchResultPresenter(searchResultView).go(pagePanel);
	}

	@Override
	public void process() {
		// this.pagePanel.add(new Label("SearchResultPage"));
		
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



	public void handleRPCSuccess(BaseBean result, CommandBean command) {
		ReservationContainerBean reservationContainer = (ReservationContainerBean)result;
		 // update gwtsession
	    if ( reservationContainer != null
	      && reservationContainer.getHotelSearchRequest() != null 
	      && reservationContainer.getHotelSearchResponse() != null) {
	      GWTSession.setCurrentReservationContainer( reservationContainer );
	      
	      processDisplayHotelItems( reservationContainer.getHotelSearchResponse() );
	      
	    } else {
			// invoke event bus for target page
			//eventBus.fireEvent(new HotelSearchEvent());	

	    	GWTExtClientUtils.redirect( VIEW_HOME );
	    } 
		
	}

	public void handleRPCError(Throwable caught, CommandBean command) {
		  // could not find in backend server session, redirect back to
	    //home page.
		GWTExtClientUtils.redirect( VIEW_HOME );

		
	}

	private void processDisplayHotelItems(
			final HotelSearchResponseBean hotelSearchResponse) {
		
		
		//SearchResultLayoutWidget layoutWidget = new SearchResultLayoutWidget();
		//layoutWidget.prepareEntryLayout(hotelSearchResponse);

		// 1. add progress bar
		/*
		 * ProgressBarWidget progressBar = new ProgressBarWidget();
		 * progressBar.processDisplayProgressBar
		 * (ProcessStatusEnum.SEARCH_RESULT); viewPanel.add(progressBar);
		 */

		// 2. add layout
		// viewPanel.add(layoutWidget);
		//searchResultView.displayResult(hotelSearchResponse);
	}
}
