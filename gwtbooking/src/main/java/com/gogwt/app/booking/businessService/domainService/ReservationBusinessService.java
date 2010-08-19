package com.gogwt.app.booking.businessService.domainService;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.gogwt.app.booking.businessService.dataaccess.HotelSearchDAO;
import com.gogwt.app.booking.businessService.geocode.GeocodeFactory;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.request.GeocodeBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.request.ReservationBean;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.GeocodeResponseBean;
import com.gogwt.app.booking.dto.dataObjects.response.GeocodeResultBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;

public final class ReservationBusinessService {
   private static Logger logger = Logger.getLogger(ReservationBusinessService.class);
	
   private HotelSearchDAO hotelSearchDAO;
   
	/**
	 * Search for hotels
	 * @param searchFormBean
	 */
	public HotelSearchResponseBean findHotelsWithLocation(final SearchFormBean searchFormBean) {
		HotelSearchResponseBean hotelSearchResponse = new HotelSearchResponseBean();
			
		//1. get geocode per location
		GeocodeResponseBean geocodeResponseBean = null;
		try {
		  String location = searchFormBean.getLocation();
		  geocodeResponseBean = GeocodeFactory.getGeocodeFactory().geocodeIt(location);
		}
		catch (Exception e) {
			//todo: handle exception
		}
		
		//2. process geocode result
		
		//2.1.1 no result
		if (geocodeResponseBean == null || !geocodeResponseBean.hasValidGeocode()) {
		
		}
		
		//2.1.2 statusCode is not 200
		if (geocodeResponseBean.getCode() != 200) {
			//todo
		}
				
		//2.2 has multiple result
		if (geocodeResponseBean.getGeocodeDataList().size()>1) {
			
		}
		
		//2.3 only one geocode, serch hotels with geocode and radius
		GeocodeBean centerGeocode = new GeocodeBean();
		
		GeocodeResultBean centerGeocodeBean = geocodeResponseBean.getGeocodeDataList().get(0);
		centerGeocode.setLat(centerGeocodeBean.getLat());
		centerGeocode.setLng(centerGeocodeBean.getLng());
		centerGeocode.setRadius(searchFormBean.getRadius());
		    
		List<HotelBean> result = getHotelSearchDAO().searchHotel(centerGeocode);
		
		hotelSearchResponse.setHotelList(result);
		hotelSearchResponse.setCenterGeocode(centerGeocode);
		
		
		return hotelSearchResponse;
	}

	/**
	 * Confirm reservation. 
	 * Save confirmation 
	 * @param guestInfo
	 * @param userContext
	 */
	public ReserveResponseBean confirmReservation(final GuestInfoFormBean guestInfo, final UserContextBean userContext) {
		 ReservationBean reservationBean = new ReservationBean();
		 
		 reservationBean.setPropertyId(guestInfo.getId());
		 reservationBean.setFirstName(guestInfo.getFirstName());
		 reservationBean.setLastName(guestInfo.getLastName());
		 reservationBean.setAddress(guestInfo.getAddress());
		 reservationBean.setCity(guestInfo.getCity());
		 reservationBean.setState(guestInfo.getStateId());
		 reservationBean.setZip(guestInfo.getZipCode());
		 reservationBean.setLanguageId(userContext.getLanguageId());
		 reservationBean.setCountryId(userContext.getCountryId());
		 reservationBean.setEmail(guestInfo.getEmail());
		 reservationBean.setCreateDate(new Date());
		 
		 int reservationId = getHotelSearchDAO().confirmReservation(reservationBean);
		 logger.debug("=== reservationid="+reservationId);
		 
		 ReserveResponseBean reserveResponseBean = new ReserveResponseBean();
		 reserveResponseBean.setReserveNum(reservationId);
		 
		 return reserveResponseBean;
	}
	
	public HotelSearchDAO getHotelSearchDAO() {
		return hotelSearchDAO;
	}

	public void setHotelSearchDAO(HotelSearchDAO hotelSearchDAO) {
		this.hotelSearchDAO = hotelSearchDAO;
	}
	
	
}
