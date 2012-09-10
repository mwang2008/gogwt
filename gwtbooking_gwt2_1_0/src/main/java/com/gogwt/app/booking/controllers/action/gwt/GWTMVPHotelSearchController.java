package com.gogwt.app.booking.controllers.action.gwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.gogwt.app.booking.controllers.BaseAbstractController;

/**
 * 
 * @author WangM
 *
 */
public class GWTMVPHotelSearchController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(GWTMVPHotelSearchController.class);
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("handleRequestInternal");
		 
	    return new ModelAndView("/gwt/reservation/reservation_mvp");
	}

}
