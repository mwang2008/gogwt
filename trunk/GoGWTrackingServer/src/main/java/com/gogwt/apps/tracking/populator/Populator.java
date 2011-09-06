package com.gogwt.apps.tracking.populator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface Populator {
	public List<PopulatorItem> getPopulator(final HttpServletRequest request);
 	
}
