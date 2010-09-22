package com.gogwt.app.booking.controllers.action.gwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.gogwt.app.booking.utils.StringUtils;

public class GWTHotelSearchController extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		 
	    return new ModelAndView("/gwt/reservation/reservation");
	}

}
