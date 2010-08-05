package org.spring.tutorial.config.interceptor;

import static org.spring.tutorial.AppConstants.ENV_NAME;
import static org.spring.tutorial.AppConstants.FORWARD_SLASH;
import static org.spring.tutorial.AppConstants.MIN_URL_TOKENS;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.spring.tutorial.config.urlmapping.URLMappingElement;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class URLHandlerInterceptor implements HandlerInterceptor {
 	
	/**
	 * 
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj)
			throws Exception {
		
		URLMappingElement appMappingElem = new URLMappingElement();
		fillBasicURLMappingElem(appMappingElem, request);
		
		request.setAttribute( ENV_NAME, appMappingElem );
		
		return true;
	}
	
	public void afterCompletion(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse, Object obj,
			Exception exception) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void postHandle(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse, Object obj,
			ModelAndView modelandview) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * http://localhost/web-tutorial1/myapp/en/us/gwthome
	 * @param appMappingElem
	 * @param request
	 */
   private void fillBasicURLMappingElem(URLMappingElement appMappingElem, HttpServletRequest request) {
	   String uri = request.getRequestURI(); //  /web-tutorial1/myapp/en/us/gwthome
	    try {
	      final StringTokenizer tokenizer = new StringTokenizer( uri, FORWARD_SLASH );
	      if ( tokenizer.countTokens() >= MIN_URL_TOKENS ) {
	    	  tokenizer.nextToken(); // right now, it is war file name
	    	   
	    	  final String languageId = tokenizer.nextToken();
	          final String countryId  = tokenizer.nextToken();
	 
	          appMappingElem.setLanguageId( languageId );
	          appMappingElem.setCountryId( countryId );
	          
	      }
	      
	    }
	    catch (Throwable e) {
	    	
	    }
	    
	    appMappingElem.setContextPath(request.getContextPath());
	    appMappingElem.setServletPath(request.getServletPath());
	    
   }
}
