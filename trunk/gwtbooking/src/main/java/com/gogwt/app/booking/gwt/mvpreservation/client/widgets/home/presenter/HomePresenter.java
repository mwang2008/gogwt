package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.presenter;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.AppHandlerManager;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.common.presenter.Presenter;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.event.HotelSearchEvent;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.validate.SearchValidate;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.view.HomeLayoutView;
import com.gogwt.app.booking.rpc.proxy.RPCProxyInterface;
import com.gogwt.app.booking.rpc.proxy.reservation.RPCReservationProxy;
import com.gogwt.framework.arch.utils.StringUtils;
import com.google.gwt.user.client.ui.HasWidgets;


public class HomePresenter implements Presenter, HomeLayoutView.Presenter<SearchFormBean>, RPCProxyInterface<HotelSearchResponseBean> {
 	 
	private final HomeLayoutView<SearchFormBean> view; 
 	
	public HomePresenter(HomeLayoutView<SearchFormBean> view) {
		
		//this.eventBus = 
		this.view = view;
		this.view.setPresenter(this);		
		 
	}

	public void go(HasWidgets container) {
	    container.clear();
	    container.add(view.asWidget());
 	}

	/**
	 * Handle search 
	 */
	public void doSearch() {
		
		//1. get form value
		SearchFormBean formBean = view.toValue();
		
		//2. validate form value
		ArrayList<String> errorList = new SearchValidate().validate(formBean);
		if (StringUtils.isSet(errorList)) {
 			view.dispErrorMsg(errorList);
			return;
		}
		
		//3. call RPC 
 		/*-------------------------------------------------
		 * The purpose of using RPC proxy is:
		 * 1. Easy to follow/read the logic for the main flow, such as this method of onClick. 
		 *    No big block of handle RPC onSuccess and onError 
		 * 2. add control function to RPC proxy, such as set timed out, avoid duplicate call etc.  
		 *------------------------------------------------*/
		GWTSession.getCurrentReservationContainer().setHotelSearchRequest(formBean);
		RPCReservationProxy.getInstance().searchHotels(formBean,
				GWTExtClientUtils.getUserContext(), new CommandBean(), this);
				
	}

	/**
	 * Call back for the RPC of searchHotels
	 */
	public void handleRPCSuccess(HotelSearchResponseBean hotelSearchResponse, CommandBean command) {
     			
		GWTSession.getCurrentReservationContainer().setHotelSearchResponse(hotelSearchResponse);

		// invoke event bus for target page
		AppHandlerManager.getEventBus().fireEvent(new HotelSearchEvent());	
	}

	public void handleRPCError(Throwable caught, CommandBean command) {
		Log.debug("Could not search hotel ");
		
	}

	 

	 
}
