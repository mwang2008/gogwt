package com.gogwt.app.booking.populator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;

public class TitlePopulator implements Populator {

	public List<PopulatorItem> getPopulator(final HttpServletRequest request) {
	   // UrlMappingElem urlMappingElem = (UrlMappingElem)request.getAttribute(ENV);
		 	
		List<PopulatorItem> populatorList = new ArrayList<PopulatorItem>();
		
		populatorList.add(new PopulatorItem("Mr","Mr"));
		populatorList.add(new PopulatorItem("Ms","Ms"));
		populatorList.add(new PopulatorItem("Dr","Dr"));
		populatorList.add(new PopulatorItem("Miss","Miss"));
				
		return populatorList;
	}

	public List<PopulatorItem> getPopulator(UserContextBean userContext) {
		List<PopulatorItem> populatorList = new ArrayList<PopulatorItem>();
		
		populatorList.add(new PopulatorItem("Mr","Mr"));
		populatorList.add(new PopulatorItem("Ms","Ms"));
		populatorList.add(new PopulatorItem("Dr","Dr"));
		populatorList.add(new PopulatorItem("Miss","Miss"));
				
		return populatorList;
	}

}
