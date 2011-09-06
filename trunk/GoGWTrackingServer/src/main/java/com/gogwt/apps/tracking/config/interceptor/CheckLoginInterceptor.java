package com.gogwt.apps.tracking.config.interceptor;

import static com.gogwt.apps.tracking.AppConstants.CUSTOMER_PROFILE;
import static com.gogwt.apps.tracking.AppConstants.ENV;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.utils.StringUtils;

public class CheckLoginInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = Logger.getLogger(CheckLoginInterceptor.class);

	public boolean preHandle(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler)
			throws Exception {

		try {
			HttpSession session = request.getSession();
			
			CustomerProfile customerProfile = (CustomerProfile)session.getAttribute(CUSTOMER_PROFILE);
			 
			UrlMappingElem urlMappingElem = (UrlMappingElem)request.getAttribute(ENV);
			
			if (customerProfile == null || !StringUtils.isSet(customerProfile.getGroupId())) {
				//return back to login page
			 
				String context = request.getContextPath();
				response.sendRedirect(context + "/en-us/login?successURL="+urlMappingElem.getUri());
				
				return false;
			}
			
			 
			 
		} catch (Exception e) {
			// todo: has error, redirect to default page
			e.printStackTrace();
			logger.info(" forward to default page");
			response.sendRedirect("tracking/en-us/home");
		}
		
		return true;
	}

}
