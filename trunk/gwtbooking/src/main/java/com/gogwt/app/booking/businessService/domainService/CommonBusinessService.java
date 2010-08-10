package com.gogwt.app.booking.businessService.domainService;

import java.util.List;

import com.gogwt.app.booking.businessService.dataaccess.CommonDAO;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.common.StateBean;

public class CommonBusinessService {
	private CommonDAO commonDAO;

	/**
	 * Search for hotels
	 * @param searchFormBean
	 */
	public List<StateBean> getStateList(final UserContextBean userContext) {
		return getCommonDAO().getStateList(userContext);
	}

	public HotelBean getHotelDetail(final int propertyId, final UserContextBean userContext) {
		return getCommonDAO().getHotelDetail(propertyId, userContext);
	}
	
	public CommonDAO getCommonDAO() {
		return commonDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}
	
	
	
}
