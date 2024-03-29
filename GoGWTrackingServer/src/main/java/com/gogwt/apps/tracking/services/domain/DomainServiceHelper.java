package com.gogwt.apps.tracking.services.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gogwt.apps.tracking.data.GDispItem;
import com.gogwt.apps.tracking.data.GLine;
import com.gogwt.apps.tracking.data.GLocation;
import com.gogwt.apps.tracking.data.GSmsData;
import com.gogwt.apps.tracking.data.GSmsItem;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.TrackingMobileData;
import com.gogwt.apps.tracking.data.TrackingSmsData;
import com.gogwt.apps.tracking.utils.StringUtils;

public final class DomainServiceHelper {

   
	/**
	 * 
	 * @param activeMap  dispName, List<Glocation>  
	 * 
	 * @return
	 */
   public static ArrayList<GDispItem> constructionActiveDisplayItemLocList(Map<String, List<GLocation>> activeMap ) {
	   if (activeMap == null || activeMap.isEmpty()) {
		   return null;
	   }
	   
	   String key = null;
	   String dispName = null;
	   GLine gline = null, newGLine = null;
	   List<GLocation> locations = null;
	   GDispItem dispItem = null;
	   List<GLocation> existingGLocList;
	   
	   Map<String, GDispItem> dispMap = new LinkedHashMap<String, GDispItem>();
	   
	   int numOfClient = 0;
	   for (Map.Entry<String, List<GLocation>> entry : activeMap.entrySet())
	   {
		   //System.out.println(entry.getKey() + "/" + entry.getValue());
		   dispName = entry.getKey().trim();
		   locations = entry.getValue();
		   
	       for (GLocation location : locations) {
	    	   key = dispName + "|" + location.getStartTime();
	    	   if (dispMap.containsKey(key)) {
		 			dispItem = dispMap.get(key);
		 			
		 			gline = dispItem.getLine();
		 			gline.setEndTime(location.getTime());
					gline.setEndLat(location.getLatitude());
					gline.setEndLng(location.getLongitude());
					
					existingGLocList = dispItem.getLocs();
					existingGLocList.add(location);
					
					dispItem.setLine(gline);
					dispItem.setLocs(existingGLocList);
					
					//add back to map
					dispMap.put(key, dispItem);
				
				}
				else {
					newGLine = convertActiveToGLine(location, dispName, numOfClient++);
					
					List<GLocation> glocationList = new ArrayList<GLocation>();
					glocationList.add(location);
					
					dispItem = new GDispItem(); 
					dispItem.setLine(newGLine);
					dispItem.setLocs(glocationList);
					dispItem.setDispName(dispName);
					
					dispMap.put(key, dispItem);	 				 
				}		  
	       }	       
	   }
	   
	   //return dispMap.values();
	   ArrayList<GDispItem> list = new ArrayList<GDispItem>(dispMap.values());
       return list;
   }
   
   public static ArrayList<GDispItem> constructHistoryDisplayItemList(List<TrackingMobileData> locationList) {
	   if (locationList == null || locationList.isEmpty()) {
		   return null;
	   }
	      
	   //key=dispname+starttime;
	   Map<String, GDispItem> dispMap = new LinkedHashMap<String, GDispItem>();
	   String key = null;
	   
	   GLine gline = null, newGLine = null;
	   GLocation location;
	   GDispItem dispItem;
	   List<GLocation> existingGLocList;
	   int numOfClient = 0;
	   for (TrackingMobileData trackData : locationList) {
		    key = trackData.getDisplayName().trim() + trackData.getStartTime();		   
		    location = convertToGLocation(trackData);
		    
 	 		if (dispMap.containsKey(key)) {
	 			dispItem = dispMap.get(key);
	 			
	 			gline = dispItem.getLine();
	 			gline.setEndTime(trackData.getTime());
				gline.setEndLat(trackData.getLatitude());
				gline.setEndLng(trackData.getLongitude());
				
				existingGLocList = dispItem.getLocs();
				existingGLocList.add(location);
				
				dispItem.setLine(gline);
				dispItem.setLocs(existingGLocList);
				
				//add back to map
				dispMap.put(key, dispItem);
			
			}
			else {
				newGLine = convertToGLine(trackData, numOfClient++);
				
				List<GLocation> glocationList = new ArrayList<GLocation>();
				glocationList.add(location);
				
				dispItem = new GDispItem(); 
				dispItem.setLine(newGLine);
				dispItem.setLocs(glocationList);
				
				dispMap.put(key, dispItem);
 				 
			}		  
	   }
	   
	   //return dispMap.values();
	   ArrayList<GDispItem> list = new ArrayList<GDispItem>(dispMap.values());
       return list;
   }
   
    
   /**
    * 
    * @param activeMsmMap  <displayName, List<GSmsData
    * @return
    */
   public static ArrayList<GSmsItem> constructionActiveDisplaySmsItemList(Map<String, List<GSmsData>> activeMsmMap) {
	   if (activeMsmMap == null || activeMsmMap.isEmpty()) {
		   return null;
	   }
	   
	   ArrayList<GSmsItem> msmItemList = new ArrayList<GSmsItem>();
	   GSmsItem gSmsItem = null;
	   String displayName = null;
	   List<GSmsData> gSmsData = null;
	   for (Map.Entry<String, List<GSmsData>> entry : activeMsmMap.entrySet()) {
		   displayName = entry.getKey();
		   gSmsData = entry.getValue();
		   
		   int index = fromResultList(displayName, msmItemList);
		   
		   if (index != -1) {
			   msmItemList.get(index).getSmsList().addAll(gSmsData);
		   }
		   else {
			   gSmsItem = new GSmsItem();
			   gSmsItem.setDispName(displayName);
			   gSmsItem.setSmsList(gSmsData);
			   msmItemList.add(gSmsItem);
		   }
	   }
	   
	   return msmItemList;
   }

   public static ArrayList<GSmsItem> convertTrackingSmsListToGSmsItemList(final List<TrackingSmsData> trackingSmsList) {
	   GSmsItem gsmsItem = convertTrackingSmsListToGSmsItem(trackingSmsList);
	   
	   if (gsmsItem == null) {
		   return null;
	   }
	   
	   ArrayList<GSmsItem> gSmsItemList = new ArrayList<GSmsItem>();
	   gSmsItemList.add(gsmsItem);
	   
	   return gSmsItemList;
   }

	   
   public static GSmsItem convertTrackingSmsListToGSmsItem(final List<TrackingSmsData> trackingSmsList) {
	  if (trackingSmsList == null || trackingSmsList.isEmpty()) {
		  return null;
	  }
	  	  
	  GSmsItem gSmsItem = new GSmsItem();
	  for (TrackingSmsData trackingSms : trackingSmsList) {
		  if (StringUtils.equalsIgnoreCase(gSmsItem.getDispName(), trackingSms.getDisplayName())) {
			  gSmsItem.getSmsList().addAll(convertTrackingSmsToGSmsItem(trackingSms));
		  }
		  else {			 
			  gSmsItem.setSmsList(convertTrackingSmsToGSmsItem(trackingSms));
			  gSmsItem.setDispName(trackingSms.getDisplayName());
		  }
	  }	  
	  return gSmsItem;
   }
   
   public static List<GSmsData> convertTrackingSmsToGSmsItem(final TrackingSmsData trackingSms) {
	   if (trackingSms == null) {
		   return null;
	   }
	   
	     
	   List<GSmsData> smsList = new ArrayList<GSmsData>();
	   
	   GSmsData gSmsData = new GSmsData();	   
	   gSmsData.setAddress(trackingSms.getAddress());
	   gSmsData.setBody(trackingSms.getBody());
	   gSmsData.setDate(trackingSms.getDate());
	   gSmsData.setRead(trackingSms.getRead());
	   gSmsData.setType(trackingSms.getType());
	   gSmsData.setStartTime(trackingSms.getStartTime());
	   
	   smsList.add(gSmsData);
	   
	    
	   
	   return smsList;
   }
   private static int fromResultList(final String displayName, ArrayList<GSmsItem> msmList) {
	   if (msmList == null || msmList.isEmpty()) {
		   return -1;
	   }
	   
	   for (int i=0; i<msmList.size(); i++) {
		   if (StringUtils.equalsIgnoreCase(displayName, msmList.get(i).getDispName())) {
			   return i;
		   }
	   }
	   return -1;
   }
	
   private static GLine convertToGLine(TrackingMobileData trackData, int numOfClient) {
	   GLine line = new GLine();
	   
	   String color = getColor(numOfClient);
	   
	   line.setWidth(2);
	   line.setHtml(trackData.getDisplayName());
	   line.setColor(color);
	   line.setLabel(trackData.getDisplayName());
	   line.setStartLat(trackData.getLatitude());
	   line.setStartLng(trackData.getLongitude());
	   line.setEndLat(trackData.getLatitude());
	   line.setEndLng(trackData.getLongitude());
	   line.setStartTime(trackData.getStartTime());
	   line.setEndTime(trackData.getStartTime());
	   
	   return line;
   }
   
   public static GLine convertActiveToGLine(GLocation location, String displayName, int numOfClient) {
	   GLine line = new GLine();
	   
	   String color = getColorPerDisplayName(numOfClient, displayName);
	   
	   line.setWidth(2);
	   line.setHtml(displayName);
	   line.setColor(color);
	   line.setLabel(displayName);
	   line.setStartLat(location.getLatitude());
	   line.setStartLng(location.getLongitude());
	   line.setEndLat(location.getLatitude());
	   line.setEndLng(location.getLongitude());
	   line.setStartTime(location.getStartTime());
	   line.setEndTime(location.getStartTime());
	   line.setDispName(displayName);
	   
	   return line;
   }
   
   public static GLocation convertToGLocation(TrackingMobileData trackData) {
	   GLocation location = new GLocation();
	   
	   
	   location.setGroupId(trackData.getGroupId());
	   location.setLatitude(trackData.getLatitude());
	   location.setLongitude(trackData.getLongitude());
	   location.setAltitude(trackData.getAltitude());
	   location.setProvider(trackData.getProvider());
	   location.setAccuracy(trackData.getAccuracy());
	   location.setBearing(trackData.getBearing());
	   location.setDistance(trackData.getDistance());
	   location.setSpeed(trackData.getSpeed());
	   location.setTime(trackData.getTime());
	   location.setStartTime(trackData.getStartTime());
	   location.setTotalDistance(trackData.getTotalDistance());
	   
	   return location;
   }
   
   private static String getColor(int i) {
	   if (i == 0) { return "#FF0000";}  //red 
	   if (i == 1) { return "#00FF00";}  //green }
	   if (i == 2) { return "#0000FF";}  //blue }
	   if (i == 3) { return "#FFFF00";}  //yellow }
	   if (i == 4) { return "#00FFFF";}  //red }
	   if (i == 5) { return "#FF00FF";}  //purple }
	   
	   return "#000000"; //black
	   
	   
   }
   
   private static String getColorPerDisplayName(int i, String displayName) {
	   return ActiveColorAssignmentManager.getColor(displayName);
   }
  	 
  // public static GLine 
   /**
    * 
    * @param trackData
    * @return
    * @deprecated
    */
   private static GDispItem convertDisplayItem(TrackingMobileData trackData) {
	   GDispItem gdispItem = new GDispItem();
	   
	   Profile profile = null;
	   List<GLocation> locs = null;
	   GLine line = null;

	   String displayName = trackData.getDisplayName();
	   trackData.getGroupId();
	   
	   return gdispItem;
   }
   
   /*
   private DisplayResponse convertMobileDataToDisplayData(List<TrackingMobileData> locationList) {
		Map<String, List<GLocation>> retData = new HashMap<String, List<GLocation>>();
		Map<String, Profile> retProfile = new HashMap<String, Profile>();
		Profile profile = null;
		
		long maxLat=-999999999, maxLng=-999999999, minLat=999999999, minLng=999999999;
		String displayName = null;
		long startTime = -999;
		String key = null;
		
		for (TrackingMobileData trackData : locationList) {
			displayName = trackData.getDisplayName();
			startTime = trackData.getStartTime();
			key = displayName + "|" + startTime;
			
			if (trackData.getLatitude() > maxLat) {
				maxLat = trackData.getLatitude();
			}
			if (trackData.getLongitude() > maxLng) {
				maxLng = trackData.getLongitude();
			}
			if (trackData.getLatitude() < minLat) {
				minLat = trackData.getLatitude();
			}
			if (trackData.getLongitude() < minLng) {
				minLng = trackData.getLongitude();
			}
			
			if (retData.containsKey(key)) {
				retData.get(key).add(trackData);
			}
			else {
				List<GLocation> trackDataList = new ArrayList<GLocation>();
				trackDataList.add(trackData);
				retData.put(key, trackDataList);
				
				profile = new Profile();
				profile.setGroupId(trackData.getGroupId());
				profile.setDisplayName(trackData.getDisplayName());
				profile.setPhoneNumber(trackData.getPhoneNumber());
		
				retProfile.put(key, profile);
			}
		}
		
		DisplayResponse response = new DisplayResponse();
		response.setLocDataMap(retData);
		response.setProfileMap(retProfile);
		response.setMaxLat(maxLat);
		response.setMaxLng(maxLng);
		response.setMinLat(minLat);
		response.setMinLng(minLng);
		
		return response;
	}
	*/
}
