package com.gogwt.apps.tracking.data.response;

import java.util.ArrayList;
import java.util.Collection;

import com.gogwt.apps.tracking.data.GDispItem;

public class DisplayResponse {
	 
	private ArrayList<GDispItem> dispLocations;
	

	public ArrayList<GDispItem> getDispLocations() {
		return dispLocations;
	}

	public void setDispLocations(ArrayList<GDispItem> dispLocations) {
		this.dispLocations = dispLocations;
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
