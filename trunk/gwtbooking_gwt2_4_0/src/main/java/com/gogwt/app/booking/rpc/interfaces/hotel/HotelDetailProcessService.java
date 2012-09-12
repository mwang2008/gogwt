package com.gogwt.app.booking.rpc.interfaces.hotel;

import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;
import com.google.gwt.user.client.rpc.RemoteService;

public interface HotelDetailProcessService extends RemoteService {
	HotelBean getHotelDetail(int hotelId, UserContextBean userContext) throws AppRemoteException;
}
