package org.spring.tutorial.actions.common;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class GWTHomeController extends SimpleFormController {

	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response)
	  throws ServletException, IOException {
	    
	    HttpSession session = request.getSession();
	    session.setAttribute( "sessionValue", "Session Value: " + new Date() );
	     
	    return new ModelAndView("/common/gwt_home");
	  }

}
