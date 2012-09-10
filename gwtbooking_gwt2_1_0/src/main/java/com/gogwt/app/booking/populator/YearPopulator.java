package com.gogwt.app.booking.populator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;

public class YearPopulator implements Populator {

	public List<PopulatorItem> getPopulator(HttpServletRequest request) {
        List<PopulatorItem> populatorList = new ArrayList<PopulatorItem>();
		
        java.util.Calendar calendar = java.util.Calendar.getInstance(); 
        int currentYear = calendar.get(java.util.Calendar.YEAR); 
        
		for (int i=currentYear-1 ; i>1901; i--) {
			populatorList.add(new PopulatorItem(String.valueOf(i),String.valueOf(i)));
		}
		
		return populatorList;
	}

	public List<PopulatorItem> getPopulator(UserContextBean userContext) {
        List<PopulatorItem> populatorList = new ArrayList<PopulatorItem>();
		
        java.util.Calendar calendar = java.util.Calendar.getInstance(); 
        int currentYear = calendar.get(java.util.Calendar.YEAR); 
        
        for (int i=currentYear-1 ; i>1901; i--) {
			populatorList.add(new PopulatorItem(String.valueOf(i),String.valueOf(i)));
		}
		
		return populatorList;
	}

}
