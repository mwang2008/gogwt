package com.gogwt.app.booking.businessService.domainService;

import java.util.List;

import com.gogwt.app.booking.businessService.dataaccess.CommonDAO;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.common.KeywordBean;
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

	public List<KeywordBean> getKeywordList(String keyword, final int numberResult) {
		return getCommonDAO().getKeywordList(keyword, numberResult);
	}

	
	public HotelBean getHotelDetail(final int propertyId, final UserContextBean userContext) {
		return getCommonDAO().getHotelDetail(propertyId, userContext);
	}
	
	private CommonDAO getCommonDAO() {
		return commonDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}
	
	
	
}
