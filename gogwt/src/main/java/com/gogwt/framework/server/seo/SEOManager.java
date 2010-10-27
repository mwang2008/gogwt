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

package com.gogwt.framework.server.seo;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gogwt.framework.server.utils.RequestUtils;

public final class SEOManager {
	private static Logger logger = Logger.getLogger(SEOManager.class);
	
    private static SEOManager instance;
    
    public String processRequest(final HttpServletRequest request, 
    		  final String paramSeoUrl, final CrawlConfigData crawlConfigData) {
    	
	    if (logger.isDebugEnabled()) {
			   logger.debug(debugInfo(request, paramSeoUrl));
		}
    	
    	long start = System.currentTimeMillis();
		String html = "";
		WebClient webClient = null;
		HtmlPage htmlPage  = null;
    	
		try {
			webClient = new WebClient( BrowserVersion.FIREFOX_3 );
			webClient.setThrowExceptionOnFailingStatusCode(false); // since some pages are giving 503
			webClient.setThrowExceptionOnScriptError(false); // since htmlunit detect some errors in the javascripts
			webClient.setAjaxController(new NicelyResynchronizingAjaxController()); // converts async calls to synchronous
			webClient.setCssEnabled( false );
			webClient.getCookieManager().setCookiesEnabled( true );
			
		    if (crawlConfigData.getTimedout() != 0) {
			 	webClient.setTimeout( crawlConfigData.getTimedout() );
		    }
	  
		    webClient.addRequestHeader("seoRequest", "true");
		    
		 	htmlPage = (HtmlPage)webClient.getPage( paramSeoUrl );

			// wait FEW seconds in order to make sure all javascript threads complete execution
			if (crawlConfigData.getWaitForBackgroundJavaScript() != 0) {
			   webClient.waitForBackgroundJavaScript(crawlConfigData.getWaitForBackgroundJavaScript());
			}
			
			if (crawlConfigData.getWaitForBackgroundJavaScriptStartingBefore() != 0) {
			   webClient.waitForBackgroundJavaScriptStartingBefore(crawlConfigData.getWaitForBackgroundJavaScriptStartingBefore());
			}
			
			html = htmlPage.asXml();
			
			 
		} catch (Exception e) {
			logger.error( "Exception occured when processing page with HTMLUnit for the URL= " + paramSeoUrl, e );
		} finally {
			if (htmlPage != null) {
				htmlPage.cleanUp();
			}
			if (webClient != null) {
				webClient.closeAllWindows();
			}
			htmlPage = null;
			webClient = null;
		}
		long end = System.currentTimeMillis();
		logger.debug( paramSeoUrl + ": took " + ( end - start ) + " milliseconds" );

		return html;
 
    }
    
    public static SEOManager getInstance() {
    	if (instance == null) {
    		instance = new SEOManager();
    	}
    	
    	return instance;
    }
    
    
	/**
     * Debug info: display whether Google, Yahoo, MSN 
     * @param request
     * @param finalSeoUrl
     * @return
     */
    private String debugInfo(final HttpServletRequest request, String finalSeoUrl) {
    	StringBuffer seoDebug = new StringBuffer();
		seoDebug.append("$$$$");
		 
		seoDebug.append(",");
		seoDebug.append(finalSeoUrl);
		
		//ip
		seoDebug.append(",");
		seoDebug.append("ip=").append(RequestUtils.getUserIPAddress(request));
		
		 	
		//user agent User-Agent
		seoDebug.append(",");
		seoDebug.append("useragent=").append(RequestUtils.getUserAgent(request));

		
		return seoDebug.toString();
    }
}
