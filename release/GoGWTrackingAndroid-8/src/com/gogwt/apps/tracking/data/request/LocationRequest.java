package com.gogwt.apps.tracking.data.request;

import java.util.List;

import com.gogwt.apps.tracking.data.GLocation;
import com.gogwt.apps.tracking.data.GSmsData;
import com.gogwt.apps.tracking.data.Profile;

public class LocationRequest {
    private List<GLocation> locations;
    private Profile  profile;
    private List<GSmsData> smsDataList;
    
	public List<GLocation> getLocations() {
		return locations;
	}

	public void setLocations(List<GLocation> locations) {
		this.locations = locations;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<GSmsData> getSmsDataList() {
		return smsDataList;
	}

	public void setSmsDataList(List<GSmsData> smsDataList) {
		this.smsDataList = smsDataList;
	}

}
