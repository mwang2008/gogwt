package com.gogwt.apps.tracking.controllers;

import static com.gogwt.apps.tracking.AppConstants.CUSTOMER_PROFILE;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class LogoutController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(LogoutController.class);

	@Override
	protected ModelAndView handleRequestInternal(
			final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("handleRequestInternal");

		HttpSession session = request.getSession();
		session.removeAttribute(CUSTOMER_PROFILE);

		String targetURL = "home";
		return new ModelAndView(new RedirectView(targetURL));
		
	}
 
}
