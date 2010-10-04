package com.gogwt.app.booking.populator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;

public interface Populator {
	public List<PopulatorItem> getPopulator(final HttpServletRequest request);
	public List<PopulatorItem> getPopulator(final UserContextBean userContext);
	
}
