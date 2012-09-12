package com.gogwt.app.booking.gwt.reservation.client.navigation;

import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.VIEW_HOME;

import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.ProgressBarWidget;
import com.gogwt.app.booking.gwt.reservation.client.widgets.searchresult.SearchResultLayoutWidget;
import com.gogwt.app.booking.rpc.proxy.SessionBackupProxyInterface;
import com.gogwt.app.booking.rpc.proxy.reservation.SessionBackupProxy;
import com.gogwt.framework.arch.widgets.AbstractController;
import com.gogwt.framework.arch.widgets.PageMetaInfo;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

 
public class SearchResultView extends AbstractController implements SessionBackupProxyInterface<ReservationContainerBean> {
	//private SearchResultLayoutWidget layoutWidget = new SearchResultLayoutWidget();
	
	@Override
	public void process() {
		 this.controlPanel.add(new Label("SearchResultView "));
		 
		 controlPanel.clear();
	 	 
		 final ReservationContainerBean currentContainer = GWTSession.getCurrentReservationContainer();
		 
	 	 if (currentContainer != null && 
	 	     currentContainer.getHotelSearchRequest() != null && 
	 		 currentContainer.getHotelSearchResponse() != null) {
	 		processDisplayHotelItems( currentContainer.getHotelSearchResponse() );
		 }
	 	 else {
	 		 // could not find, call session backup.	 	     
	 		SessionBackupProxy.getInstance()
				.getReservationContainerBeanFromSession(
						ProcessStatusEnum.SEARCH_RESULT, new CommandBean(),
						this);
	 	 }
	 	 
	
	}

	public void handleSessionBackupRPCSuccess(ReservationContainerBean reservationContainer, CommandBean command) {
		 
		
		 // update gwtsession
	    if ( reservationContainer != null
	      && reservationContainer.getHotelSearchRequest() != null 
	      && reservationContainer.getHotelSearchResponse() != null) {
	      GWTSession.setCurrentReservationContainer( reservationContainer );
       
	      processDisplayHotelItems( reservationContainer.getHotelSearchResponse() );
	    } else {
	    	GWTExtClientUtils.redirect( VIEW_HOME );
	    } 
		
	}

	public void handleSessionBackupRPCError(Throwable caught, CommandBean command) {
		 
		  // could not find in backend server session, redirect back to
	    // hotelsearch/home page.
		GWTExtClientUtils.redirect( VIEW_HOME );
	}

	
	
	  /**
	   * <p>
	   * processDisplayHotelItems
	   * </p>
	   * @param hotelSearchResponse
	   */
	  /**
	   * @param hotelSearchResponse
	   */
	  private void processDisplayHotelItems(
	    final HotelSearchResponseBean hotelSearchResponse )
	  {
			 SearchResultLayoutWidget layoutWidget = new SearchResultLayoutWidget();
			 layoutWidget.prepareEntryLayout(hotelSearchResponse);
			 
			 //1. add progress bar
			 ProgressBarWidget progressBar = new ProgressBarWidget();
			 progressBar.processDisplayProgressBar(ProcessStatusEnum.SEARCH_RESULT);
			 controlPanel.add(progressBar);
			 
			 controlPanel.add(new HTML("<br/>"));
			 
			 //2. add layout
			 controlPanel.add(layoutWidget);
	  }

	@Override
	protected void fillMetaInfo(PageMetaInfo pageInfo) {
		pageInfo.setTitle("Hotel Search Result");
		
	}
}
