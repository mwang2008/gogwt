package com.gogwt.apps.tracking.filter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Filter implementation allowing response headers to be set via init parameters specified in the web.xml
 * @author Michael.Wang
 *
 */
public class CacheHeaderFilter implements Filter {
	private static Logger logger = Logger.getLogger(CacheHeaderFilter.class);
	
	private static final String INIT_PARAMETER_ENABLED = "enabled";
	private static final String INIT_PARAMETER_CACHE_HEADER = "Cache-Control";
	private static final String INIT_PARAMETER_NO_CACHE_HEADER = "No-Cache-Control";
	private static final int TWO = 2;
	private static final int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
	
	private boolean enabled = false;
	private String cacheControl;
	private String noCacheControl;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		setEnabled(Boolean.valueOf(filterConfig.getInitParameter(INIT_PARAMETER_ENABLED)));
		setCacheControl(filterConfig.getInitParameter(INIT_PARAMETER_CACHE_HEADER));
		setNoCacheControl(filterConfig.getInitParameter(INIT_PARAMETER_NO_CACHE_HEADER));
		
		if (logger.isDebugEnabled()) {
			logger.debug("Setting header [enabled]=" + enabled);
			logger.debug("Setting header [" + INIT_PARAMETER_CACHE_HEADER + "] to value [" + getCacheControl() + "]");
		}
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		if (!servletResponse.isCommitted() && isEnabled()) {
			setResponseHeader(servletRequest, servletResponse);
		}
		filterChain.doFilter(servletRequest, servletResponse);
		
	}
	
	/**
	 * setResponseHeader
	 */
	private void setResponseHeader(ServletRequest servletRequest, ServletResponse servletResponse) {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		String path = request.getRequestURI().toLowerCase();
		
		if (path.contains("nocache")) {
			response.setHeader("Pragma", "no-cache");
			response.setHeader(INIT_PARAMETER_CACHE_HEADER, getNoCacheControl());
			response.setHeader("Expires", "-1");
		}
		else {
		    long current = System.currentTimeMillis();
			response.setDateHeader("Last-Modified", current);
			// set Expires header
			String expiry = getHtmlExpiresDateFormat().format(new Date(current + (MILLIS_IN_DAY / TWO)));
			if (expiry != null) {
			   response.setHeader("Expires", expiry);
			}	
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	
	private DateFormat getHtmlExpiresDateFormat() {
		DateFormat httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
		httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return httpDateFormat;
	}	
	


	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getCacheControl() {
		return cacheControl;
	}

	public void setCacheControl(String cacheControl) {
		this.cacheControl = cacheControl;
	}

	public String getNoCacheControl() {
		return noCacheControl;
	}

	public void setNoCacheControl(String noCacheControl) {
		this.noCacheControl = noCacheControl;
	}
 
}
