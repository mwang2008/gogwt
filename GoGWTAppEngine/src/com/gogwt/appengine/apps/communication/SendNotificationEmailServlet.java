package com.gogwt.appengine.apps.communication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * http://gogwtengine.appspot.com/notifyemail
 * @author michael.wang
 *
 */
public class SendNotificationEmailServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(SendNotificationEmailServlet.class
			.getName());

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//response.setContentType("text/plain");
		log.finer("= doGet ");
		getServletConfig().getServletContext().getRequestDispatcher(
		"/show_notify_form.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//response.setContentType("text/plain");
		log.finer("= doPost ");
		String to = request.getParameter("to");
		String subject = request.getParameter("subject");
		String body = request.getParameter("body");
		
		List<String> toAddress = new ArrayList<String>();
		toAddress.add(to);
		
		SendEmailUtils.gogwtNotificationEmail(toAddress, subject, body);
		
		getServletConfig().getServletContext().getRequestDispatcher(
		"/show_notify_form.jsp").forward(request, response);
	}
}
