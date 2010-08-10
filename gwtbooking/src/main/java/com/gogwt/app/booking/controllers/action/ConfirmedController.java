package com.gogwt.app.booking.controllers.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class ConfirmedController extends AbstractController  {

	@Override
	protected ModelAndView handleRequestInternal(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	    
		logger.debug("handleRequestInternal");
		
	      
	    return new ModelAndView("/reservation/confirmed_reservation");
	}

}
