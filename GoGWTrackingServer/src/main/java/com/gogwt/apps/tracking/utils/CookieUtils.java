package com.gogwt.apps.tracking.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gogwt.apps.tracking.data.CustomerProfile;

public class CookieUtils {
	public static final int SECONDS_PER_MONTH = 60*60*24*30;
	public static final int SECONDS_PER_YEAR = 60*60*24*365;
	public static final String DOMAIN_NAME = ".gogwt.com";
	private static final String PROFILE_COOKIE_NAME = "profile";
	public static final String HOST_HEADER = "HOST";
	
	public static String getCookieDomain( final HttpServletRequest request ) {
		String hostName = request.getHeader( HOST_HEADER );
		
		return hostName;
	}
	
	public static String getCookieValue(Cookie[] cookies, String cookieName) {
		if (cookies == null || cookies.length ==0) {
			return null;
		}
		
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookieName.equals(cookie.getName()))
				return (cookie.getValue());
		}
		return null;
	}
	
	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie[] cookies=request.getCookies();
		return getCookieValue(cookies, cookieName);
	}
	
	public static Cookie createCookie(final String cookieDomain, String cookieName, String cookieValue, int maxAge)
    {		 
        return createCookie(cookieDomain, cookieName, cookieValue, maxAge);
    }
	
	public static Cookie createCookie(final String cookieDomain, String cookieName, String cookieValue, boolean isSecure, int maxAge)
    {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setDomain(cookieDomain);
        //cookie.setPath(CookieUtils.DEFAULT_COOKIE_PATH);
        cookie.setSecure(isSecure);
        cookie.setMaxAge(maxAge);
        return cookie;
    }
	
	public static void setCookie(HttpServletResponse response, Cookie cookie) {
		 response.addCookie(cookie);
	}
	
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int maxAge) {
		 Cookie cookie = new Cookie(name, value);
		 
		 cookie.setMaxAge(maxAge);
		 cookie.setDomain(getCookieDomain(request));
		 
		 response.addCookie(cookie);
	}
	
	/**
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 * @deprecated user other setCookie method instead
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
		 Cookie cookie = new Cookie(name, value);
		 
		 cookie.setMaxAge(maxAge);
		 cookie.setDomain(DOMAIN_NAME);
		 
		 response.addCookie(cookie);
	}
	
	public static void setProfileCookie(HttpServletRequest request, HttpServletResponse response, CustomerProfile customerProfile) {
		String cookieValue = customerProfile.getGroupId()+"|"+customerProfile.getUserName()+"|"+customerProfile.getFirstName()+"|"+customerProfile.getLastName();
		
		setCookie(request, response, PROFILE_COOKIE_NAME, cookieValue, SECONDS_PER_MONTH);
	}
	
	public static CustomerProfile getCookieProfile(HttpServletRequest request) {
		String cookieValue = getCookieValue(request, PROFILE_COOKIE_NAME);
		if (cookieValue == null) {
			return null;
		}
		
		String [] cookieValues = cookieValue.split("\\|");
		CustomerProfile profile = new CustomerProfile();
		profile.setGroupId(cookieValues[0]);
		profile.setUserName(cookieValues[1]);
		profile.setFirstName(cookieValues[2]);
		profile.setLastName(cookieValues[3]);
		
		return profile;
	}
	
}
