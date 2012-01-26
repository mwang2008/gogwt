package com.gogwt.apps.tracking.services.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.GDispItem;
import com.gogwt.apps.tracking.data.GLine;
import com.gogwt.apps.tracking.data.GLocation;
import com.gogwt.apps.tracking.data.GSmsData;
import com.gogwt.apps.tracking.data.GSmsItem;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.Status;
import com.gogwt.apps.tracking.data.TrackingMobileData;
import com.gogwt.apps.tracking.data.TrackingMobileDataCol;
import com.gogwt.apps.tracking.data.TrackingSmsData;
import com.gogwt.apps.tracking.data.request.LocationRequest;
import com.gogwt.apps.tracking.data.response.DisplayResponse;
import com.gogwt.apps.tracking.data.response.LocationResponse;
import com.gogwt.apps.tracking.data.response.LoginResponse;
import com.gogwt.apps.tracking.exceptions.AppRemoteException;
import com.gogwt.apps.tracking.utils.MessageUtils;
import com.gogwt.apps.tracking.utils.StringUtils;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderLocationType;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.LatLng;

public final class RestBusinessDomainService extends BaseBusinessDomainService {
	private static Logger logger = Logger
			.getLogger(RestBusinessDomainService.class);

	public LocationResponse processLocation(final LocationRequest request) {

		logger.debug(" ===== $$$$$ RestBusinessDomainService.LocationResponse LocationRequest="
				+ request.toString());

		LocationResponse response = new LocationResponse();
		List<TrackingMobileData> trackingMobileDataList = convertToTrackingMobileData(request);
		try {
			// save to DB
			if (request.hasLocation()) {
				int numOfInsert = getCustomerDAO().saveTrackingData(
						trackingMobileDataList);
			}
			if (request.hasSmsData()) {
				List<TrackingSmsData> smsDataList = convertToTrackingSmsData(request);
				int numOfsmsInsert = getCustomerDAO().saveSmsData(smsDataList);
			}
			
			logger.debug(" ===== RestBusinessDomainService.processLocation "
					+ request.toString());

			// save to memory
			ActiveSharedLocation.addGroupIdLocationRequestMap(request);
			ActiveSharedLocation.addGroupIdSmsRequestMap(request);
		} catch (AppRemoteException e) {
            e.printStackTrace();
		}

		Status status = new Status(200, "message from processLocation");
		response.setStatus(status);

		return response;
	}

	public List<TrackingMobileDataCol> retrieveLocationsSnapShot(
			CustomerProfile customerProfile) {
		logger.debug("retrieveLocations ");
		try {
			List<TrackingMobileData> maxList = getCustomerDAO().retrieveMaxLocationsSnapShot(customerProfile);
			List<TrackingMobileData> minList = getCustomerDAO().retrieveMinLocationsSnapShot(customerProfile);

			
			if (maxList == null || maxList.isEmpty() || minList == null || minList.isEmpty()) {
				return null;
			}

			//sort list
			TrackingMobileDataComparator comparator = new TrackingMobileDataComparator();
			Collections.sort(maxList, comparator);
			Collections.sort(minList, comparator);
 			 
			TrackingMobileData maxTrack;
			
			List<TrackingMobileDataCol> trackingMobileDataColList = new ArrayList<TrackingMobileDataCol>();
			TrackingMobileDataCol trackingMobileDataCol = null;
			String address=null;
			for (TrackingMobileData minTrack : minList) {
				address = findAddress(minTrack);
				minTrack.setAddress(address);
				
				maxTrack = findMaxTrackingPair(minTrack, maxList);
				if (maxTrack == null) {
					continue;
				}
				
				address = findAddress(maxTrack);
				maxTrack.setAddress(address);

				trackingMobileDataCol = new TrackingMobileDataCol(minTrack, maxTrack);
				
				trackingMobileDataColList.add(trackingMobileDataCol);
			}

			return trackingMobileDataColList;
			
		} catch (AppRemoteException e) {
			e.printStackTrace();
		}
		return null;
	}


	
	public List<TrackingMobileData> retrieveLocations(
			CustomerProfile customerProfile, Calendar endCal, Calendar startCal) {
		logger.debug("retrieveLocations ");
		try {
			return getCustomerDAO().retrieveLocations(customerProfile, endCal,
					startCal);
		} catch (AppRemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteTrack(String userName, String groupId,
			String displayName, long startTimeLong) {
		logger.debug("retrieveLocations ");
		try {
			int row = getCustomerDAO().deleteTrack(userName, groupId,
					displayName, startTimeLong);
			logger.debug(" delete rows = " + row);
		} catch (AppRemoteException e) {
			logger.error("Fail to delete record of groupId=" + groupId
					+ ",displayName=" + displayName + ",startTimeLong="
					+ startTimeLong);
		}
	}

	public List<TrackingMobileData> getTrack(String userName, String groupId,
			String displayName, long startTimeLong) {
		logger.debug("getTrack ");
		try {
			List<TrackingMobileData> trackingData = getCustomerDAO().getTrack(
					userName, groupId, displayName, startTimeLong);

			return trackingData;
		} catch (AppRemoteException e) {
			logger.error("Fail to get record of groupId=" + groupId
					+ ",displayName=" + displayName + ",startTimeLong="
					+ startTimeLong);
		}

		return null;
	}

	public List<TrackingSmsData> findAllSms(String groupId,	String displayName, long startTimeLong) {
		List<TrackingSmsData> trackingSmsList;
		logger.debug("findAllSms ");
		
		try {
			trackingSmsList = getCustomerDAO().findAllTrackingSmsData(groupId, displayName, startTimeLong);

			return trackingSmsList;
		} catch (AppRemoteException e) {
			logger.error("Fail to get record of groupId=" + groupId
					+ ",displayName=" + displayName + ",startTimeLong="
					+ startTimeLong);
		}

		return null;
		
	}
	/**
	 * Get active locations from shared location, called by RestClientController
	 * 
	 * @param groupId
	 * @return
	 */
	//public DisplayResponse ArrayList<GDispItem> getActiveLocationsByGroupId(final String groupId) {
	public DisplayResponse getActiveLocationsByGroupId(final String groupId) {
		// displayName(corresponding mobile phone), locations
		Map<String, List<GLocation>> activeLocMap = ActiveSharedLocation
				.getLocationMap(groupId);

		
		
		if (logger.isDebugEnabled()) {
			printOutActiveMap(activeLocMap);
		}

		ArrayList<GDispItem> retLocList = DomainServiceHelper
				.constructionActiveDisplayItemLocList(activeLocMap);

		Map<String, List<GSmsData>> activeMsmMap = ActiveSharedLocation.getSmsMap(groupId);
		
		ArrayList<GSmsItem> msmList = DomainServiceHelper.constructionActiveDisplaySmsItemList(activeMsmMap);
		 
		if (logger.isDebugEnabled()) {
			printOutGDispItem(retLocList);
		}
		
		Boolean hasNewTrackBool = ActiveSharedLocation.getNewTrackMap().get(groupId);
		boolean hasNewTrack = false;
		if (hasNewTrackBool != null) {
			hasNewTrack = hasNewTrackBool.booleanValue();
		}
		
		DisplayResponse response = new DisplayResponse();
		response.setDispLocations(retLocList);
		response.setSmsList(msmList);
		response.setHasNewTrack(hasNewTrack);
		
		//reset newtrack to false, as we already have one so far.
		ActiveSharedLocation.getNewTrackMap().put(groupId, false);
		
		return response;
	}

	/**
	 * 
	 * @param groupId
	 * @param displayName
	 * @param startTime
	 * @return
	 */
	public ArrayList<GDispItem> getHistorialTrackDetail(String groupId,
			String displayName, String startTime) {
		long startTimeLong = Long.parseLong(startTime);
		try {
			List<TrackingMobileData> retList = getCustomerDAO()
					.getTrack(null, groupId, displayName,
							startTimeLong);

			// convert List<TrackingMobileData> to ArrayList<GDispItem>
			if (retList == null || retList.isEmpty()) {
				return null;
			}

			if (logger.isDebugEnabled()) {
				printOutTrackingMobileData(retList);
			}
			
			ArrayList<GDispItem> locations = convertTrackingMobileDataListToGDispItemList(retList);
			
			if (logger.isDebugEnabled()) {
				printOutGDispItemData(locations);
			}
			
			/*
			 * GLine newGLine = null; GDispItem dispItem = new GDispItem();
			 * 
			 * int recordIndex = 0; List<GLocation> glocationList = new
			 * ArrayList<GLocation>(); GLocation glocation; for
			 * (TrackingMobileData track : retList) { //first record
			 * 
			 * if (recordIndex == 0) { newGLine =
			 * DomainServiceHelper.convertActiveToGLine(track, displayName, 0);
			 * }
			 * 
			 * glocation = DomainServiceHelper.convertToGLocation(track);
			 * glocationList.add(glocation);
			 * 
			 * //last record if (recordIndex == retList.size()-1) {
			 * dispItem.setLine(newGLine); dispItem.setLocs(glocationList);
			 * dispItem.setDispName(displayName); }
			 * 
			 * recordIndex++; }
			 * 
			 * ArrayList<GDispItem> locations = new ArrayList<GDispItem>();
			 * locations.add(dispItem);
			 */
			return locations;
		} catch (AppRemoteException e) {
			e.printStackTrace();
		}

		return null;
	}


	
	public ArrayList<GDispItem> convertTrackingMobileDataListToGDispItemList(List<TrackingMobileData> retList) {
		GLine newGLine = null;
		GDispItem dispItem = new GDispItem();

		int recordIndex = 0;
		List<GLocation> glocationList = new ArrayList<GLocation>();
		GLocation glocation;
		for (TrackingMobileData track : retList) {
			// first record
			if (recordIndex == 0) {
				newGLine = DomainServiceHelper.convertActiveToGLine(track, track.getDisplayName(), 0);
				newGLine.setStartAddr(findAddress(track));
			}
 		
			glocation = DomainServiceHelper.convertToGLocation(track);
			glocationList.add(glocation);

			// last record
			if (recordIndex == retList.size() - 1) {
				newGLine.setEndTime(track.getTime());
		        newGLine.setEndAddr(findAddress(track));
		        newGLine.setEndLat(track.getLatitude());
		        newGLine.setEndLng(track.getLongitude());
		        
				//newGLine.setEndAddr(endAddr);
				dispItem.setLine(newGLine);
				dispItem.setLocs(glocationList);
				dispItem.setDispName(track.getDisplayName());
			}

			recordIndex++;
		}

		ArrayList<GDispItem> locations = new ArrayList<GDispItem>();
		locations.add(dispItem);

		return locations;
	}

	public void stopTracking(Profile profile) {
		ActiveSharedLocation.removeClientFromMap(profile.getGroupId(),
				profile.getDisplayName());
	}

	/**
	 * Called by Rest login.
	 * 
	 * @param request
	 * @return
	 */
	public LoginResponse retrieveCustomerProfileByGroupId(final Profile request) {

		Profile profile = request;

		LoginResponse response = new LoginResponse();
		Status status = new Status();
		CustomerProfile customerProfile = null;
		try {
			customerProfile = getCustomerDAO()
					.retrieveCustomerProfileByGroupId(request.getGroupId());

			// save to
			status.setCode(200);
			status.setMsg(MessageUtils
					.getMessage("code.hand.cell.success.signin"));
			profile.setGroupId(request.getGroupId());
			profile.setServerUsername(customerProfile.getUserName());
			profile.setDisplayName(request.getDisplayName());
			profile.setServerFirstName(customerProfile.getFirstName());
			profile.setServerLastName(customerProfile.getLastName());
			profile.setServerEmail(customerProfile.getEmail());
			profile.setServerPhone(customerProfile.getPhoneNumber());
			
		} catch (AppRemoteException e) {
			// PMD
			logger.error(" could not find groupId = " + request.getGroupId());
			status.setCode(500);
			status.setMsg(MessageUtils.getMessage("code.hand.cell.fail.signin"));
		}

		response.setStatus(status);
		response.setProfile(profile);

		return response;

	}

	public List<TrackingSmsData> findAllSms(String groupId, String displayName, String startTime) {
		List<TrackingSmsData> smsList;
		try {
			long startTimeLong = Long.parseLong(startTime);
			smsList = getCustomerDAO().findAllTrackingSmsData(groupId, displayName, startTimeLong);
            return smsList;
		}
		catch (AppRemoteException e) {
			logger.error(e.getMessage() + ", groupId="+groupId +",displayName="+displayName + ",startTime="+startTime);
		}
		
		return null;
	}
	
	/**************************************************************
	 * PRIVATE METHODS
	 **************************************************************/

	private List<TrackingSmsData> convertToTrackingSmsData(final LocationRequest request) {
		List<TrackingSmsData> smsDataList = new ArrayList<TrackingSmsData>();
		Profile profile = request.getProfile();
		
		TrackingSmsData trackingSmsData;
		for (GSmsData smsData : request.getSmsDataList()) {
			trackingSmsData = new TrackingSmsData();
			
			trackingSmsData.setGroupId(profile.getGroupId());
			trackingSmsData.setDisplayName(profile.getDisplayName());
			trackingSmsData.setAddress(smsData.getAddress());
			trackingSmsData.setDate(smsData.getDate());
			trackingSmsData.setRead(smsData.getRead());
			trackingSmsData.setType(smsData.getType());
			trackingSmsData.setBody(smsData.getBody());
			trackingSmsData.setStartTime(smsData.getStartTime());
			 
			smsDataList.add(trackingSmsData);
		}
		
		return smsDataList;
	}
	 	
	private List<TrackingMobileData> convertToTrackingMobileData(
			final LocationRequest request) {
		List<TrackingMobileData> trackingMobileDataList = new ArrayList<TrackingMobileData>();
		List<GLocation> locations = request.getLocations();
		Profile profile = request.getProfile();

		if (locations == null || locations.isEmpty()) {
			return null;
		}

		String id = "";
		TrackingMobileData trackingMobileData = null;
		for (GLocation location : locations) {
			trackingMobileData = new TrackingMobileData();

			//use generated key
			//id = profile.getDisplayName()+"_"+location.getStartTime();
			//trackingMobileData.setId(id);
			
			trackingMobileData.setGroupId(profile.getGroupId());
			trackingMobileData.setPhoneNumber(profile.getPhoneNumber());
			trackingMobileData.setDisplayName(profile.getDisplayName());
			trackingMobileData.setLatitude(location.getLatitude());
			trackingMobileData.setLongitude(location.getLongitude());
			trackingMobileData.setAltitude(location.getAltitude());
			trackingMobileData.setProvider(location.getProvider());
			trackingMobileData.setAccuracy(location.getAccuracy());
			trackingMobileData.setBearing(location.getBearing());
			trackingMobileData.setDistance(location.getDistance());
			trackingMobileData.setSpeed(location.getSpeed());
			trackingMobileData.setTime(location.getTime());
			trackingMobileData.setStartTime(location.getStartTime());
			trackingMobileData.setTotalDistance(location.getTotalDistance());

			trackingMobileDataList.add(trackingMobileData);
		}
		return trackingMobileDataList;
	}

	private void printOutActiveMap(final Map<String, List<GLocation>> activeMap) {
		System.out.println(" \n\n ==== activeMap ==== ");
		if (activeMap != null) {
			for (Map.Entry<String, List<GLocation>> item : activeMap.entrySet()) {
				System.out.println(item.getKey());
				//handle ConcurrentModificationException of ArrayList
				synchronized (item.getValue()) {
				   for (GLocation location : item.getValue()) {
					  System.out.println("  " + location.toString());
				   }
				}
			}
		}
		else {
			System.out.println(" ==== activeMap is null ==== ");
		}
	}

	private void printOutGDispItem(ArrayList<GDispItem> dispItems) {
		System.out.println("\n\n ==== ArrayList<GDispItem> ===");
		if (dispItems != null) {
		   for (GDispItem item : dispItems) {
			  System.out.println("  " + item.toString());
		   }
		}
		else {
			System.out.println("  dispItems is null");
		}
	}

	private void printOutTrackingMobileData(List<TrackingMobileData> retList) {
		System.out.println(" \n\n ==== History Tracking Data from DB ==== ");
		int i=0;
		for (TrackingMobileData track : retList) {
			System.out.println("i= " + i++ + "," + track.toString());
		}
	}
	
	private void printOutGDispItemData(ArrayList<GDispItem> locations) {
		System.out.println(" \n\n ==== History GDispItem Data ==== ");
		int i=0;
		for (GDispItem dispItem : locations) {
			System.out.println("i= " + i++ + "," + dispItem.toString());
		}	
	}
	
	private TrackingMobileData findMaxTrackingPair(TrackingMobileData minTrack, List<TrackingMobileData> maxList) {
	   for (TrackingMobileData maxTrack : maxList) {
		   if (StringUtils.equalsIgnoreCase(minTrack.getGroupId(), maxTrack.getGroupId()) &&
				   StringUtils.equals(minTrack.getDisplayName(), maxTrack.getDisplayName()) &&
				   minTrack.getStartTime() == maxTrack.getStartTime()){
			   return maxTrack;
		   }
	   }
		   
	   return null;
	}
		
		/**
		 * 
		 * http://code.google.com/intl/uk/apis/maps/documentation/geocoding/#ReverseGeocoding
		 * @param trackingData
		 * @return
		 */
	public String findAddress(TrackingMobileData trackingData) {
		BigDecimal lat, lng;
		GeocoderRequest request = new GeocoderRequest();
		
		try {
		    // reverse geocode
		    final Geocoder geocoder = new Geocoder();
			
   		    lat = new BigDecimal(trackingData.getLatitude() / 1e6);
 		    lng = new BigDecimal(trackingData.getLongitude() / 1e6);
			
		    request.setLocation(new LatLng(lat, lng));
		    GeocodeResponse geocoderResponse = geocoder.geocode(request);
				 
		    GeocoderLocationType locationType = geocoderResponse
					.getResults().get(0).getGeometry().getLocationType();
		    String address = geocoderResponse.getResults().get(0)
					.getFormattedAddress();

		    if (locationType == GeocoderLocationType.ROOFTOP
				|| locationType == GeocoderLocationType.RANGE_INTERPOLATED) {
			   return address;
		    }
		    else {
		    	return "(latitude,longitude) : (" + trackingData.getLatitude() + ", " + trackingData.getLongitude() + ")"; 
		    }
		}
		catch (Throwable e) {
			//do nothing, just return null
		}
		return "(latitude,longitude) : (" + trackingData.getLatitude() + ", " + trackingData.getLongitude() + ")";
	}
}
