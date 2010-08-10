package com.gogwt.app.booking.populator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface BasePopulator {
	public List getPopulator(final HttpServletRequest request);
}
