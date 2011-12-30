package com.gogwt.apps.tracking.services.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.GLocation;
import com.gogwt.apps.tracking.data.GSmsData;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.request.LocationRequest;

public final class ActiveSharedLocation {
	
	//<groupId, Map<displayName, locationList>>
    private static Map<String, Map<String, List<GLocation>>> aciveClientLocationMap;
    
    //<groupId, Map<displayName, smsList>>
    private static Map<String, Map<String, List<GSmsData>>> activeClientSmsMap;
    
    //<groupId, Map<displayName, Profile>>
    private static Map<String, Map<String, Profile>> activeClientProfileMap;
    
    //<groupId, Boolean>
	private static Map<String, Boolean> newTrackMap = new HashMap<String, Boolean>();
	
	/**
	 * Add sms to shared activeClientSmsMap
	 * @param request
	 */
	public static void addGroupIdSmsRequestMap(LocationRequest request) {
		//do not add if no sms
		if (request == null || request.getSmsDataList() == null || request.getSmsDataList().isEmpty()) {
			return;
		}
		
		String groupId = request.getProfile().getGroupId();
		String displayName = request.getProfile().getDisplayName();
		displayName = displayName.trim();
		
		long newStartTime = request.getSmsDataList().get(0).getStartTime();
		
		//active sms
		if (activeClientSmsMap == null) {
			activeClientSmsMap = new LinkedHashMap<String, Map<String, List<GSmsData>>>();
		}
		
		List<GSmsData> gsmsDataList = request.getSmsDataList();
		if (gsmsDataList != null && !gsmsDataList.isEmpty()) {
			//displayName,List<GSmsData>
			Map<String, List<GSmsData>> smsMap = getSmsMap(groupId);
			if (smsMap == null) {
				smsMap = new LinkedHashMap<String, List<GSmsData>>();
			}
			
			List<GSmsData> existingSmsList = smsMap.get(displayName);
			//check existing track: if startTime of existing is not equals to new startTime, 
			//the existing most likely is left over, (user start new track without click "Stop").
			//we need to remove it by null it.
			if (existingSmsList != null && !existingSmsList.isEmpty()) {
				if (newStartTime != existingSmsList.get(0).getStartTime()) {						 
					existingSmsList.clear();	
		    	}			
			}
			
			if (existingSmsList == null) {
				existingSmsList = new ArrayList<GSmsData>();
			}
			
			existingSmsList.addAll(gsmsDataList);
			smsMap.put(displayName, existingSmsList);
			activeClientSmsMap.put(groupId, smsMap);
		}
	}
    /**
     * Add locations to the shared obj
     * @param request
     */
	public static void addGroupIdLocationRequestMap(LocationRequest request) {
		//do not add if no location
		if (request == null || request.getLocations() == null || request.getLocations().isEmpty()) {
			return;
		}
		
		String groupId = request.getProfile().getGroupId();
		
		//active Location
		if (aciveClientLocationMap == null) {
			newTrackMap.put(groupId, true); 
	 		aciveClientLocationMap = new LinkedHashMap<String, Map<String, List<GLocation>>>();
		}
				
		
		String displayName = request.getProfile().getDisplayName();
		displayName = displayName.trim();
		long startTime = request.getLocations().get(0).getStartTime();
		
		List<GLocation> newLocations = request.getLocations();
		
		Map<String, List<GLocation>> locationMap = getLocationMap(groupId);
		if (locationMap == null) {
			locationMap = new LinkedHashMap<String, List<GLocation>>();
		}
		
		List<GLocation> existingLocationList = locationMap.get(displayName);
		
		//check existing track: if startTime of existing is not equals to new startTime, 
		//the existing most likely is left over, (user start new track without click "Stop").
		//we need to remove it by null it.
		if (existingLocationList != null && !existingLocationList.isEmpty()) {
			if (startTime != existingLocationList.get(0).getStartTime()) {	
				newTrackMap.put(groupId, true); 
				existingLocationList.clear();	
	    	}			
		}
		
		if (existingLocationList == null) {
			newTrackMap.put(groupId, true);
			existingLocationList = new ArrayList<GLocation>();
		}
		existingLocationList.addAll(newLocations);
		locationMap.put(displayName, existingLocationList);
		
		aciveClientLocationMap.put(groupId, locationMap);
		
		//active profile
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
	
	public static Map<String, List<GSmsData>> getSmsMap(String groupId) {
		if (activeClientSmsMap == null || activeClientSmsMap.isEmpty()) {
			return null;
		}
	 	
		return activeClientSmsMap.get(groupId);
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

	public static Map<String, Boolean> getNewTrackMap() {
		return newTrackMap;
	}
 
}
