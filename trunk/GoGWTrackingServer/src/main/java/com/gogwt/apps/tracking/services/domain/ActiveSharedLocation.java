package com.gogwt.apps.tracking.services.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.GLocation;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.request.LocationRequest;

public final class ActiveSharedLocation {
	
	//<groupId, Map<displayName, locationList>>
    private static Map<String, Map<String, List<GLocation>>> aciveClientLocationMap;
    
    //<groupId, Map<displayName, Profile>>
    private static Map<String, Map<String, Profile>> activeClientProfileMap;
	  
	public static void addGroupIdLocationRequestMap(LocationRequest request) {
		//do not add if no location
		if (request == null || request.getLocations() == null || request.getLocations().isEmpty()) {
			return;
		}
		
		if (aciveClientLocationMap == null) {
	 		aciveClientLocationMap = new LinkedHashMap<String, Map<String, List<GLocation>>>();
		}
				
		String groupId = request.getProfile().getGroupId();
		String displayName = request.getProfile().getDisplayName();
		List<GLocation> newLocations = request.getLocations();
		
		Map<String, List<GLocation>> locationMap = getLocationMap(groupId);
		if (locationMap == null) {
			locationMap = new LinkedHashMap<String, List<GLocation>>();
		}
		
		displayName = displayName.trim();
		
		List<GLocation> existingLocationList = locationMap.get(displayName);
		if (existingLocationList == null) {
			existingLocationList = new ArrayList<GLocation>();
		}
		existingLocationList.addAll(newLocations);
		locationMap.put(displayName, existingLocationList);
		
		aciveClientLocationMap.put(groupId, locationMap);
		
		if (activeClientProfileMap == null) {
			activeClientProfileMap = new ConcurrentHashMap<String, Map<String, Profile>>();
		}

		Map<String, Profile> displayNameProfileMap = activeClientProfileMap.get(groupId);
		if (displayNameProfileMap == null) {
			displayNameProfileMap = new ConcurrentHashMap<String, Profile>();
		}
		
		if (!displayNameProfileMap.containsKey(displayName)) {
			displayNameProfileMap.put(displayName, request.getProfile());
			activeClientProfileMap.put(groupId, displayNameProfileMap);
		}
	}
    
	public static Map<String, Map<String, List<GLocation>>> getActiveLocationMap() {
		return aciveClientLocationMap;
	}
	
	public static boolean hasAnyEntry() {
		if (aciveClientLocationMap == null) {
			return false;
		}
		
		Set keySet = aciveClientLocationMap.keySet();
		if (keySet == null || keySet.isEmpty()) {
			return false;
		}
		
		
		return true;
	}
	
	public static Map<String, List<GLocation>> getLocationMap(CustomerProfile customerProfile) {
		return getLocationMap(customerProfile.getGroupId());
	}
	
	public static Map<String, List<GLocation>> getLocationMap(String groupId) {
		if (aciveClientLocationMap == null || aciveClientLocationMap.isEmpty()) {
			return null;
		}
	 	
		return aciveClientLocationMap.get(groupId);
	}
	
	public static Map<String, Profile> getProfileMap(String groupId) {
		if (activeClientProfileMap == null) {
			return null;
		}
		
		return activeClientProfileMap.get(groupId);
	}
	
	public static List<GLocation> getLocationList(String groupId, String displayName) {
		Map<String, List<GLocation>> locationMap = getLocationMap(groupId);
		if (locationMap == null) {
			return null;
		}
		return locationMap.get(displayName);		
	}
	
	public static boolean contains(String groupId, String displayName) {
		if (!aciveClientLocationMap.containsKey(groupId)) {
			return false;
		}
		
		return getLocationMap(groupId).containsKey(displayName);
 	}
	
	/**
	 * Remove client data once client (mobile phone user) from active map data.
	 * @param groupId
	 * @param displayName
	 */
	public static void removeClientFromMap(String groupId, String displayName) {
		displayName = displayName.trim();
		
		Map<String, List<GLocation>> displayNameLocationMap =  getLocationMap(groupId);
		if (displayNameLocationMap != null && displayNameLocationMap.containsKey(displayName)) {
			displayNameLocationMap.remove(displayName);
		}
		
		Map<String, Profile> displayNameProfileMap = getProfileMap(groupId);
	    if (displayNameProfileMap != null && displayNameProfileMap.containsKey(displayName)) {
	    	displayNameProfileMap.remove(displayName);
	    }
	    
	    //return color to the color pool
	    ActiveColorAssignmentManager.removeColor(displayName);
	}
}
