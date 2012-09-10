package com.gogwt.app.booking.controllers.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.gogwt.app.booking.controllers.BaseAbstractController;

public class EnrollCustomerSuccessController  extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(EnrollCustomerSuccessController.class);
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		 
		
		return new ModelAndView("/customer/enroll_success");		
		
	}

}
