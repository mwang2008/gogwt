package com.gogwt.app.booking.rpc.services.reservation;

import com.gogwt.app.booking.businessService.domainService.LookupBusinessService;
import com.gogwt.app.booking.businessService.domainService.ReservationBusinessService;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;

/**
 * End point of RPC server side code for reserving hotel . 
 * @author WangM
 *
 */
public class HotelReserveRPCController extends ReservationProcessServiceAdapter {
	
	public ReserveResponseBean reserveHotel(GuestInfoFormBean guestInfoForm,
			UserContextBean userContext) throws AppRemoteException {
		
		ReservationBusinessService reservationBusinessService = LookupBusinessService.getReservationBusinessService();		
		ReserveResponseBean reserveResponseBean =reservationBusinessService.confirmReservation(guestInfoForm, userContext);

		return reserveResponseBean;
	}
}
