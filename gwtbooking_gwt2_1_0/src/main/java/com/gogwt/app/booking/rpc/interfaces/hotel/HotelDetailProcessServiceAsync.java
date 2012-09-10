package com.gogwt.app.booking.rpc.interfaces.hotel;

import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;


public interface HotelDetailProcessServiceAsync {
	final String GET_HOTEL_DETAIL_SERVICE_URI = "gethoteldetail.rpc";
	
	/***************************************************************************
	 * Define Async interfaces
	 * 
	 **************************************************************************/
	void  getHotelDetail(int hotelId, UserContextBean userContext, AsyncCallback<HotelBean> callback);
	
	
	/**************************************************************************
	 * <code><B>Util<code><B> <p/> Utils to get service <p/>
	 * 
 	 **************************************************************************/
	class Util {
		private static HotelDetailProcessServiceAsync instance;
		
		public static HotelDetailProcessServiceAsync getHotelDetailAsync() {
			return getInstance(GET_HOTEL_DETAIL_SERVICE_URI);
		}
 		
		/**
		 * <p> Convenient method used by all method calls in this service </p>
		 * 
		 * @param uri
		 * @return
		 */
		private static HotelDetailProcessServiceAsync getInstance(String uri) {
			if (instance == null) {
				instance = (HotelDetailProcessServiceAsync) GWT.create(HotelDetailProcessService.class);
			}
			ServiceDefTarget target = (ServiceDefTarget) instance;
			target.setServiceEntryPoint(GWT.getModuleBaseURL() + uri);
			return instance;
		}
	}

}
