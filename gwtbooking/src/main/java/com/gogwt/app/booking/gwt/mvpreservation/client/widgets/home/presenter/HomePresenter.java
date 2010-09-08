package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.presenter;

import java.util.ArrayList;

import com.gogwt.app.booking.dto.dataObjects.BaseBean;
import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.common.presenter.Presenter;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.event.HotelSearchEvent;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.view.HomeLayoutView;
import com.gogwt.app.booking.rpc.proxy.RPCProxyInterface;
import com.gogwt.app.booking.rpc.proxy.reservation.RPCReservationProxy;
import com.gogwt.framework.arch.utils.GWTStringUtils;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;


public class HomePresenter implements Presenter, HomeLayoutView.Presenter<SearchFormBean>, RPCProxyInterface<BaseBean> {
	 
	
	private final HandlerManager eventBus;
	private final HomeLayoutView<SearchFormBean> view; 
	//private SearchFormBean searchFormBean;
	//private final HomeLayoutView.Presenter presenter;
	
	public HomePresenter(HandlerManager eventBus,
			HomeLayoutView<SearchFormBean> view) {
		
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);		
		//this.presenter = presenter;
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
		if (GWTStringUtils.isSet(errorList)) {
 			view.fillErrorMsg(errorList);
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
	public void handleRPCSuccess(BaseBean result, CommandBean command) {
        HotelSearchResponseBean hotelSearchResponse = (HotelSearchResponseBean)result;
				
		GWTSession.getCurrentReservationContainer().setHotelSearchResponse(hotelSearchResponse);

		// invoke event bus for target page
		eventBus.fireEvent(new HotelSearchEvent());	
	}

	public void handleRPCError(Throwable caught, CommandBean command) {
		// TODO Auto-generated method stub
		
	}

	 
}
