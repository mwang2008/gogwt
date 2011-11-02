package com.gogwt.apps.tracking.services.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.GDispItem;
import com.gogwt.apps.tracking.data.GLine;
import com.gogwt.apps.tracking.data.GLocation;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.Status;
import com.gogwt.apps.tracking.data.TrackingMobileData;
import com.gogwt.apps.tracking.data.request.LocationRequest;
import com.gogwt.apps.tracking.data.response.LocationResponse;
import com.gogwt.apps.tracking.data.response.LoginResponse;
import com.gogwt.apps.tracking.exceptions.AppRemoteException;
import com.gogwt.apps.tracking.utils.MessageUtils;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderLocationType;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.LatLng;

public final class RestBusinessDomainService extends BaseBusinessDomainService {
	private static Logger logger = Logger.getLogger(RestBusinessDomainService.class);
	
	public LocationResponse processLocation(final LocationRequest request) {
	   
		logger.debug(" ===== $$$$$ RestBusinessDomainService.LocationResponse LocationRequest=" + request.toString());
		
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
	

	public List<TrackingMobileData> retrieveLocationsSnapShot(CustomerProfile customerProfile) {
		logger.debug("retrieveLocations " );
		try {
			List<TrackingMobileData> retList = getCustomerDAO().retrieveLocationsSnapShot(customerProfile);
			
			if (retList == null || retList.isEmpty()) {
				return null;
			}
			
			
			Collections.sort(retList, new Comparator() {
				@Override
				public int compare(Object c1, Object c2) {
					TrackingMobileData o1 = (TrackingMobileData)c1;
					TrackingMobileData o2 = (TrackingMobileData)c2;
					
					if (o1.getStartTime() - o2.getStartTime() == 0) {
						if (o1.getDisplayName().equals(o2.getDisplayName())) {
							return (int)(o1.getTime()-o2.getTime());
						}
						else {
							return o1.getDisplayName().compareTo(o2.getDisplayName());
						}						
					}
					else {
						return (int)(o1.getStartTime() - o2.getStartTime());
					}

				}
				/*
				public int compare(TrackingMobileData o1, TrackingMobileData o2) {
					if (o1.getDisplayName().equals(o2.getDisplayName())) {
						return (int)(o1.getTime()-o2.getTime());
					}
					else {
						return o1.getDisplayName().compareTo(o2.getDisplayName());
					}   APPROXIMATE, GEOMETRIC_CENTER, RANGE_INTERPOLATED, ROOFTOP;	
				}
				*/			 
			});
			
			//reverse geocode
			final Geocoder geocoder = new Geocoder();
			GeocodeResponse geocoderResponse;

	        //http://code.google.com/intl/uk/apis/maps/documentation/geocoding/#ReverseGeocoding
			GeocoderRequest request = new GeocoderRequest();
			BigDecimal lat, lng;
			
			for (TrackingMobileData track: retList) {
				lat = new BigDecimal(track.getLatitude()/1e6);
				lng = new BigDecimal(track.getLongitude()/1e6);
				request.setLocation(new LatLng(lat, lng));
		        geocoderResponse = geocoder.geocode(request);
		        GeocoderLocationType locationType = geocoderResponse.getResults().get(0).getGeometry().getLocationType();
		        String address = geocoderResponse.getResults().get(0).getFormattedAddress();
		        
		        if (locationType == GeocoderLocationType.ROOFTOP || locationType == GeocoderLocationType.RANGE_INTERPOLATED) {
		        	track.setAddress(address);
		        }
		        System.out.println("geocoderResponse="+locationType.value() + " address="+address);		        
		        //track.setAddress(geocoderResponse.getResults()[0])
			}
			
		    return retList;
		}
        catch (AppRemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<TrackingMobileData> retrieveLocations(CustomerProfile customerProfile, Calendar endCal, Calendar startCal) {
		logger.debug("retrieveLocations " );
		try {
		    return getCustomerDAO().retrieveLocations(customerProfile, endCal, startCal);
		}
        catch (AppRemoteException e) {
        	e.printStackTrace();
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

	/**
	 * 
	 * @param groupId
	 * @param displayName
	 * @param startTime
	 * @return
	 */
	public  ArrayList<GDispItem> getHistorialTrackDetail(String groupId, String displayName, String startTime) {
		long startTimeLong = Long.parseLong(startTime); 
		try {
		   List<TrackingMobileData> retList = getCustomerDAO().getHistorialTrackDetail(groupId, displayName, startTimeLong);
		   
		   //convert List<TrackingMobileData> to ArrayList<GDispItem>
		   if (retList == null || retList.isEmpty()) {
			   return null;
		   }
		    
		   GLine newGLine = null;
		   GDispItem dispItem = new GDispItem();
		    
		   int recordIndex = 0;
		   List<GLocation> glocationList = new ArrayList<GLocation>();
		   GLocation glocation;
		   for (TrackingMobileData track : retList) {
			   //first record
			   if (recordIndex == 0) {
				   newGLine = DomainServiceHelper.convertActiveToGLine(track, displayName, 0);   				   
			   }
			   
			   glocation = DomainServiceHelper.convertToGLocation(track);
			   glocationList.add(glocation);
			    
			   //last record
			   if (recordIndex == retList.size()-1) {
				   dispItem.setLine(newGLine);
				   dispItem.setLocs(glocationList);
				   dispItem.setDispName(displayName);
			   }
			   
			   recordIndex++;			   
		   }
		   
		   ArrayList<GDispItem> locations = new ArrayList<GDispItem>();
		   locations.add(dispItem);
		   
		   return locations; 
		}
		catch (AppRemoteException e) {
        	e.printStackTrace();
		}
		
		return null;
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
