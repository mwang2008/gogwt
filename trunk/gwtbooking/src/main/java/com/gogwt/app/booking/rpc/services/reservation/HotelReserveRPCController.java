package com.gogwt.app.booking.rpc.services.reservation;

import com.gogwt.app.booking.businessService.domainService.LookupBusinessService;
import com.gogwt.app.booking.businessService.domainService.ReservationBusinessService;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;
import com.gogwt.app.booking.exceptions.clientserver.SessionTimedoutException;
import com.gogwt.app.booking.scopeManager.session.SessionBeanLookupService;

/**
 * End point of RPC server side code for reserving hotel . 
 * @author WangM
 *
 */
public class HotelReserveRPCController extends ReservationProcessServiceAdapter {
	
	public ReserveResponseBean reserveHotel(GuestInfoFormBean guestInfoForm, final int selectedHotelItme,
			UserContextBean userContext) throws AppRemoteException {
		
		ReservationContainerBean reservationContainerBean = SessionBeanLookupService.getReservationSessionManager().getReservationContainerBean();
		
		//1. check session time
		if (reservationContainerBean == null || reservationContainerBean.getHotelSearchResponse() == null) {
			throw new SessionTimedoutException("Session timedout when reserve the reservation");
		}
		
		final HotelBean selectHotel = SessionBeanLookupService.getReservationSessionManager().getReservationContainerBean().getHotelSearchResponse().getHotelList().get(selectedHotelItme);
		
		
		//2. call domain service
		ReservationBusinessService reservationBusinessService = LookupBusinessService.getReservationBusinessService();		
		ReserveResponseBean reserveResponseBean =reservationBusinessService.confirmReservation(guestInfoForm, selectHotel, userContext);

		//3. set request/response to session
		reservationContainerBean.setStatus(ProcessStatusEnum.CONFIRMATION);
		reservationContainerBean.setGuestInfoBean(guestInfoForm);
		reservationContainerBean.setReserveResponse(reserveResponseBean);
		reservationContainerBean.setSelectHotelItem(selectedHotelItme);
		
		
		reservationContainerBean.setSelectedHotel(selectHotel);
 		
		return reserveResponseBean;
	}
}
