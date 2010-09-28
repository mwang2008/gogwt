package com.gogwt.app.booking.rpc.services.hoteldetail;

import com.gogwt.app.booking.businessService.domainService.HotelDetailBusinessService;
import com.gogwt.app.booking.businessService.domainService.LookupBusinessService;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;

public class HotelDetailRPCController extends HotelDetailProcessServiceAdapter  {
	
	/**
	 * Get HotelDetail
	 */
	public HotelBean getHotelDetail(int hotelId, UserContextBean userContext) throws AppRemoteException {
		
		//1. call domain service
		HotelDetailBusinessService hotelDetailBusinessService = LookupBusinessService.getHotelDetailBusinessService();
		HotelBean hotelBean = hotelDetailBusinessService.getHotelDetail(hotelId, userContext);
		
		return hotelBean;
	}   
}
