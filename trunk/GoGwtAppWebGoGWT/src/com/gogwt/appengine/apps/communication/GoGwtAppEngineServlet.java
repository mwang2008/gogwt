package com.gogwt.appengine.apps.communication;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GoGwtAppEngineServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		
		List<String> to = new ArrayList<String>();
		to.add("mwang_2008@yahoo.com");
		to.add("michael.wang@cypresscare.com");
		
		String subject = " test app engine email";
	    String body = "test";
	    
		boolean success = SendEmailUtils.gogwtNotificationEmail(to, subject, body);
		
		if (success) {
			resp.getWriter().println("Success sending email");
		}
		else {
			resp.getWriter().println("Fail to send email");
		}
		resp.getWriter().println("Hello, world");
	}
}
