package com.gogwt.app.booking.gwt.common.populator;

import java.util.ArrayList;
import java.util.HashMap;

import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;



/**
 * 
 * @author WangM
 *
 */
public class PopulatorDataCollection  {
	
	
	
	
	//<entry key="statesProvincesPopulator">com.gogwt.app.booking.populator.StatePopulator</entry>
	public HashMap <String, ArrayList<PopulatorItem>> getPopulatorDataCollection() {
		HashMap <String, ArrayList<PopulatorItem>> populator;
		
		populator = new HashMap <String, ArrayList<PopulatorItem>>();
		populator.put("statesProvincesPopulator", getStatePopulator());
		populator.put("titlePopulator", getTitlePopulator());
		
		return populator;
	}
	
	public ArrayList<PopulatorItem> getTitlePopulator() {
		ArrayList<PopulatorItem> titleList = new ArrayList<PopulatorItem>();
		titleList.add(new PopulatorItem("Mr", "Mr"));
		titleList.add(new PopulatorItem("Ms", "Ms"));
		titleList.add(new PopulatorItem("Dr", "Dr"));
		titleList.add(new PopulatorItem("Miss", "Miss"));

        return titleList;
	}
	
	public ArrayList<PopulatorItem> getStatePopulator() {
		ArrayList<PopulatorItem> stateList = new ArrayList<PopulatorItem>();
		stateList.add(new PopulatorItem("AK", "ALASKA"));
		stateList.add(new PopulatorItem("AL", "ALABAMA"));
		stateList.add(new PopulatorItem("AR", "ARKANSAS"));
		stateList.add(new PopulatorItem("AZ", "ARIZONA"));
		stateList.add(new PopulatorItem("CA", "CALIFORNIA"));
		stateList.add(new PopulatorItem("CO", "COLORADO"));
		stateList.add(new PopulatorItem("CT", "CONNECTICUT"));
		stateList.add(new PopulatorItem("DC", "DISTRICT OF COLUMBIA"));
		stateList.add(new PopulatorItem("DE", "DELAWARE"));
		stateList.add(new PopulatorItem("FL", "FLORIDA"));
		stateList.add(new PopulatorItem("GA", "GEORGIA"));
		stateList.add(new PopulatorItem("HI", "HAWAII"));
		stateList.add(new PopulatorItem("IA", "IOWA"));
		stateList.add(new PopulatorItem("ID", "IDAHO"));
		stateList.add(new PopulatorItem("IL", "ILLINOIS"));
		stateList.add(new PopulatorItem("IN", "INDIANA"));
		stateList.add(new PopulatorItem("KS", "KANSAS"));
		stateList.add(new PopulatorItem("KY", "KENTUCKY"));
		stateList.add(new PopulatorItem("LA", "LOUISIANA"));
		stateList.add(new PopulatorItem("MA", "MASSACHUSETTS"));
		stateList.add(new PopulatorItem("MD", "MARYLAND"));
		stateList.add(new PopulatorItem("ME", "MAINE"));
		stateList.add(new PopulatorItem("MI", "MICHIGAN"));
		stateList.add(new PopulatorItem("MN", "MINNESOTA"));
		stateList.add(new PopulatorItem("MO", "MISSOURI"));
		stateList.add(new PopulatorItem("MS", "MISSISSIPPI"));
		stateList.add(new PopulatorItem("MT", "MONTANA"));
		stateList.add(new PopulatorItem("NC", "NORTH CAROLINA"));
		stateList.add(new PopulatorItem("ND", "NORTH DAKOTA"));
		stateList.add(new PopulatorItem("NE", "NEBRASKA"));
		stateList.add(new PopulatorItem("NH", "NEW HAMPSHIRE"));
		stateList.add(new PopulatorItem("NJ", "NEW JERSEY"));
		stateList.add(new PopulatorItem("NM", "NEW MEXICO"));
		stateList.add(new PopulatorItem("NV", "NEVADA"));
		stateList.add(new PopulatorItem("NY", "NEW YORK"));
		stateList.add(new PopulatorItem("OH", "OHIO"));
		stateList.add(new PopulatorItem("OK", "OKLAHOMA"));
		stateList.add(new PopulatorItem("OR", "OREGON"));
		stateList.add(new PopulatorItem("PA", "PENNSYLVANIA"));
		stateList.add(new PopulatorItem("RI", "RHODE ISLAND"));
		stateList.add(new PopulatorItem("SC", "SOUTH CAROLINA"));
		stateList.add(new PopulatorItem("SD", "SOUTH DAKOTA"));
		stateList.add(new PopulatorItem("TN", "TENNESSEE"));
		stateList.add(new PopulatorItem("TX", "TEXAS"));
		stateList.add(new PopulatorItem("UT", "UTAH"));
		stateList.add(new PopulatorItem("VA", "VIRGINIA"));
		stateList.add(new PopulatorItem("VT", "VERMONT"));
		stateList.add(new PopulatorItem("WA", "WASHINGTON"));
		stateList.add(new PopulatorItem("WI", "WISCONSIN"));
		stateList.add(new PopulatorItem("WV", "WEST VIRGINIA"));
		stateList.add(new PopulatorItem("WY", "WYOMING"));


		
		return stateList;
	}
	
	
}
