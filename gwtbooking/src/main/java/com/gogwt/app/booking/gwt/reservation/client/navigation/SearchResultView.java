package com.gogwt.app.booking.gwt.reservation.client.navigation;

import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.VIEW_HOME;

import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.reservation.client.processor.ReservationSessionBackup;
import com.gogwt.app.booking.gwt.reservation.client.processor.ReservationSessionBackupProcessor;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.ProgressBarWidget;
import com.gogwt.app.booking.gwt.reservation.client.widgets.searchresult.SearchResultLayoutWidget;
import com.gogwt.framework.arch.widgets.AbstractView;
import com.google.gwt.user.client.ui.Label;

 
public class SearchResultView extends AbstractView implements ReservationSessionBackup{
	//private SearchResultLayoutWidget layoutWidget = new SearchResultLayoutWidget();
	
	@Override
	public void process() {
		 this.viewPanel.add(new Label("SearchResultView "));
		 
		 viewPanel.clear();
	 	 
		 final ReservationContainerBean currentContainer = GWTSession.getCurrentReservationContainer();
		 
	 	 if (currentContainer != null && 
	 	     currentContainer.getHotelSearchRequest() != null && 
	 		 currentContainer.getHotelSearchResponse() != null) {
	 		processDisplayHotelItems( currentContainer.getHotelSearchResponse() );
		 }
	 	 else {
	 		 // could not find, call session backup.
	 	      new ReservationSessionBackupProcessor(
	 	        this, ProcessStatusEnum.SEARCH_RESULT, null ); 
	 	 }
	 	 
	
	}

	public void onSuccessSessionBackup(ReservationContainerBean reservationContainer,
			CommandBean command) {
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

	public void onErrorSessionBackup(Throwable caught, CommandBean command) {
		 
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
			 viewPanel.add(progressBar);
			 
			 //2. add layout
			 viewPanel.add(layoutWidget);
	  }
}
