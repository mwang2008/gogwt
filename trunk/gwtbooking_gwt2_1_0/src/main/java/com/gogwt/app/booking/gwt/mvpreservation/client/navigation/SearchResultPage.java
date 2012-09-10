package com.gogwt.app.booking.gwt.mvpreservation.client.navigation;

import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.exceptions.clientserver.SessionTimedoutException;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.AppHandlerManager;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.event.BackHomeEvent;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.presenter.SearchResultPresenter;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view.SearchResultView;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view.SearchResultViewImpl;
import com.gogwt.app.booking.rpc.proxy.SessionBackupProxyInterface;
import com.gogwt.app.booking.rpc.proxy.reservation.SessionBackupProxy;
import com.gogwt.framework.arch.widgets.AbstractController;
import com.gogwt.framework.arch.widgets.PageMetaInfo;

public class SearchResultPage extends AbstractController implements
		SessionBackupProxyInterface<ReservationContainerBean> {
	
	private SearchResultView<HotelBean, HotelSearchResponseBean> searchResultView;

	public SearchResultPage() {
		if (searchResultView == null) {
			searchResultView = new SearchResultViewImpl();
		}

	}

	@Override
	public void process() {
		
		final ReservationContainerBean currentContainer = GWTSession
				.getCurrentReservationContainer();

		/*----------------------------------------------------------+
		 * Session backup in case user click refresh button. 
		 *----------------------------------------------------------*/
		if (currentContainer != null
				&& currentContainer.getHotelSearchRequest() != null
				&& currentContainer.getHotelSearchResponse() != null) {
			processDisplayHotelItems(currentContainer.getHotelSearchResponse());
			return;
		}

		// could not find, call session backup.
		SessionBackupProxy.getInstance()
				.getReservationContainerBeanFromSession(
						ProcessStatusEnum.SEARCH_RESULT, new CommandBean(),
						this);

	}

	public void handleSessionBackupRPCSuccess(ReservationContainerBean reservationContainer, CommandBean command) {
		 
		 // update gwtsession
	    if ( reservationContainer != null
	      && reservationContainer.getHotelSearchRequest() != null 
	      && reservationContainer.getHotelSearchResponse() != null) {
	      GWTSession.setCurrentReservationContainer( reservationContainer );
	      
	      processDisplayHotelItems( reservationContainer.getHotelSearchResponse() );
	      
	    } else {
 	    	//GWTExtClientUtils.redirect( VIEW_HOME );
	    	AppHandlerManager.getEventBus().fireEvent(new BackHomeEvent());
	    } 
		
	}

	public void handleSessionBackupRPCError(Throwable caught, CommandBean command) {
 		if (caught instanceof SessionTimedoutException) {
 			//todo: add error message
			AppHandlerManager.getEventBus().fireEvent(new BackHomeEvent());
			return;
		}
		
		AppHandlerManager.getEventBus().fireEvent(new BackHomeEvent());
	}
	
	/**
	 * 
	 * @param hotelSearchResponse
	 */
	private void processDisplayHotelItems(
			final HotelSearchResponseBean hotelSearchResponse) {

		new SearchResultPresenter(searchResultView).go(controlPanel);
		searchResultView.processDisplay(hotelSearchResponse);

	}

	@Override
	protected void fillMetaInfo(PageMetaInfo pageInfo) {
		// TODO Auto-generated method stub
		
	}
}
