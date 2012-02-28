package com.gogwt.apps.tracking.controllers;

import static com.gogwt.apps.tracking.AppConstants.CUSTOMER_PROFILE;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.gogwt.apps.tracking.exceptions.AppRemoteException;
import com.gogwt.apps.tracking.services.domain.LookupBusinessService;
import com.gogwt.apps.tracking.services.domain.ProfileBusinessDomainService;
import com.gogwt.apps.tracking.utils.CookieUtils;

public class DeleteAccountController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(HomePageController.class);

	@Override
	protected ModelAndView handleRequestInternal(
			final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("handleRequestInternal");

		
		//delete the account 
		String groupId = request.getParameter("groupId");
		String username = request.getParameter("userName");
		
		ProfileBusinessDomainService businessService = LookupBusinessService.getProfileBusinessDomainService();
		
		try {
		   businessService.deleteAccountByGroupIdNuserName(groupId, username);
		   
		   HttpSession session = request.getSession();
		   session.removeAttribute(CUSTOMER_PROFILE);

		   //remove cookie
		   CookieUtils.removeCookie(request,response, CookieUtils.PROFILE_COOKIE_NAME );

		}
		catch (AppRemoteException e) {
		   logger.error(e.getMessage());
		   e.printStackTrace();
		}
				 
		String targetURL = "home";
		return new ModelAndView(new RedirectView(targetURL));
	}

}
