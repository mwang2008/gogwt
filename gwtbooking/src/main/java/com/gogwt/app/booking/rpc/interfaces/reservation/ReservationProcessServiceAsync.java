package com.gogwt.app.booking.rpc.interfaces.reservation;

import java.util.ArrayList;

import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.dto.dataObjects.response.SuggestiveDestinationResponseBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * ReservationProcessServiceAsync matches of ReservationProcessService
 * 
 * Util is convenience class to get instance of Async, 
 * where the connections URL such as suggestiveText.rpc are defined in servlet xml file.
 *
 */
public interface ReservationProcessServiceAsync {
	
	final String SUGGESTIVE_TEXT_SERVICE_URI = "suggestiveText.rpc";
	final String HOTEL_SEARCH_SERVICE_URI = "hotelSearch.rpc";
	final String HOTEL_RESERVE_SERVICE_URI = "reserveHotel.rpc";
	final String RESERVATION_SESSION_SERVICE_URI = "reservationSession.rpc";
	
	/***************************************************************************
	 * Define Async interfaces
	 * 
	 **************************************************************************/
	void getLocationKeyWords(String destination, UserContextBean userContext, AsyncCallback<ArrayList<SuggestiveDestinationResponseBean>> callback);
	
	void  searchHotels(SearchFormBean hotelSearch, UserContextBean userContext, AsyncCallback<HotelSearchResponseBean> callback);
	
	void  reserveHotel(GuestInfoFormBean guestInfoForm, UserContextBean userContext, AsyncCallback<ReserveResponseBean> callback);
	
	void  getReservationContainerBeanFromSession(ProcessStatusEnum processStatusEnum, AsyncCallback<ReservationContainerBean> callback);
	
	/**************************************************************************
	 * <code><B>Util<code><B> <p/> Utils to get service <p/>
	 * 
 	 **************************************************************************/
	class Util {
		private static ReservationProcessServiceAsync instance;
		
		public static ReservationProcessServiceAsync getLocationKeyWordsAsync() {
			return getInstance(SUGGESTIVE_TEXT_SERVICE_URI);
		}
		
		public static ReservationProcessServiceAsync searchHotelsAsync() {
			return getInstance(HOTEL_SEARCH_SERVICE_URI);
		}
		
		public static ReservationProcessServiceAsync reserveHotelAsync() {
			return getInstance(HOTEL_RESERVE_SERVICE_URI);
		}
		
		public static ReservationProcessServiceAsync getSeesionBackupAsync() {
			return getInstance(RESERVATION_SESSION_SERVICE_URI);
		}
		
		
		/**
		 * <p> Convenient method used by all method calls in this service </p>
		 * 
		 * @param uri
		 * @return
		 */
		private static ReservationProcessServiceAsync getInstance(String uri) {
			if (instance == null) {
				instance = (ReservationProcessServiceAsync) GWT.create(ReservationProcessService.class);
			}
			ServiceDefTarget target = (ServiceDefTarget) instance;
			target.setServiceEntryPoint(GWT.getModuleBaseURL() + uri);
			return instance;
		}
	}


	 
}
