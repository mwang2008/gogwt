package com.gogwt.app.booking.gwt.common.helper;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.framework.arch.utils.StringUtils;

public class DisplayAmenitiesGWTHelper {
	/**
	 * Use by GWT 
	 * @param hotelBean
	 * @return	 
	 */
	public static String fillAmenities(final HotelBean hotelBean, TagsReservationResources tags) {
		StringBuilder amenities = new StringBuilder();

		boolean hasAmenities = false;
		if (StringUtils.equalsIgnoreCase("Y", hotelBean.getHasTennisCourt())) {
			amenities.append(hasAmenities == true ? ", " : "");
			amenities.append(tags.amenities_tennis());
			hasAmenities = true;
		}
		if (StringUtils.equalsIgnoreCase("Y", hotelBean.getHasPetsAllowed())) {
			amenities.append(hasAmenities == true ? ", " : "");
			amenities.append(tags.amenities_pet_allowed());
			hasAmenities = true;
		}
		if (StringUtils.equalsIgnoreCase("Y", hotelBean.getHasIndoorPool())) {
			amenities.append(hasAmenities == true ? ", " : "");
			amenities.append(tags.amenities_indoor_pool());
			hasAmenities = true;
		}
		if (StringUtils.equalsIgnoreCase("Y", hotelBean.getHasOutdoorPool())) {
			amenities.append(hasAmenities == true ? ", " : "");
			amenities.append(tags.amenities_outdoor_pool());
			hasAmenities = true;
		}
		if (StringUtils.equalsIgnoreCase("Y", hotelBean.getHasKitchen())) {
			amenities.append(hasAmenities == true ? ", " : "");
			amenities.append(tags.amenities_kitchen());
			hasAmenities = true;
		}
		 
		return amenities.toString();	
	}
}
