package com.gogwt.apps.tracking.config.interceptor;

import static com.gogwt.apps.tracking.AppConstants.CUSTOMER_PROFILE;
import static com.gogwt.apps.tracking.AppConstants.ENV;
import static com.gogwt.apps.tracking.AppConstants.FORWARD_SLASH;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.LoginStatus;
import com.gogwt.apps.tracking.utils.CookieUtils;

/**
 * URLHandlerInterceptor is executed before controller
 * 
 * @author michael.wang
 * 
 */
public class URLHandlerInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = Logger
			.getLogger(URLHandlerInterceptor.class);

	public boolean preHandle(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler)
			throws Exception {

		try {

			// basic element
			final UrlMappingElem urlMappingElem = fillRequestAttributes(
					request, response, handler);

			HttpSession session = request.getSession();
			CustomerProfile customerProfile = (CustomerProfile)session.getAttribute(CUSTOMER_PROFILE);
			urlMappingElem.setCustomerProfile(customerProfile);
			
			// save urlMappingElem to request
			request.setAttribute(ENV, urlMappingElem);

			if (customerProfile == null) {
			   CustomerProfile profile = CookieUtils.getCookieProfile(request);
			   if (profile != null) {
				  profile.setStatus(LoginStatus.IMPLICIT);
				  session.setAttribute(CUSTOMER_PROFILE, profile);
			   }
			}
			
			return true;
		} catch (Exception e) {
			// todo: has error, redirect to default page
			e.printStackTrace();
			logger.info(" forward to default page");
			response.sendRedirect("tracking/en-us/home");
		}
		
		return false;
	}

	private UrlMappingElem fillRequestAttributes(
			final HttpServletRequest request,
			final HttpServletResponse response, final Object handler)
			throws Exception {
		UrlMappingElem urlMappingElem = new UrlMappingElem();

		// fill language/country
		fillLanguageRegion(request, urlMappingElem);

		return urlMappingElem;

	}

	private void fillLanguageRegion(final HttpServletRequest request,
			UrlMappingElem urlMappingElem) throws Exception {
		
		String uri = request.getRequestURI();
		urlMappingElem.setUri(uri);
		
		urlMappingElem.setContextPath(request.getContextPath());
		
		urlMappingElem.setPrefix(request.getContextPath() + request.getServletPath());
		
		try {
			String servletPath = request.getServletPath(); // /en-US
			servletPath = servletPath.substring(1);

			final StringTokenizer tokenizer = new StringTokenizer(servletPath,
					FORWARD_SLASH);

			// 1. get language-Region
			String langRegion = tokenizer.nextToken();
			String[] langRegionArray = langRegion.split("-");
			urlMappingElem.setLanguageId(langRegionArray[0]);
			urlMappingElem.setCountryId(langRegionArray[1]);

		} catch (final NoSuchElementException ex) {
			logger.info("Could not set url mapping elements from uri " + uri + ", error msg=" + ex.getMessage());
			urlMappingElem.setLanguageId("en");
			urlMappingElem.setCountryId("us"); 
		}				
	}
}
