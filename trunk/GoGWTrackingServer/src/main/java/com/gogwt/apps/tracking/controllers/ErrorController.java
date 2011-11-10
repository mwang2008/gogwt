package com.gogwt.apps.tracking.controllers;

import static com.gogwt.apps.tracking.AppConstants.ERROR_MSG;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

public class ErrorController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(ErrorController.class);

	@Override
	protected ModelAndView handleRequestInternal(
			final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("handleRequestInternal");

		HttpSession session = request.getSession();
		String errorMsg = (String)session.getAttribute(ERROR_MSG);
		
		final ModelMap modelMap = new ModelMap();
		modelMap.addAttribute(ERROR_MSG, errorMsg);
		
		session.removeAttribute(ERROR_MSG);
		
		return new ModelAndView("/tracking/error").addAllObjects(modelMap);
	}

}
