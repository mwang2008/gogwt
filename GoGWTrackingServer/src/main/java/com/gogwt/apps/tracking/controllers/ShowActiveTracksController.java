package com.gogwt.apps.tracking.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author michael.wang
 *
 */
public class ShowActiveTracksController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(ShowActiveTracksController.class);

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("/tracking/show_active_tracking");				
	}
	
 
	/*
	protected ModelAndView handleRequestInternal(
			final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("handleRequestInternal");
		HttpSession session = request.getSession();
		
		CustomerProfile customerProfile = (CustomerProfile)session.getAttribute(CUSTOMER_PROFILE);
		
		Map<String, List<GLocation>> fullLocationMap = ActiveSharedLocation.getLocationMap(customerProfile.getGroupId());
		Map<String, Profile> profileMap = ActiveSharedLocation.getProfileMap(customerProfile.getGroupId());
		
		final ModelMap modelMap = new ModelMap();
		if (fullLocationMap == null || fullLocationMap.isEmpty()) {
			modelMap.addAttribute( "hasResult", false );
		}
		else {
			modelMap.addAttribute( "hasResult", true );
			modelMap.addAttribute( "profileMap", profileMap );
			modelMap.addAttribute( "locationMap", fullLocationMap );
			
			
		 
		}
		
		return new ModelAndView("/tracking/show_active_tracking").addAllObjects(modelMap);
		
	}
	*/

}
