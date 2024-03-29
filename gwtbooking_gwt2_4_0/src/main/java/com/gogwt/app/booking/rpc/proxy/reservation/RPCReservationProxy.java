package com.gogwt.app.booking.rpc.proxy.reservation;

  

import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.rpc.interfaces.reservation.ReservationProcessServiceAsync;
import com.gogwt.app.booking.rpc.proxy.RPCProxyInterface;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * RPCReservationProxy
 * 
 *
 */
public final class RPCReservationProxy {
	private static RPCReservationProxy instance = new RPCReservationProxy();
	
	private RPCReservationProxy() {
	}

	public static RPCReservationProxy getInstance() {
		return instance;
	}
	
	/**
	 * 
	 * <p> Hotel Search </p>
	 * 
	 * @param hotelSearchFormBean
	 * @param userContext
	 * @param callback
	 */
	public void searchHotels(final SearchFormBean hotelSearch, final UserContextBean userContext, 
			       final CommandBean command,
			final RPCProxyInterface<HotelSearchResponseBean> callback) {
		
		ReservationProcessServiceAsync service = ReservationProcessServiceAsync.Util.searchHotelsAsync();
		
		service.searchHotels(hotelSearch, userContext, new AsyncCallback<HotelSearchResponseBean>() {

			//@Override
			public void onFailure(Throwable caught) {
				callback.handleRPCError(caught, command);
				
			}

			//@Override
			public void onSuccess(HotelSearchResponseBean result) {
				callback.handleRPCSuccess(result, command);	
				
			}			
		});
 	}
	
 	
	/**
	 * 
	 * <p> Reserve Hotel </p>
	 * 
	 * @param hotelSearchFormBean
	 * @param userContext
	 * @param callback
	 */
	public void reserveHotel(final GuestInfoFormBean guestInfo, final int selectedHotelItem, final UserContextBean userContext, 
			       final CommandBean command,
			final RPCProxyInterface<ReserveResponseBean> callback) {
		
		ReservationProcessServiceAsync service = ReservationProcessServiceAsync.Util.reserveHotelAsync();
		
		service.reserveHotel(guestInfo, selectedHotelItem, userContext, new AsyncCallback<ReserveResponseBean>() {

			//@Override
			public void onFailure(Throwable caught) {
				callback.handleRPCError(caught, command);				
			}

			//@Override
			public void onSuccess(ReserveResponseBean result) {
				callback.handleRPCSuccess(result, command);				
			}		 
		});
 	}
	

}
