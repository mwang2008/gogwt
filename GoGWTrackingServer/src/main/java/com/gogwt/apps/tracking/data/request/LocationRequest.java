package com.gogwt.apps.tracking.data.request;

import java.util.List;

import com.gogwt.apps.tracking.data.GLocation;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.Setting;

public class LocationRequest {
    private List<GLocation> locations;
    private Profile  profile;
 
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
	
	public boolean hasLocation () {
		return (locations != null && !locations.isEmpty());
	}
	
	public String toString() {

		StringBuilder sbuf = new StringBuilder();
		sbuf.append(Setting.class.getSimpleName() + "=[");
		int i=0;
		if (locations != null && !locations.isEmpty()) {
			for (GLocation location : locations) {
				sbuf.append("i=" + i++ + ", "+ location.toString());
			}			 
		}
		else {
			sbuf.append(" No location ");
		}
		
		sbuf.append(profile);
		 
	 	sbuf.append("]");

		return sbuf.toString();

	}
}
