package com.gogwt.apps.tracking.services.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.GDispItem;
import com.gogwt.apps.tracking.data.GLocation;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.Status;
import com.gogwt.apps.tracking.data.TrackingMobileData;
import com.gogwt.apps.tracking.data.request.LocationRequest;
import com.gogwt.apps.tracking.data.response.LocationResponse;
import com.gogwt.apps.tracking.data.response.LoginResponse;
import com.gogwt.apps.tracking.exceptions.AppRemoteException;
import com.gogwt.apps.tracking.utils.MessageUtils;

public final class RestBusinessDomainService extends BaseBusinessDomainService {
	private static Logger logger = Logger.getLogger(RestBusinessDomainService.class);
	
	public LocationResponse processLocation(final LocationRequest request) {
	   
		System.out.println(" ===== $$$$$ RestBusinessDomainService.LocationResponse LocationRequest=" + request.toString());
		
		LocationResponse response = new LocationResponse();
		List<TrackingMobileData> trackingMobileDataList = convertToTrackingMobileData(request);
		try {
			//save to DB
			if (request.hasLocation()) {
		      int numOfInsert = getCustomerDAO().saveTrackingData(trackingMobileDataList);
			}
			
			logger.debug(" ===== RestBusinessDomainService.processLocation " + request.toString());
			
			//save to memory
			ActiveSharedLocation.addGroupIdLocationRequestMap(request);
		}
		catch (AppRemoteException e) {
			
		}
		
		Status status = new Status(200, "message from processLocation");
		response.setStatus(status);
		
		return response;
	}
	

	public List<TrackingMobileData> retrieveLocations(CustomerProfile customerProfile, Calendar endCal, Calendar startCal) {
		logger.debug("retrieveLocations " );
		try {
		    return getCustomerDAO().retrieveLocations(customerProfile, endCal, startCal);
		}
        catch (AppRemoteException e) {
			
		}
		return null;
		
	}

	/**
	 * Get active locations from shared location
	 * @param groupId
	 * @return
	 */
	public ArrayList<GDispItem> getActiveLocationsByGroupId(final String groupId) {
		 //displayName(corresponding mobile phone), locations
		 Map<String, List<GLocation>> activeMap = ActiveSharedLocation.getLocationMap(groupId);
		 
		 return DomainServiceHelper.constructionActiveDisplayItemList(activeMap);
	}

	public void stopTracking(Profile profile) {
		ActiveSharedLocation.removeClientFromMap(profile.getGroupId(), profile.getDisplayName()); 		
	}
	
	/**
	 * Called by Rest login.
	 * @param request
	 * @return
	 */
	public LoginResponse retrieveCustomerProfileByGroupId(final Profile request) {
		
		Profile profile = request;
		
		LoginResponse response = new LoginResponse();
		Status status = new Status();
		CustomerProfile customerProfile = null;
		try {
			customerProfile = getCustomerDAO().retrieveCustomerProfileByGroupId(request.getGroupId());
			
			//save to 
			status.setCode(200);
			status.setMsg(MessageUtils.getMessage("code.hand.cell.success.signin"));
			profile.setGroupId(request.getGroupId());
			profile.setServerUsername(customerProfile.getUserName());
			profile.setDisplayName(request.getDisplayName());
			profile.setServerFirstName(customerProfile.getFirstName());
			profile.setServerLastName(customerProfile.getLastName());
			profile.setServerEmail(customerProfile.getEmail());
		}
		catch (AppRemoteException e) {
			//PMD 
			logger.error(" could not find groupId = " + request.getGroupId());
			status.setCode(500);
			status.setMsg(MessageUtils.getMessage("code.hand.cell.fail.signin"));
		}
		
		response.setStatus(status);
		response.setProfile(profile);
		
		return response;
		
	}
	
	/**************************************************************
	 *  PRIVATE METHODS
	 **************************************************************/
	
	private List<TrackingMobileData> convertToTrackingMobileData(final LocationRequest request) {
		List<TrackingMobileData> trackingMobileDataList = new ArrayList<TrackingMobileData>();
		List<GLocation> locations = request.getLocations();
		Profile profile = request.getProfile();
		
		if (locations == null || locations.isEmpty()) {
			return null;
		}
		
		TrackingMobileData trackingMobileData = null;
		for (GLocation location: locations) {
			trackingMobileData = new TrackingMobileData();
			
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
	 
}
