package com.gogwt.app.booking.config.interceptor;

import static com.gogwt.app.booking.BookingConstants.ENV;
import static com.gogwt.app.booking.BookingConstants.FORWARD_SLASH;
import static com.gogwt.app.booking.BookingConstants.*;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gogwt.app.booking.config.urlmapping.UrlMappingElem;

 
/**
 * URLHandlerInterceptor is executed before controller
 * @author WangM
 *
 */
public class URLHandlerInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger logger = Logger.getLogger(URLHandlerInterceptor.class);
	private Map<String, String> supportedLangRegion;
	
	/**
	 * Executed every request before handler.
	 *
	 * @param httpServletRequest  The servlet request we are processing
	 * @param httpServletResponse The servlet response we are creating
	 * @return boolean TRUE = continue processing the request FALE = response has
	 *         already been completed
	 */
	public boolean preHandle(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler)
	throws Exception {
		
		try {
		  final UrlMappingElem urlMappingElem = fillRequestAttributes(request);
		  
		  
		  
		  //save urlMappingElem to request
		  request.setAttribute(ENV, urlMappingElem);
		  request.setAttribute(SUPPORTED_LANG_REGIONS, getSupportedLangRegion());
		  
		  //convert to JSON and save to request with name envJson
		  JSONObject jsonEnv = JSONObject.fromObject(urlMappingElem);
		  request.setAttribute(ENV_JSON, jsonEnv);
		  
		  return true;
		}
		catch (Exception e) {
			//has error, redirect to default page
			e.printStackTrace();
			
		}
		return false;
	}
	
	private UrlMappingElem fillRequestAttributes(final HttpServletRequest request) throws Exception {
		UrlMappingElem urlMappingElem = new UrlMappingElem();
		
		String uri = request.getRequestURI();
		urlMappingElem.setUri(uri);
		urlMappingElem.setContextPath(request.getContextPath());
		urlMappingElem.setPrefix(request.getContextPath()+request.getServletPath());
		String domainName = request.getRemoteHost();
		boolean isSecure = request.isSecure();
		
		try {
			String servletPath = request.getServletPath(); //   /en-US
			servletPath = servletPath.substring(1);
			
			final StringTokenizer tokenizer = new StringTokenizer(servletPath, FORWARD_SLASH);
			
		    //1. get language-Region
		    String langRegion = tokenizer.nextToken();
		    String [] langRegionArray = langRegion.split("-");
		    urlMappingElem.setLanguageId(langRegionArray[0]);
		    urlMappingElem.setCountryId(langRegionArray[1]);  
		    
		}
		catch (final NoSuchElementException ex) {
			logger.info("Could not set url mapping elements from uri " + uri);
			throw new Exception("Could not set url mapping elements from uri " + uri);
		}
		
		
		return urlMappingElem;
		
	}

	public Map<String, String> getSupportedLangRegion() {
		return supportedLangRegion;
	}

	public void setSupportedLangRegion(Map<String, String> supportedLangRegion) {
		this.supportedLangRegion = supportedLangRegion;
	}
	
	
}
