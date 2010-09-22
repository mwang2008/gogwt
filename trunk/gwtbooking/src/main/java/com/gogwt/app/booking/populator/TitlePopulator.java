package com.gogwt.app.booking.populator;

import static com.gogwt.app.booking.BookingConstants.ENV;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gogwt.app.booking.config.urlmapping.UrlMappingElem;
import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;

public class TitlePopulator implements BasePopulator {

	public List<PopulatorItem> getPopulator(final HttpServletRequest request) {
	    UrlMappingElem urlMappingElem = (UrlMappingElem)request.getAttribute(ENV);
		 	
		List<PopulatorItem> populatorList = new ArrayList<PopulatorItem>();
		
		populatorList.add(new PopulatorItem("Mr","Mr"));
		populatorList.add(new PopulatorItem("Ms","Ms"));
		populatorList.add(new PopulatorItem("Dr","Dr"));
		populatorList.add(new PopulatorItem("Miss","Miss"));
				
		return populatorList;
	}

}
