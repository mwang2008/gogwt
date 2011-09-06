package com.gogwt.apps.tracking.data.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gogwt.apps.tracking.data.GDispItem;
import com.gogwt.apps.tracking.data.GLocation;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.Stock;
import com.gogwt.apps.tracking.data.StockDailyRecord;
import com.gogwt.apps.tracking.data.TrackingMobileData;

public class DisplayResponse {
	/**@deprecated*/
	private Map<String, List<GLocation>> locDataMap;
	/**@deprecated*/
	private Map<String, Profile> profileMap;
	/**@deprecated*/
	private String test;
	/**@deprecated*/
	private Map<String,List<TrackingMobileData>> myMap;
	/**@deprecated*/
	private List<TrackingMobileData> mylist;
	/**@deprecated*/
	private long maxLat, maxLng, minLat, minLng;
	
	private Collection<GDispItem> dispLocations;
	

	public Collection<GDispItem> getDispLocations() {
		return dispLocations;
	}

	public void setDispLocations(Collection<GDispItem> dispLocations) {
		this.dispLocations = dispLocations;
	}

	public Map<String, List<GLocation>> getLocDataMap() {
		return locDataMap;
	}

	public void setLocDataMap(Map<String, List<GLocation>> locDataMap) {
		this.locDataMap = locDataMap;
	}

	public Map<String, Profile> getProfileMap() {
		return profileMap;
	}

	public void setProfileMap(Map<String, Profile> profileMap) {
		this.profileMap = profileMap;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public long getMaxLat() {
		return maxLat;
	}

	public void setMaxLat(long maxLat) {
		this.maxLat = maxLat;
	}

	public long getMaxLng() {
		return maxLng;
	}

	public void setMaxLng(long maxLng) {
		this.maxLng = maxLng;
	}

	public long getMinLat() {
		return minLat;
	}

	public void setMinLat(long minLat) {
		this.minLat = minLat;
	}

	public long getMinLng() {
		return minLng;
	}

	public void setMinLng(long minLng) {
		this.minLng = minLng;
	}

	public List<TrackingMobileData> getMylist() {
		return mylist;
	}

	public void setMylist(List<TrackingMobileData> mylist) {
		this.mylist = mylist;
	}

	public Map<String, List<TrackingMobileData>> getMyMap() {
		return myMap;
	}

	public void setMyMap(Map<String, List<TrackingMobileData>> myMap) {
		this.myMap = myMap;
	}

	
	
	/*
	 * public DisplayResponse() { mapData = new HashMap<String,
	 * List<GLocation>>();
	 * 
	 * List<GLocation> arList = new ArrayList<GLocation>(); GLocation location =
	 * new GLocation(); location.setLatitude(34030000);
	 * location.setLongitude(-84190000); arList.add(location);
	 * 
	 * location = new GLocation(); location.setLatitude(34090000);
	 * location.setLongitude(-84100000); arList.add(location);
	 * 
	 * mapData.put("u1", arList);
	 * 
	 * 
	 * arList = new ArrayList<GLocation>(); location = new GLocation();
	 * location.setLatitude(44030000); location.setLongitude(-84190000);
	 * arList.add(location);
	 * 
	 * location = new GLocation(); location.setLatitude(44090000);
	 * location.setLongitude(-84100000); arList.add(location);
	 * 
	 * mapData.put("u2", arList);
	 * 
	 * }
	 */

	public String toString() {

		StringBuilder sbuf = new StringBuilder();
		sbuf.append(DisplayResponse.class.getSimpleName() + "=[");
		
		sbuf.append(" dispLocations=[" );
		if (dispLocations != null && !dispLocations.isEmpty()) {
			int i=0; 
			for (GDispItem item : dispLocations) {
				sbuf.append("i=" + i++ + ","+ item.toString());
				sbuf.append("\n");
			}
		}
		else {
			sbuf.append(" is null" );
		}
		sbuf.append("]");

		return sbuf.toString();

	}
	
	public String getContent() {
		return toString();
	}
}
