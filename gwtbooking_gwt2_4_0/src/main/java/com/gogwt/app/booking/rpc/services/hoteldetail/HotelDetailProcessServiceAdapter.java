package com.gogwt.app.booking.rpc.services.hoteldetail;

import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;
import com.gogwt.app.booking.exceptions.clientserver.UnsupportedException;
import com.gogwt.app.booking.rpc.interfaces.hotel.HotelDetailProcessService;
import com.gogwt.app.booking.rpc.services.BaseService;

public abstract class HotelDetailProcessServiceAdapter extends BaseService implements HotelDetailProcessService {

	public HotelBean getHotelDetail(int hotelId, UserContextBean userContext)
			throws AppRemoteException {
		 
		throw new UnsupportedException();
	}

}
