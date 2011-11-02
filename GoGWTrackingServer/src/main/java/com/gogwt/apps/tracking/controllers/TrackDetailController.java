package com.gogwt.apps.tracking.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

public class TrackDetailController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(ShowActiveTracksController.class);

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String groupId = request.getParameter("groupId");
		String displayName = request.getParameter("displayName");
		String startTime = request.getParameter("startTime");
		
		
		System.out.println("groupId="+groupId+" ,displayName="+displayName + ", startTime="+startTime);
		
		return new ModelAndView("/tracking/show_detail_tracking");				
	}
}
