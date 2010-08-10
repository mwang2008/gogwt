package com.gogwt.app.booking.scopeManager.session;

import com.gogwt.app.booking.utils.BeanLookupService;

public class SessionBeanLookupService {
	 private static final String RESERVATION_MANAGER = "reservationSessionManager";
	 
	 /**
	   * Get Reservation Session Manager through the BeanLookupService
	   * @return instance of ReservationSessionManager
	   */
	  public static ReservationSessionManager getReservationSessionManager()
	  {
	    return (ReservationSessionManager)BeanLookupService.getBean(RESERVATION_MANAGER );
	  }
}
