package com.gogwt.app.booking.populator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gogwt.app.booking.dto.dataObjects.Gender;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;

public class GenderPopulator implements Populator {

	public List<PopulatorItem> getPopulator(HttpServletRequest request) {
		List<PopulatorItem> populatorList = new ArrayList<PopulatorItem>();

		populatorList.add(new PopulatorItem(String.valueOf(Gender.Male.ordinal()), String.valueOf(Gender.Male.toString())));
		populatorList.add(new PopulatorItem(String.valueOf(Gender.Female.ordinal()), String.valueOf(Gender.Female.toString())));

		return populatorList;
	}

	public List<PopulatorItem> getPopulator(UserContextBean userContext) {
		List<PopulatorItem> populatorList = new ArrayList<PopulatorItem>();

		populatorList.add(new PopulatorItem(String.valueOf(Gender.Male.ordinal()), String.valueOf(Gender.Male.toString())));
		populatorList.add(new PopulatorItem(String.valueOf(Gender.Female.ordinal()), String.valueOf(Gender.Female.toString())));

		return populatorList;
	}

}
