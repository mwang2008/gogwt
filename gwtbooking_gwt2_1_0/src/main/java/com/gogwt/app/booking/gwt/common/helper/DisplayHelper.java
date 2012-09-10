package com.gogwt.app.booking.gwt.common.helper;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.framework.arch.utils.StringUtils;

public final class DisplayHelper {
 	
	public static String fullAddress(final HotelBean hotelBean) {
		StringBuilder addressBuilder = new StringBuilder();
		addressBuilder.append(hotelBean.getAddress());
		if (StringUtils.isSet(hotelBean.getAddress2())) {
			addressBuilder.append(" ");
			addressBuilder.append(hotelBean.getAddress2());
		}
		if (StringUtils.isSet(hotelBean.getCity())) {
			addressBuilder.append(", ");
			addressBuilder.append(hotelBean.getCity());
		}
		
		if (StringUtils.isSet(hotelBean.getState())) {
			addressBuilder.append(", ");
			addressBuilder.append(hotelBean.getState());
		}

		if (StringUtils.isSet(hotelBean.getZipCode())) {
			addressBuilder.append(" ");
			addressBuilder.append(hotelBean.getZipCode());
		}

		if (StringUtils.isSet(hotelBean.getCountry())) {
			addressBuilder.append(", ");
			addressBuilder.append(hotelBean.getCountry());
		}
		
		return addressBuilder.toString();
	}
	
	public static String fullAddress(final GuestInfoFormBean guestInfo) {
		StringBuilder addressBuilder = new StringBuilder();
 
		if (StringUtils.isSet(guestInfo.getAddress())) {
			addressBuilder.append(" ");
			addressBuilder.append(guestInfo.getAddress());
		}
		if (StringUtils.isSet(guestInfo.getCity())) {
			addressBuilder.append(", ");
			addressBuilder.append(guestInfo.getCity());
		}
		
		if (StringUtils.isSet(guestInfo.getStateId())) {
			addressBuilder.append(", ");
			addressBuilder.append(guestInfo.getStateId());
		}

		if (StringUtils.isSet(guestInfo.getZipCode())) {
			addressBuilder.append(" ");
			addressBuilder.append(guestInfo.getZipCode());
		}
 		
		return addressBuilder.toString();
	}
	
	
	public static String dispReservationNum(int reservationNum) {
		//DecimalFormat df = new DecimalFormat("000000000");
		String resNum = Integer.toString(reservationNum);
		StringBuilder sbbuild = new StringBuilder();
		
		if (resNum.length() < 10) {
		   for (int i=0; i<10-resNum.length(); i++) {
			   sbbuild.append("0");
		   }
		}
		sbbuild.append(resNum);
		
		return sbbuild.toString();
	}
}
