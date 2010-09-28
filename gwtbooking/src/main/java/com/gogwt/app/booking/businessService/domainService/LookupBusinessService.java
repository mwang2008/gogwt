package com.gogwt.app.booking.businessService.domainService;

import com.gogwt.app.booking.utils.BeanLookupService;

/**
 * Locate domain service defined in domonService.xml 
 * @author WangM
 *
 */
public final class LookupBusinessService {

	public static String RESERVATION_DOMAIN_SERVICE = "Booking:name=domain/reservation/ReservationBusinessService";
	public static String COMMON_DOMAIN_SERVICE = "Booking:name=domain/reservation/CommonBusinessService";
	public static String HOTELDETAIL_DOMAIN_SERVICE = "Booking:name=domain/hoteldetail/HotelDetailBusinessService";
	
	/**
	 * <p>
	 * Look up Reservation Service
	 * </p>
	 * 
	 * @return instance of Reservation
	 */
	public static ReservationBusinessService getReservationBusinessService() {
		return (ReservationBusinessService) BeanLookupService.getBean(RESERVATION_DOMAIN_SERVICE);
	}
	
	public static CommonBusinessService getCommonBusinessService() {
		return (CommonBusinessService) BeanLookupService.getBean(COMMON_DOMAIN_SERVICE);
	}
	
	public static HotelDetailBusinessService getHotelDetailBusinessService() {
		return (HotelDetailBusinessService) BeanLookupService.getBean(HOTELDETAIL_DOMAIN_SERVICE);
	}
}
