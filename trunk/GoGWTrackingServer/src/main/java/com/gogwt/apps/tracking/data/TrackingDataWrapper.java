package com.gogwt.apps.tracking.data;

import java.util.ArrayList;

public class TrackingDataWrapper {
	ArrayList<TrackingMobileData> locList;
	ArrayList<TrackingSmsData> smsList;
	
	public ArrayList<TrackingMobileData> getLocList() {
		return locList;
	}
	public void setLocList(ArrayList<TrackingMobileData> locList) {
		this.locList = locList;
	}
	public ArrayList<TrackingSmsData> getSmsList() {
		return smsList;
	}
	public void setSmsList(ArrayList<TrackingSmsData> smsList) {
		this.smsList = smsList;
	}
    
	
}
