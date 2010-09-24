package com.gogwt.app.booking.controllers.helper;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.utils.MessageUtils;
import com.gogwt.framework.arch.utils.GWTStringUtils;

public class DisplayAmenitiesJSPHelper {
	private static final String amenities_tennis = MessageUtils.getMessage("amenities.tennis");
	private static final String amenities_pet_allowed = MessageUtils.getMessage("amenities.pet.allowed");

	private static final String amenities_indoor_pool = MessageUtils.getMessage("amenities.indoor.pool");
	private static final String amenities_outdoor_pool = MessageUtils.getMessage("amenities.outdoor.pool");
	private static final String amenities_kitchen = MessageUtils.getMessage("amenities.kitchen");
	
	/**
	 * Use server side code
	 * JSP custom tag
	 * @param hotelBean
	 * @param tags
	 * @return
	 * 
	 */
	public static String fillAmenities(final HotelBean hotelBean) {
		StringBuilder amenities = new StringBuilder();

		boolean hasAmenities = false;
		if (GWTStringUtils.equalsIgnoreCase("Y", hotelBean.getHasTennisCourt())) {
			amenities.append(hasAmenities == true ? ", " : "");
			amenities.append(amenities_tennis);
			hasAmenities = true;
		}
		if (GWTStringUtils.equalsIgnoreCase("Y", hotelBean.getHasPetsAllowed())) {
			amenities.append(hasAmenities == true ? ", " : "");
			amenities.append(amenities_pet_allowed);
			hasAmenities = true;
		}
		if (GWTStringUtils.equalsIgnoreCase("Y", hotelBean.getHasIndoorPool())) {
			amenities.append(hasAmenities == true ? ", " : "");
			amenities.append(amenities_indoor_pool);
			hasAmenities = true;
		}
		if (GWTStringUtils.equalsIgnoreCase("Y", hotelBean.getHasOutdoorPool())) {
			amenities.append(hasAmenities == true ? ", " : "");
			amenities.append(amenities_outdoor_pool);
			hasAmenities = true;
		}
		if (GWTStringUtils.equalsIgnoreCase("Y", hotelBean.getHasKitchen())) {
			amenities.append(hasAmenities == true ? ", " : "");
			amenities.append(amenities_kitchen);
			hasAmenities = true;
		}
		 
		return amenities.toString();	
	}
}
