package com.gogwt.app.booking.populator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gogwt.app.booking.businessService.domainService.LookupBusinessService;
import com.gogwt.app.booking.config.urlmapping.UrlMappingElem;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;
import com.gogwt.app.booking.dto.dataObjects.common.StateBean;

import static com.gogwt.app.booking.BookingConstants.*;

/**
 * Get state list
 * 
 * @author WangM
 *
 */
public class StatePopulator implements BasePopulator {

	public List<PopulatorItem> getPopulator(final HttpServletRequest request) {
	    UrlMappingElem urlMappingElem = (UrlMappingElem)request.getAttribute(ENV);
		
		UserContextBean userContext = new UserContextBean();
		userContext.setCountryId(urlMappingElem.getCountryId());
		userContext.setLanguageId(urlMappingElem.getLanguageId());
		
		List<StateBean> stateList = LookupBusinessService.getCommonBusinessService().getStateList(userContext);
		
		List<PopulatorItem> populatorList = new ArrayList<PopulatorItem>();
		PopulatorItem populatorItem = null;
		for (StateBean state : stateList) {
			populatorItem = new PopulatorItem();
			populatorItem.setCode(state.getStateId());
			populatorItem.setDisplay(state.getStateName());
			
			populatorList.add(populatorItem);
		}
		
		return populatorList;
	}
	
	
}
