package com.gogwt.app.booking.businessService;

import com.gogwt.app.booking.businessService.domainService.CommonBusinessService;
import com.gogwt.app.booking.businessService.domainService.HotelDetailBusinessService;
import com.gogwt.app.booking.businessService.domainService.ProfileBusinessService;
import com.gogwt.app.booking.businessService.domainService.ReservationBusinessService;
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
	public static String PROFILE_DOMAIN_SERVICE = "Booking:name=domain/customer/ProfileBusinessService";
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
	
	public static ProfileBusinessService getProfileBusinessService() {
		return (ProfileBusinessService) BeanLookupService.getBean(PROFILE_DOMAIN_SERVICE);
	}
}
