package com.gogwt.app.booking.rpc.interfaces.reservation;

import java.util.ArrayList;

import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.dto.dataObjects.response.SuggestiveDestinationResponseBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;
import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Remote services
 * 
 * @author Wang,Michael
 *
 */
public interface ReservationProcessService extends RemoteService {
	
	/**
	 * Retrieve values for dropdown list of destination
	 * @param destination
	 * @param userContext
	 * @return
	 * @throws AppRemoteException
	 */
	ArrayList<SuggestiveDestinationResponseBean> getLocationKeyWords(String destination, UserContextBean userContext) throws AppRemoteException;
	
	/**
	 * Search hotel with location and radius.
	 * @param hotelSearch
	 * @param userContext
	 * @return
	 * @throws AppRemoteException
	 */
	HotelSearchResponseBean searchHotels(SearchFormBean hotelSearch, UserContextBean userContext) throws AppRemoteException;
	
	/**
	 * Create reservation with guestinfo and selected hotelId.
	 * @param guestInfoForm
	 * @param userContext
	 * @return
	 * @throws AppRemoteException
	 */
	ReserveResponseBean reserveHotel(GuestInfoFormBean guestInfoForm, UserContextBean userContext) throws AppRemoteException;
	
}
