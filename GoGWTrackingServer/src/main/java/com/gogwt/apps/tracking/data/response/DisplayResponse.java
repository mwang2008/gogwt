package com.gogwt.apps.tracking.data.response;

import java.util.ArrayList;

import com.gogwt.apps.tracking.data.GDispItem;
import com.gogwt.apps.tracking.data.GSmsItem;

public class DisplayResponse {
	 
	private ArrayList<GDispItem> dispLocations;
	private boolean hasNewTrack = false;
	private ArrayList<GSmsItem>  smsList;
	

	public ArrayList<GDispItem> getDispLocations() {
		return dispLocations;
	}

	public void setDispLocations(ArrayList<GDispItem> dispLocations) {
		this.dispLocations = dispLocations;
	}
 
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
			sbuf.append(" loc is null" );
		}
		
		if (smsList != null && !smsList.isEmpty()) {
			for (int i=0; i<smsList.size(); i++) {
				sbuf.append("i=" + i + ","+ smsList.get(i).toString());
				sbuf.append("\n");
			}
		}
		else {
			sbuf.append(" sms is null" );
		}
		sbuf.append("]");

		return sbuf.toString();

	}
	
	public String getContent() {
		return toString();
	}

	public boolean isHasNewTrack() {
		return hasNewTrack;
	}

	public void setHasNewTrack(boolean hasNewTrack) {
		this.hasNewTrack = hasNewTrack;
	}

	public ArrayList<GSmsItem> getSmsList() {
		return smsList;
	}

	public void setSmsList(ArrayList<GSmsItem> smsList) {
		this.smsList = smsList;
	}
 
}
