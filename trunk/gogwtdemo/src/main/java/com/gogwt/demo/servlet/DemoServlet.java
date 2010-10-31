package com.gogwt.demo.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gogwt.framework.server.permutation.PermutationSelector;
import com.gogwt.framework.server.utils.google.PermutationUtil;

@SuppressWarnings("serial")
public class DemoServlet extends HttpServlet {
	public static final String PERMUTATION_FILE = "WEB-INF/permutation.properties";
	
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
 		
	
		
		//determine permutation setting
		//Map<String, String> permutationMap = findPermutationSelection(request);
		//request.setAttribute(PermutationSelector.REQ_PERMUTATION, permutationMap);
		
	  	
		String nextJSP = "/gogwt_demo.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}

	private Map<String, String> findPermutationSelection(HttpServletRequest request) {
		String userAgent = PermutationUtil.getUserAgent(request);
		Locale defaultLocale = Locale.getDefault();
		String locale = defaultLocale.getLanguage()+ "_" + defaultLocale.getCountry();			
		String logLevel = "off";

		System.out.println("userAgent="+userAgent + ",locale="+locale + ",logLevel="+logLevel);
		
		Map<String, String> permutationMap = new HashMap<String, String>();
		permutationMap.put("locale", locale.toLowerCase());
		permutationMap.put("log_level", logLevel.toLowerCase());
		permutationMap.put("user.agent", userAgent.toLowerCase());
		
		return permutationMap;
	}
}
