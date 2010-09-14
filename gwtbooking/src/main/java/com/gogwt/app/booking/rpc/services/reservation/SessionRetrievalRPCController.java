package com.gogwt.app.booking.rpc.services.reservation;

import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;
import com.gogwt.app.booking.exceptions.clientserver.SessionTimedoutException;
import com.gogwt.app.booking.scopeManager.session.SessionBeanLookupService;

/**
 * <code><B>SessionRetrievalRPCController<code><B>
 * <p/>
 * Retrieve session container
 * <p/>
 */

public class SessionRetrievalRPCController extends
		ReservationProcessServiceAdapter {
	/**
	 * @see com.ihg.dec.apps.hi.services.reservation.common.ReservationProcessServiceAdapter#getReservationContainerBeanFromSession()
	 */
	public ReservationContainerBean getReservationContainerBeanFromSession(
			ProcessStatusEnum processStatusEnum) throws AppRemoteException {

		final ReservationContainerBean sessionReservationContainerBean = SessionBeanLookupService
				.getReservationSessionManager().getReservationContainerBean();

		final ReservationContainerBean reservationContainerBean = new ReservationContainerBean();

		checkSessionTimeOut(processStatusEnum, sessionReservationContainerBean);

		// ProcessStatusEnum.SEARCH_FORM
		if (processStatusEnum == ProcessStatusEnum.SEARCH_FORM) {
			reservationContainerBean
					.setHotelSearchRequest(sessionReservationContainerBean
							.getHotelSearchRequest());

			return reservationContainerBean;
		}

		// ProcessStatusEnum.SEARCH_RESULT
		if (processStatusEnum == ProcessStatusEnum.SEARCH_RESULT) {

	 		reservationContainerBean.setHotelSearchRequest(sessionReservationContainerBean.getHotelSearchRequest());
			if (sessionReservationContainerBean.getHotelSearchResponse() != null) {
				reservationContainerBean
						.setHotelSearchResponse(sessionReservationContainerBean
								.getHotelSearchResponse());
			}

			return reservationContainerBean;
		}

		// ProcessStatusEnum.SEARCH_RESULT
		if (processStatusEnum == ProcessStatusEnum.CONFIRMATION) {
			if (sessionReservationContainerBean.getReserveResponse() != null) {
				reservationContainerBean
						.setReserveResponse(sessionReservationContainerBean
								.getReserveResponse());
			}
		}

		return reservationContainerBean;
	}

	/**
	 * <p>
	 * checkSessionTimeOut
	 * </p>
	 * 
	 * @param processStatusEnum
	 * @param sessionReservationContainerBean
	 * @throws SessionTimedoutException
	 */
	private void checkSessionTimeOut(final ProcessStatusEnum processStatusEnum,
			final ReservationContainerBean sessionReservationContainerBean)
			throws SessionTimedoutException {

		if (sessionReservationContainerBean == null) {
			throw new SessionTimedoutException();
		}

		if (processStatusEnum == ProcessStatusEnum.SEARCH_RESULT) {
		    if (sessionReservationContainerBean.getHotelSearchRequest() == null) {
			   throw new SessionTimedoutException();
		    }
		}
		
		if (processStatusEnum == ProcessStatusEnum.CONFIRMATION) {
			if (sessionReservationContainerBean.getReserveResponse() == null) {
			   throw new SessionTimedoutException();
			}
		}
	}
}
