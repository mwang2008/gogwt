package com.gogwt.app.booking.dto.dataObjects.common;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @author WangM
 * todo: revisit
 */
public enum ProcessStatusEnum implements IsSerializable {
	SEARCH_FORM, 
	SEARCH_RESULT, 
	ROOM_RATE, 
	GUEST_INFO, 
	CONFIRM_RESERVATION, 
	CONFIRMATION, 
	SINGLE_PROPERTY_SEARCH, 
	OTHER;

}
