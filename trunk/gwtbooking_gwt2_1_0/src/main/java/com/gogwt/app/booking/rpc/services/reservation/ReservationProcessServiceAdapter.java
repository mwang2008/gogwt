package com.gogwt.app.booking.rpc.services.reservation;

import java.util.List;

import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.KeywordBean;
import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;
import com.gogwt.app.booking.exceptions.clientserver.UnsupportedException;
import com.gogwt.app.booking.rpc.interfaces.reservation.ReservationProcessService;
import com.gogwt.app.booking.rpc.services.BaseService;

 

public abstract class ReservationProcessServiceAdapter extends BaseService implements
		ReservationProcessService {

	 
	public List<KeywordBean> getLocationKeyWords(
			String destination, UserContextBean userContext)
			throws AppRemoteException {
		throw new UnsupportedException();
	}

	 
	public HotelSearchResponseBean searchHotels(
			SearchFormBean hotelSearchDTO, UserContextBean userContext)
			throws AppRemoteException {
		throw new UnsupportedException();
	}

 
	public ReserveResponseBean reserveHotel(GuestInfoFormBean guestInfoForm, final int selectedHotelItme,
			UserContextBean userContext) throws AppRemoteException {
		throw new UnsupportedException();
	}


	public ReservationContainerBean getReservationContainerBeanFromSession(
			ProcessStatusEnum processStatusEnum) throws AppRemoteException {
		throw new UnsupportedException();
	}
 
	
}
