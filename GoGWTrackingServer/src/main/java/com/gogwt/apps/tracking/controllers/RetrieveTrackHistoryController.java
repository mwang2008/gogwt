package com.gogwt.apps.tracking.controllers;

import static com.gogwt.apps.tracking.AppConstants.CUSTOMER_PROFILE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.TrackingMobileData;
import com.gogwt.apps.tracking.data.TrackingMobileDataCol;
import com.gogwt.apps.tracking.exceptions.AppRemoteException;
import com.gogwt.apps.tracking.services.domain.LookupBusinessService;
import com.gogwt.apps.tracking.services.domain.RestBusinessDomainService;
import com.gogwt.apps.tracking.utils.StringUtils;

/**
 * 
 * retrievetracks
 * type=days
 * days=2
 * days=7
 * 
 * @author michael.wang
 * 
 */
public class RetrieveTrackHistoryController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(RetrieveTrackHistoryController.class);

	@Override
	protected ModelAndView handleRequestInternal(
			final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("handleRequestInternal");
		HttpSession session = request.getSession();
	 
		String typeParam = request.getParameter("type");
		String daysParam = request.getParameter("days");
		
		CustomerProfile customerProfile = (CustomerProfile)session.getAttribute(CUSTOMER_PROFILE);
		
		//TrackingMobileData;
		Calendar todayCal = Calendar.getInstance();
		Calendar startCal = null; 
		
		try {
		   int days = Integer.parseInt(daysParam);
		   startCal = Calendar.getInstance();
		   startCal.add(Calendar.DATE, (-1)*days);
		}
		catch (NumberFormatException e ) {
			//do nothing
		}
		
		final RestBusinessDomainService service =  LookupBusinessService.getRestBusinessDomainService();
		//List<TrackingMobileData> locationList = service.retrieveLocationsSnapShot(customerProfile);
		
		List<TrackingMobileDataCol> trackingMobileDataColList	= null;
		try {
			trackingMobileDataColList = service.retrieveLocationsSnapShot(customerProfile);
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		
		final ModelMap modelMap = new ModelMap();
		
		if (trackingMobileDataColList == null || trackingMobileDataColList.isEmpty()) {
			modelMap.addAttribute( "hasResult", false );
		}
		else {
			modelMap.addAttribute( "hasResult", true );
			//ArrayList<ArrayList<TrackingMobileData>> groupLocationList = groupInDisplay(locationList);
		    //modelMap.addAttribute( "trackHistoryList", locationList );	
		    //modelMap.addAttribute( "trackHistoryGroupList", groupLocationList );
			modelMap.addAttribute( "trackHistoryGroupList", trackingMobileDataColList );
		}
		
		return new ModelAndView("/tracking/show_history_tracking_list").addAllObjects(modelMap);
	}

	private ArrayList<ArrayList<TrackingMobileData>> groupInDisplay(List<TrackingMobileData> locationList) {
		ArrayList<ArrayList<TrackingMobileData>> retList = new ArrayList<ArrayList<TrackingMobileData>>();
		
		//sort by startTime and displayName
		ArrayList<TrackingMobileData> subList = null;
		String displayName=null;
		long startTime=-1;
		for (TrackingMobileData track : locationList) {
			if (startTime == track.getStartTime() && StringUtils.equals(displayName, track.getDisplayName())) {
				subList.add(track);
			}
			else {
				//start new track
				if (displayName != null) {
					retList.add(subList);
				}
				startTime = track.getStartTime();
				displayName = track.getDisplayName();
				subList = new ArrayList<TrackingMobileData>();
				subList.add(track);
			}
		}
		
		//add the last one
		retList.add(subList);
		
		return retList;
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
