package com.gogwt.app.booking.controllers.action.gwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.gogwt.app.booking.controllers.BaseAbstractController;

public class GWTHotelDetailController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(GWTHotelDetailController.class);
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("handleRequestInternal");
		 
	    return new ModelAndView("/gwt/reservation/hotel_detail");
	}

}
