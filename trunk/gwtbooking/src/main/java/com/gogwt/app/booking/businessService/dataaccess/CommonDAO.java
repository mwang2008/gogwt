package com.gogwt.app.booking.businessService.dataaccess;

import java.util.List;

import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.common.StateBean;

public interface CommonDAO {
	public List<StateBean> getStateList(final UserContextBean userContext);
	public HotelBean getHotelDetail(final int propertyId, final UserContextBean userContext);
}
