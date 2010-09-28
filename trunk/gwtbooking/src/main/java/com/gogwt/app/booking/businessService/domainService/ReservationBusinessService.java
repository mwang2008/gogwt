package com.gogwt.app.booking.businessService.domainService;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.gogwt.app.booking.businessService.dataaccess.HotelSearchDAO;
import com.gogwt.app.booking.businessService.geocode.EarthGeoUtils;
import com.gogwt.app.booking.businessService.geocode.GeocodeFactory;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.GeoCodeBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.request.GeoSearchBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.request.ReservationBean;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.GeocodeResponseBean;
import com.gogwt.app.booking.dto.dataObjects.response.GeocodeResultBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;
import com.gogwt.app.booking.exceptions.clientserver.InvalidateGeocodeException;
import com.gogwt.framework.arch.utils.StringUtils;
 

public final class ReservationBusinessService {
   private static Logger logger = Logger.getLogger(ReservationBusinessService.class);
	
   private HotelSearchDAO hotelSearchDAO;
   
	/**
	 * Search for hotels
	 * @param searchFormBean
	 */
	public HotelSearchResponseBean findHotelsWithLocation(final SearchFormBean searchFormBean) throws AppRemoteException {
		 	
		//1. search if location looks like lat, lng
		GeoCodeBean geoCode = fillIfLocationIsLatLng(searchFormBean.getLocation());
		if (geoCode != null) {
			GeoSearchBean centerGeocode = new GeoSearchBean();
			centerGeocode.setLat(geoCode.getLat());
			centerGeocode.setLng(geoCode.getLng());
			centerGeocode.setRadius(searchFormBean.getRadius());
		    return findHotelsForGivenGeocode(centerGeocode);	  	
		}
		
		//2. if SearchFormBean has geocode, use it 
		if (searchFormBean.getGeoCode() != null) {
			GeoSearchBean centerGeocode = new GeoSearchBean();
			centerGeocode.setLat(searchFormBean.getGeoCode().getLat());
			centerGeocode.setLng(searchFormBean.getGeoCode().getLng());
			centerGeocode.setRadius(searchFormBean.getRadius());
		    return findHotelsForGivenGeocode(centerGeocode);	
		}
 		
		//3. get geocode per location
		final GeocodeResponseBean geocodeResponseBean = geocodeIt(searchFormBean.getLocation());
				
		//3.1. has multiple result
		// Simplicity for DEMO, use the first one 
		if (geocodeResponseBean.getGeocodeDataList().size()>1) {
			logger.debug("has multiple geocde results for " + searchFormBean.getLocation());
		}
				
		//3.2 has only one geocode, search hotels with geocode and radius
		final GeocodeResultBean centerGeocodeBean = geocodeResponseBean.getGeocodeDataList().get(0);
		
		final GeoSearchBean centerGeocode = new GeoSearchBean();
 		centerGeocode.setLat(centerGeocodeBean.getLat());
		centerGeocode.setLng(centerGeocodeBean.getLng());
		centerGeocode.setRadius(searchFormBean.getRadius());
	 	 
		return findHotelsForGivenGeocode(centerGeocode);
 		 
	}


	public GeocodeResponseBean geocodeIt(final String location) throws AppRemoteException {
		GeocodeResponseBean geocodeResponseBean = null;
		
		try {
			geocodeResponseBean = GeocodeFactory.getGeocodeFactory().geocodeIt(location);
		}
		catch (Exception e) {
			throw new InvalidateGeocodeException(location);
		}
		 	
		// no result
		if (geocodeResponseBean == null || !geocodeResponseBean.hasValidGeocode()) {
			throw new InvalidateGeocodeException(location);
		}
			
		// statusCode is not 200
		if (geocodeResponseBean.getCode() != 200) {
			throw new InvalidateGeocodeException(location);
		}
			
		return geocodeResponseBean;
	}
	
	public HotelSearchResponseBean findHotelsForGivenGeocode(final GeoSearchBean centerGeocode) {
		HotelSearchResponseBean hotelSearchResponse = new HotelSearchResponseBean();
		
		List<HotelBean> result = getHotelSearchDAO().searchHotel(centerGeocode);
		
		 // return if hotel is not found.
        if (result == null || result.isEmpty()) {
            return hotelSearchResponse;
        }
        
		// remove the hotel which outside the radius
		double distance;
		if (result != null && !result.isEmpty()) {
	 		for (HotelBean hotel : result) {
			   distance = EarthGeoUtils.arcDistance(centerGeocode.getLat(), centerGeocode.getLng(), hotel.getLat(), hotel.getLng() );
			   hotel.setDistance(distance);
			   //todo: remove outbound hotels later on when optimize using light version of hotels
/*			   if (distance > searchFormBean.getRadius() ) {
				   result.remove(hotel);
				   continue;
			   }
*/ 			}
			hotelSearchResponse.setHotelList(result);
		}
		
		// sort by distance.
        Collections.sort(result, new HotelDistanceComparator());
       
		hotelSearchResponse.setCenterGeocode(centerGeocode);
		return hotelSearchResponse;
	}
	
	/**
	 * Confirm reservation. 
	 * Save confirmation 
	 * @param guestInfo
	 * @param userContext
	 */
	public ReserveResponseBean confirmReservation(final GuestInfoFormBean guestInfo, final HotelBean selectHotel, final UserContextBean userContext) {
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
		 reserveResponseBean.setGuestInfo(guestInfo);
		 reserveResponseBean.setSelectHotel(selectHotel);
		 
		 return reserveResponseBean;
	}
	
	public HotelSearchDAO getHotelSearchDAO() {
		return hotelSearchDAO;
	}

	public void setHotelSearchDAO(HotelSearchDAO hotelSearchDAO) {
		this.hotelSearchDAO = hotelSearchDAO;
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	///  PRIVATE
	///
	/**
	 * test to see whether the location is with [lat, lng] 
	 * ex. 33.754487, -84.389663
	 * @param location
	 * @return
	 */ 
	private GeoCodeBean fillIfLocationIsLatLng(final String location) {
	   
	   GeoCodeBean geoCode = new GeoCodeBean();
	   
	   if (!StringUtils.isSet(location)) {
		   return null;
	   }
	   
	   int commaPos = location.indexOf(",");
	   if (commaPos == -1) {
		   return null;
	   }
	   
	   String latStr = location.substring(0, commaPos);
	   String lngStr = location.substring(commaPos+1);
	   
	   try {
		   double lat = Double.parseDouble(latStr.trim());
		   geoCode.setLat(lat);
	   }
	   catch (NumberFormatException e) {
		   return null;
	   }
	
	   try {
		   double lng = Double.parseDouble(lngStr.trim());
		   geoCode.setLng(lng);
	   }
	   catch (NumberFormatException e) {
		   return null;
	   }
	   
	   return geoCode;
	}
	
}
