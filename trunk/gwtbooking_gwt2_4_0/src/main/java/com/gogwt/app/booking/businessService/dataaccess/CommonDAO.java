package com.gogwt.app.booking.businessService.dataaccess;

import java.util.List;

import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.EmailConfig;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.common.KeywordBean;
import com.gogwt.app.booking.dto.dataObjects.common.StateBean;

public interface CommonDAO {
	public List<StateBean> getStateList(final UserContextBean userContext);
	public HotelBean getHotelDetail(final int propertyId, final UserContextBean userContext);
	public List<KeywordBean> getKeywordList(String keyword, final int numberResult);
    public EmailConfig getEmailConfig();
    
}
