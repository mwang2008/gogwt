package com.gogwt.app.booking.populator;

import static com.gogwt.app.booking.BookingConstants.ENV;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gogwt.app.booking.businessService.domainService.LookupBusinessService;
import com.gogwt.app.booking.config.urlmapping.UrlMappingElem;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;

/**
 * Get state list
 * 
 * @author WangM
 *
 */
public class StatePopulator implements BasePopulator {

	 
	public List getPopulator(HttpServletRequest request) {
	
		//todo: add cache later
		
		UrlMappingElem urlMappingElem = (UrlMappingElem)request.getAttribute(ENV);
		
		UserContextBean userContext = new UserContextBean();
		userContext.setCountryId(urlMappingElem.getCountryId());
		userContext.setLanguageId(urlMappingElem.getLanguageId());
		
		return LookupBusinessService.getCommonBusinessService().getStateList(userContext);
 	}

}
