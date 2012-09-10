package com.gogwt.app.booking.populator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;

public class MonthPopulator implements Populator {
	
	public List<PopulatorItem> getPopulator(final HttpServletRequest request) {
		   // UrlMappingElem urlMappingElem = (UrlMappingElem)request.getAttribute(ENV);
			 	
			List<PopulatorItem> populatorList = new ArrayList<PopulatorItem>();
			
			populatorList.add(new PopulatorItem("1","January"));
			populatorList.add(new PopulatorItem("2","February"));
			populatorList.add(new PopulatorItem("3","March"));
			populatorList.add(new PopulatorItem("4","April"));
			populatorList.add(new PopulatorItem("5","May"));
			populatorList.add(new PopulatorItem("6","June"));
			populatorList.add(new PopulatorItem("7","July"));
			populatorList.add(new PopulatorItem("8","August"));
			populatorList.add(new PopulatorItem("9","September"));
			populatorList.add(new PopulatorItem("10","October"));
			populatorList.add(new PopulatorItem("11","November"));
			populatorList.add(new PopulatorItem("12","December"));
			
			return populatorList;
		}

	public List<PopulatorItem> getPopulator(UserContextBean userContext) {
		List<PopulatorItem> populatorList = new ArrayList<PopulatorItem>();
		
		populatorList.add(new PopulatorItem("1","January"));
		populatorList.add(new PopulatorItem("2","February"));
		populatorList.add(new PopulatorItem("3","March"));
		populatorList.add(new PopulatorItem("4","April"));
		populatorList.add(new PopulatorItem("5","May"));
		populatorList.add(new PopulatorItem("6","June"));
		populatorList.add(new PopulatorItem("7","July"));
		populatorList.add(new PopulatorItem("8","August"));
		populatorList.add(new PopulatorItem("9","September"));
		populatorList.add(new PopulatorItem("10","October"));
		populatorList.add(new PopulatorItem("11","November"));
		populatorList.add(new PopulatorItem("12","December"));
		
		return populatorList;
	}
}
