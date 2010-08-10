package com.gogwt.app.booking.controllers;

import static com.gogwt.app.booking.BookingConstants.ENV;

import javax.servlet.http.HttpServletRequest;

import com.gogwt.app.booking.config.urlmapping.UrlMappingElem;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;

public final class ControllerHelper {
	public static UserContextBean getUserContext(final HttpServletRequest request ) {
		UrlMappingElem urlMappingElem = (UrlMappingElem)request.getAttribute(ENV);
		UserContextBean userContextBean = new UserContextBean();
		userContextBean.setCountryId(urlMappingElem.getCountryId());
		userContextBean.setLanguageId(urlMappingElem.getLanguageId());
		
		return userContextBean;
	}
}
