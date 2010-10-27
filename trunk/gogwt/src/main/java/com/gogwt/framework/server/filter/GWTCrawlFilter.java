/*
 * Copyright 2010 GoGWT.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gogwt.framework.server.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.gogwt.framework.arch.utils.StringUtils;
import com.gogwt.framework.server.seo.CrawlConfigData;
import com.gogwt.framework.server.seo.SEOManager;
import com.gogwt.framework.server.utils.RequestUtils;
import com.gogwt.framework.server.utils.ServletUtils;

/**
 * <code><B>GWTCrawlFilter<code><B>
 * The filter class proposed by Google for AJAX application 
 * <pre>
 * "If you're running an AJAX application with content that you'd like to appear in search results, we have a new process that, when implemented, can help Google (and potentially other search engines) crawl and index your content."
 * </pre>
 * 
 * See following URL for details: 
 * http://code.google.com/web/ajaxcrawling/
 * 
 * It needs to add this filter to web.xml in order to execute, following is the default setting. 
 * See HtmlUnit reference for timedout, waitForBackgroundJavaScript and waitForBackgroundJavaScriptStartingBefore.
 * 
 *<pre>
    <filter>
		<filter-name>GWTCrawlFilter</filter-name>
		<filter-class>com.gogwt.framework.server.filter.GWTCrawlFilter</filter-class>
		<init-param>
			<param-name>timedoutMillis</param-name>
			<param-value>6000</param-value>
		</init-param>
		<init-param>
         <param-name>waitForBackgroundJavaScriptMillis</param-name>
         <param-value>5000</param-value>
      </init-param>
      <init-param>
         <param-name>waitForBackgroundJavaScriptStartingBeforeMillis</param-name>
         <param-value>4000</param-value>
      </init-param>
	</filter>
	
	<filter-mapping>
		<filter-name>GWTCrawlFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
 *</pre>
 *
 * Note: if scheme, domain and port are different from the one from request, 
 * (some apache setting may change the those values).
 * extends this filter and overrite getSchemeDomainPort method. 
 * <p/>
 */
public class GWTCrawlFilter implements Filter {
	private static final String WAIT_FOR_BACKGROUND_JAVASCRIPT_MILLIS = "waitForBackgroundJavaScriptMillis";
	private static final String WAIT_FOR_BACKGROUND_JAVA_SCRIPT_STARTING_BEFORE_MILLIS = "waitForBackgroundJavaScriptStartingBeforeMillis";
	
	private static final String TIMED_OUT_MILLIS = "timedoutMillis";
	private static final String G_CRAWL_KEYWORD = "_escaped_fragment_";

	private static Logger logger = Logger.getLogger(GWTCrawlFilter.class);

	private CrawlConfigData crawlConfigData;

	public void init(FilterConfig filterConfig) throws ServletException {
		crawlConfigData = new CrawlConfigData();

		if (isValidInteger(filterConfig.getInitParameter(TIMED_OUT_MILLIS))) {
			crawlConfigData.setTimedout(Integer.valueOf(filterConfig
					.getInitParameter(TIMED_OUT_MILLIS)));
		}
		if (isValidInteger(filterConfig
				.getInitParameter(WAIT_FOR_BACKGROUND_JAVASCRIPT_MILLIS))) {
			crawlConfigData
					.setWaitForBackgroundJavaScript(Integer.valueOf(filterConfig
							.getInitParameter(WAIT_FOR_BACKGROUND_JAVASCRIPT_MILLIS)));
		}
		
		if (isValidInteger(filterConfig
				.getInitParameter(WAIT_FOR_BACKGROUND_JAVA_SCRIPT_STARTING_BEFORE_MILLIS))) {
			crawlConfigData
					.setWaitForBackgroundJavaScriptStartingBefore(Integer.valueOf(filterConfig
							.getInitParameter(WAIT_FOR_BACKGROUND_JAVA_SCRIPT_STARTING_BEFORE_MILLIS)));
		}
	}

	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String queryString = req.getQueryString();

		if (queryString != null && queryString.contains(G_CRAWL_KEYWORD)) {
			
			final String schemeDomainPort = getSchemeDomainPort(req);
			String seoURL = schemeDomainPort + rewriteQueryString(req, queryString);
			try {
				String html = null;
				
				//get from cache
				html = getFromCache(seoURL);
				
				// generate html
				if (!StringUtils.isSet(html)) {
				   html = SEOManager.getInstance().processRequest(req,
						seoURL, crawlConfigData);
				}
				
				// send result back
				res.setContentType( "text/html;charset=UTF-8" );
			    PrintWriter out = res.getWriter();
			    
			    if (StringUtils.isSet(html)) {
			    	setToCache(seoURL, html);
			    }
			    
			    out.println( html );
			    out.close();
			    
			} catch (Exception e) {
				logger.error("could not parser " + e.getMessage(), e);
			}
		} else {
			try {
				// not an _escaped_fragment_ URL, so move up the chain of
				// servlet (filters)
				chain.doFilter(request, response);
			} catch (ServletException e) {
				logger.error("error doing SEL filter " + e.getMessage(), e);
			}
		}
	}

	/**
	 * Get result from cache.
	 * @param seoURL
	 * @return
	 */
	protected String getFromCache(final String seoURL) {
		return null;
	}
	
	/**
	 * Sub class needs to implement it. 
	 * @param seoURL
	 * @param html
	 */
	protected void setToCache(final String seoURL, final String html) {
		
	}
	
	/**
	 * Return first part of request url.
	 * 
	 * If scheme, domain and port are not standard way to get. 
	 * Subclass method may overrite it to provide  
	 * 
	 * http://www.domain.com:port
	 * https://www.domain.com:port
	 * 
	 * @param req
	 * @return
	 */
    protected String getSchemeDomainPort(HttpServletRequest req) {
    	StringBuilder sBuilder = new StringBuilder();
    	
    	sBuilder.append(req.getScheme());
    	sBuilder.append("://");
    	sBuilder.append(RequestUtils.getRequestHostName(req));
    	
    	int port = req.getServerPort();
    	
    	if (!(port == RequestUtils.HTTPS_PORT || port == RequestUtils.HTTP_PORT)) {    	 
    		sBuilder.append(":");
    		sBuilder.append(port);    		 
    	}
    	
    	return sBuilder.toString();
    }
    
    
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * rewriteQueryString rewrite the URL back to the original #! version
	 * unescape any %XX characters
	 * 
	 * @param req
	 * @param queryString
	 * @return
	 */
	private static String rewriteQueryString(HttpServletRequest req,
			String queryString) {
		String uri = req.getRequestURI();

		// get token if any for _escaped_fragment_
		String token = req.getParameter(G_CRAWL_KEYWORD);

		// remove _escaped_fragment_ and its value
		queryString = ServletUtils
				.removeParameter(queryString, G_CRAWL_KEYWORD);

		if (StringUtils.isSet(queryString)) {
			queryString = ServletUtils.encode(queryString);
		}

		StringBuilder seoURL = new StringBuilder(uri);
		if (StringUtils.isSet(queryString)) {
		   seoURL.append("?");
		   seoURL.append(queryString);
		}

		if (StringUtils.isSet(token)) {
			seoURL.append("#!");
			seoURL.append(token);
		}

		return seoURL.toString();

	}

	private static boolean isValidInteger(final String intStr) {
		if (intStr == null) {
			return false;
		}
		try {
			Integer.valueOf(intStr);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}
}
