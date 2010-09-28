package com.gogwt.app.booking.controllers.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.gogwt.app.booking.controllers.BaseAbstractController;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.scopeManager.session.SessionBeanLookupService;

/**
 * Display hotels
 * @author WangM
 *
 */
public class HotelSearchResultController extends BaseAbstractController {

	private static Logger logger = Logger.getLogger(HotelSearchResultController.class);
	
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response)
	  throws ServletException, IOException {
		logger.debug("handleRequestInternal");
		
		HotelSearchResponseBean hotelSearchResponse = SessionBeanLookupService.getReservationSessionManager().getReservationContainerBean().getHotelSearchResponse();
		final ModelMap modelMap = new ModelMap();
		
		modelMap.addAttribute( "searchResponse", hotelSearchResponse );
		modelMap.addAttribute( "searchRequestForm", SessionBeanLookupService.getReservationSessionManager().getReservationContainerBean().getHotelSearchRequest() );
		
	    return new ModelAndView("/reservation/search_result").addAllObjects(modelMap);
	    
	 }

}
