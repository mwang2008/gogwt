package com.gogwt.app.booking.scopeManager.session;

import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;

public class ReservationSessionManager {
	private ReservationContainerBean reservationContainerBean;

	public ReservationContainerBean getReservationContainerBean() {
	   if (reservationContainerBean == null) {
			  reservationContainerBean = new ReservationContainerBean();
	   }
	   return reservationContainerBean;
	}

	public void setReservationContainerBean(
			ReservationContainerBean reservationContainerBean) {
		this.reservationContainerBean = reservationContainerBean;
	}
 
}
