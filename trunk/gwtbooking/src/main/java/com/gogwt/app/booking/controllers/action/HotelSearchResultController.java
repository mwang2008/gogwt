package com.gogwt.app.booking.controllers.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.scopeManager.session.SessionBeanLookupService;

/**
 * Display hotels
 * @author WangM
 *
 */
public class HotelSearchResultController extends AbstractController {

	private static Logger logger = Logger.getLogger(HotelSearchResultController.class);
	
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response)
	  throws ServletException, IOException {
		logger.debug("handleRequestInternal");
		
		HotelSearchResponseBean hotelSearchResponse = SessionBeanLookupService.getReservationSessionManager().getHotelSearchResponse();
		final ModelMap modelMap = new ModelMap();
		
		modelMap.addAttribute( "searchResponse", hotelSearchResponse );
		modelMap.addAttribute( "searchRequestForm", SessionBeanLookupService.getReservationSessionManager().getSearchFormBean() );
		
	    return new ModelAndView("/reservation/search_result").addAllObjects(modelMap);
	    
	 }

}
