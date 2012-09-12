package com.gogwt.app.booking.businessService.domainService;

import org.apache.log4j.Logger;

import com.gogwt.app.booking.businessService.dataaccess.CommonDAO;
import com.gogwt.app.booking.businessService.dataaccess.HotelSearchDAO;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;

public final class HotelDetailBusinessService {
	 private static Logger logger = Logger.getLogger(HotelDetailBusinessService.class);
	 		
	 private CommonDAO commonDAO;
	 
	 public HotelBean getHotelDetail(int hotelId, UserContextBean userContext) throws AppRemoteException {
		 return getCommonDAO().getHotelDetail(hotelId, userContext);
	 }

	public CommonDAO getCommonDAO() {
		return commonDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

 
}
