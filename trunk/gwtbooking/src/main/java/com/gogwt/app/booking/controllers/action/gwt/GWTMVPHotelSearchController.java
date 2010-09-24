package com.gogwt.app.booking.controllers.action.gwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.gogwt.app.booking.controllers.action.HotelSearchController;
import com.gogwt.app.booking.utils.StringUtils;

/**
 * 
 * @author WangM
 *
 */
public class GWTMVPHotelSearchController extends AbstractController {
	private static Logger logger = Logger.getLogger(GWTMVPHotelSearchController.class);
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("handleRequestInternal");
		 
	    return new ModelAndView("/gwt/reservation/reservation_mvp");
	}

}
