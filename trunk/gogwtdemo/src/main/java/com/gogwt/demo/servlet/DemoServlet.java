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
 	
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
 	  	
		String nextJSP = "/gogwt_demo.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}

	 
}
