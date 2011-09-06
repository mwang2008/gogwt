package com.gogwt.apps.tracking.controllers;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

public class HomePageController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(HomePageController.class);

	@Override
	protected ModelAndView handleRequestInternal(
			final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("handleRequestInternal");

		HttpSession session = request.getSession();
		session.setAttribute("sessionValue", "Session Value: " + new Date());

		return new ModelAndView("/tracking/home");
	}
}
