package com.gogwt.apps.tracking.controllers;

import static com.gogwt.apps.tracking.AppConstants.CUSTOMER_PROFILE;
import static com.gogwt.apps.tracking.AppConstants.ERROR_MSG;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.TrackingMobileData;
import com.gogwt.apps.tracking.services.domain.LookupBusinessService;
import com.gogwt.apps.tracking.services.domain.RestBusinessDomainService;
import com.gogwt.apps.tracking.utils.StringUtils;

public class TrackDeleteController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(TrackDeleteController.class);

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		String groupId = request.getParameter("groupId");
		String displayName = request.getParameter("displayName");
		String startTime = request.getParameter("startTime");
		
		if (!StringUtils.isSet(groupId) || !StringUtils.isSet(displayName) || !StringUtils.isSet(startTime)) {
			//redirect to error page
			session.setAttribute(ERROR_MSG, "Missing parameters: groupId, displayName or startTime");
			return new ModelAndView(new RedirectView("error"));
		}
		
		long startTiemLong = Long.parseLong(startTime);
		
		CustomerProfile customerProfile = (CustomerProfile)session.getAttribute(CUSTOMER_PROFILE);
		
		logger.debug("groupId="+groupId+" ,displayName="+displayName + ", startTime="+startTime);
		final RestBusinessDomainService service =  LookupBusinessService.getRestBusinessDomainService();
		service.deleteTrack(customerProfile.getUserName(), groupId, displayName, startTiemLong);
		
		String successView = getSuccessView();
		return new ModelAndView(new RedirectView(successView));
		
	}

}
