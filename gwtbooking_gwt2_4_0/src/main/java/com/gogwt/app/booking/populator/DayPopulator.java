package com.gogwt.app.booking.populator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;

public class DayPopulator implements Populator {

	public List<PopulatorItem> getPopulator(HttpServletRequest request) {
		List<PopulatorItem> populatorList = new ArrayList<PopulatorItem>();
		
		for (int i=1 ; i< 32; i++) {
			populatorList.add(new PopulatorItem(String.valueOf(i),String.valueOf(i)));
		}
		
		return populatorList;
	}

	public List<PopulatorItem> getPopulator(UserContextBean userContext) {
	    List<PopulatorItem> populatorList = new ArrayList<PopulatorItem>();
		
		for (int i=0 ; i< 32; i++) {
			populatorList.add(new PopulatorItem(String.valueOf(i),String.valueOf(i)));
		}
		
		return populatorList;
	}

}
