package com.gogwt.apps.tracking.ws.rest;

import static com.gogwt.apps.tracking.AppConstants.CUSTOMER_PROFILE;
import static com.gogwt.apps.tracking.AppConstants.TRACK_DATA_WRAPPER;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.EnrollResult;
import com.gogwt.apps.tracking.data.GDispItem;
import com.gogwt.apps.tracking.data.GSmsItem;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.Status;
import com.gogwt.apps.tracking.data.TrackingDataWrapper;
import com.gogwt.apps.tracking.data.TrackingMobileData;
import com.gogwt.apps.tracking.data.TrackingSmsData;
import com.gogwt.apps.tracking.data.request.LocationRequest;
import com.gogwt.apps.tracking.data.response.DisplayResponse;
import com.gogwt.apps.tracking.data.response.EnrollResponse;
import com.gogwt.apps.tracking.data.response.LocationResponse;
import com.gogwt.apps.tracking.data.response.LoginResponse;
import com.gogwt.apps.tracking.exceptions.AppRemoteException;
import com.gogwt.apps.tracking.exceptions.DuplicatedUserNameException;
import com.gogwt.apps.tracking.formbean.EnrollCustomerFormBean;
import com.gogwt.apps.tracking.services.domain.DomainServiceHelper;
import com.gogwt.apps.tracking.services.domain.LookupBusinessService;
import com.gogwt.apps.tracking.services.domain.ProfileBusinessDomainService;
import com.gogwt.apps.tracking.services.domain.RestBusinessDomainService;
import com.gogwt.apps.tracking.utils.StringUtils;


@Controller
public class RestClientController {
	protected static Logger logger = Logger.getLogger("RestClientController");
	
 	/**
	 * http://localhost/tracking/en-us/resttest
	 * @return
	 */
	@RequestMapping(value="/resttest", method=RequestMethod.GET)
	public String test() {
		logger.info("==== Spring Android Showcase");
		return "/tracking/home";
	}
	
	
	@RequestMapping(value="mobilelogin", method=RequestMethod.POST, headers="Content-Type=application/xml")
	public @ResponseBody LoginResponse sendLocationXML(@RequestBody Profile request) {
		logger.debug(" ==== mobilelogin XML input: " + request.toString());
		
		final RestBusinessDomainService service =  LookupBusinessService.getRestBusinessDomainService();
		LoginResponse response = service.retrieveCustomerProfileByGroupId(request);
		
		logger.info("     mobilelogin response: " + response.getStatus().getCode());
		return response;
 	}
	
	@RequestMapping(value="mobilelogin", method=RequestMethod.POST, headers="Content-Type=application/json")
	//@RequestMapping(value="mobilelogin", method=RequestMethod.GET, headers="Content-Type=application/json")
	public @ResponseBody LoginResponse sendLoginJSON(@RequestBody Profile request) {
		//logger.debug(" ==== mobilelogin JSON input: " + request.toString());
		System.out.println(" ==== mobilelogin JSON input: " + request.toString());
		final RestBusinessDomainService service =  LookupBusinessService.getRestBusinessDomainService();
		LoginResponse response = service.retrieveCustomerProfileByGroupId(request);
		
		System.out.println("      mobilelogin response: " + response.getStatus().getCode());
		return response;
	}
	
	/**
	 * Send location from android
	 * http://localhost/tracking/en-us/mobilelocation  post
	 * @param request
	 * @return
	 */
	@RequestMapping(value="mobilelocation", method=RequestMethod.POST, headers="Content-Type=application/xml")
	public @ResponseBody LocationResponse sendLoginXML(@RequestBody LocationRequest request) {
		logger.debug(" ==== sendLocation XML input: " + request.toString());
		
		final RestBusinessDomainService service =  LookupBusinessService.getRestBusinessDomainService();
		LocationResponse response = service.processLocation(request);
		
		logger.debug(" ==== response: " + response.toString());
		return response;
 	}
	
	/**
	 * Send location from android
	 * http://localhost/tracking/en-us/mobilelocation  post
	 * @param request
	 * @return
	 */
	@RequestMapping(value="mobilelocation", method=RequestMethod.POST, headers="Content-Type=application/json")
	public @ResponseBody LocationResponse sendLocationJSON(@RequestBody LocationRequest request) {
		logger.debug(" ==== sendLocation JSON input: " + request.toString());
		final RestBusinessDomainService service =  LookupBusinessService.getRestBusinessDomainService();
		LocationResponse response = service.processLocation(request);
		
		logger.debug(" ==== response: " + response.toString());
		return response;
	}
	

	/**
	 * Send enroll 
	 * @param request
	 * @return
	 */
	/*
	@RequestMapping(value="mobileenroll", method=RequestMethod.POST, headers="Content-Type=application/xml")
	public @ResponseBody EnrollResponse sendEnrollXML(@RequestBody EnrollCustomerFormBean request) {
		logger.info(" ==== sendEnrollXML XML input: " + request.toString());
		
		final ProfileBusinessDomainService businessService = LookupBusinessService
				.getProfileBusinessDomainService();

		EnrollResponse response = new EnrollResponse();
		Status status = new Status();
		
		try {
			CustomerProfile customerProfile = businessService.enrollCustomer(request);
			status.setCode(200);
			customerProfile.setLogin(false);
			
			response.setCustomerProfile(customerProfile);
			
		} catch (DuplicatedUserNameException e) {			
			status.setCode(600);
			status.setMsg("The groupId is used already, please use other one");
			e.printStackTrace();
		} catch (AppRemoteException e) {
			status.setCode(602);
			status.setMsg("Server error, please try again later");

			e.printStackTrace();
		}
	 	 
		response.setStatus(status);
		logger.info(" ==== response: " + response.toString());
		return response;
 	}
	*/
	
	/**
	 * Send location
	 * @param request
	 * @return
	 */
	@RequestMapping(value="mobileenroll", method=RequestMethod.POST, headers="Content-Type=application/json")
	public @ResponseBody EnrollResponse sendEnrollJSON(@RequestBody EnrollCustomerFormBean request) {
		System.out.println(" ==== sendEnrollJSON JSON input: " + request.toString());
		final ProfileBusinessDomainService businessService = LookupBusinessService
				.getProfileBusinessDomainService();

		EnrollResponse response = new EnrollResponse();
		Status status = new Status();
		
		try {
			CustomerProfile customerProfile = businessService.enrollCustomer(request);
			status.setCode(200);
			EnrollResult result = new EnrollResult();
			
			result.setId(customerProfile.getId());
			result.setGroupId(customerProfile.getGroupId());
			result.setGroupName(customerProfile.getGroupName());
			result.setFirstName(customerProfile.getFirstName());
			result.setLastName(customerProfile.getLastName());
			result.setEmail(customerProfile.getEmail());
			result.setUserName(customerProfile.getUserName());
			result.setPhoneNumber(customerProfile.getPhoneNumber());
			result.setLogin(customerProfile.getLogin());
			result.setActive(customerProfile.getActive());
			result.setCreateDate(customerProfile.getCreateDate());
			
			//todo: response.setCustomerProfile(customerProfile);
			response.setResult(result);
			
		} catch (DuplicatedUserNameException e) {			
			status.setCode(600);
			status.setMsg("The groupId is used already, please use other one");
			e.printStackTrace();
		} catch (AppRemoteException e) {
			status.setCode(602);
			status.setMsg("Server error, please try again later");

			e.printStackTrace();
		}
	 	 
		response.setStatus(status);
		 
		System.out.println(" ==== response: status="+response.getStatus().getCode());
		if (response.getResult() != null) {
			System.out.println("    groupId="+response.getResult().getGroupId());
			System.out.println("    username="+response.getResult().getUserName());
			System.out.println("    firstname="+response.getResult().getFirstName());
			System.out.println("    lastName="+response.getResult().getLastName());
		}
		
		return response;
	}
	
	@RequestMapping(value="displaylocation", method=RequestMethod.GET, headers="Accept=application/json")	
	public @ResponseBody DisplayResponse displayLocationJSON(@RequestParam("groupId") String groupId, @RequestParam("days") String daysParam, final HttpServletRequest request ) {
		 
		return processHistoryDisp(groupId, daysParam, request);
	}	
	
	@RequestMapping(value="displaylocation", method=RequestMethod.GET, headers="Accept=application/xml")	
	public @ResponseBody DisplayResponse displayLocationXML(@RequestParam("groupId") String groupId, @RequestParam("days") String daysParam, final HttpServletRequest request ) {
		 
		return processHistoryDisp(groupId, daysParam, request);
	}
	
	/**
	 * Called from client (browsers), Ajax call
	 * Active current track
	 * http://localhost/tracking/en-us/displaycurrentlocation?groupId=g5&days=5
	 * @param groupId
	 * @param request
	 * @return displayResponse contains loc list and sms list
	 */
	@RequestMapping(value="displaycurrentlocation", method=RequestMethod.GET, headers="Accept=application/json")	
	public @ResponseBody DisplayResponse displayCurrentLocationJSON(@RequestParam("groupId") String groupId, final HttpServletRequest request ) {
		logger.debug(" ====%%% displayCurrentlocation displayCurrentLocationJSON");
		return processCurrentDisp(groupId, request);
	}	
	
	@RequestMapping(value="displaycurrentlocation", method=RequestMethod.GET, headers="Accept=application/xml")	
	public @ResponseBody DisplayResponse displayCurrentLocationXML(@RequestParam("groupId") String groupId, final HttpServletRequest request ) {
		logger.debug(" ====%%% displayCurrentlocation displayCurrentLocationXML");
		return processCurrentDisp(groupId, request);
	}
	
	
	/**
	 * Called from client (browsers), Ajax call to display track detail for history (not active track)
	 * 
	 * http://localhost/tracking/en-us/displaytrackdetail?groupId=g5&displayName=show+5&startTime=1
	 * @param groupId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="displaytrackdetail", method=RequestMethod.GET, headers="Accept=application/json")	
	public @ResponseBody DisplayResponse displayTrackDetailJSON(@RequestParam("groupId") String groupId, @RequestParam("displayName") String displayName, 
			@RequestParam("startTime") String startTime, @RequestParam("from") String from, final HttpServletRequest request ) {
		logger.debug(" ====%%% displayTrackDetailJSON displayCurrentLocationJSON");
		return processTrackDetailDisp(groupId, displayName, startTime, from, request);
	}	
	
	@RequestMapping(value="displaytrackdetail", method=RequestMethod.GET, headers="Accept=application/xml")	
	public @ResponseBody DisplayResponse displayTrackDetailXML(@RequestParam("groupId") String groupId, @RequestParam("displayName") String displayName, 
			@RequestParam("startTime") String startTime, @RequestParam("from") String from, final HttpServletRequest request ) {
		logger.debug(" ====%%% displayTrackDetailXML displayCurrentLocationXML");
		return processTrackDetailDisp(groupId, displayName, startTime, from, request);
	}

	
	/**
	 * tracking/en-us/stoptracking
	 * @param profile
	 */
	@RequestMapping(value="stoptracking", method=RequestMethod.POST, headers="Content-Type=application/xml")
	public @ResponseBody String stopTrackingXML(@RequestBody Profile profile) {
		logger.debug(" ==== stopTrackingXML XML input: " + profile.toString());
		
		final RestBusinessDomainService service =  LookupBusinessService.getRestBusinessDomainService();
		service.stopTracking(profile);
		
		return "success";
		 
 	}
 
	
	
	@RequestMapping(value="stoptracking", method=RequestMethod.POST, headers="Content-Type=application/json")
	public @ResponseBody String stopTrackingJSON(@RequestBody Profile profile) {
		logger.debug(" ==== stopTrackingJSON JSON input: " + profile.toString());
		final RestBusinessDomainService service =  LookupBusinessService.getRestBusinessDomainService();
		service.stopTracking(profile);
		
		return "success";
	}
	
	
	/********************************************************
	 *  PRIVATE METHODS 
	 */
	private DisplayResponse processCurrentDisp(String groupId, final HttpServletRequest request) { 
	    final RestBusinessDomainService service =  LookupBusinessService.getRestBusinessDomainService();
          
        DisplayResponse response = service.getActiveLocationsByGroupId(groupId);
        
        logger.debug(" response " + response.toString());
        
        return response;
        
	}
	
	private DisplayResponse processTrackDetailDisp(String groupId, String displayName, String startTime, String from, final HttpServletRequest request) {
		
		final RestBusinessDomainService service =  LookupBusinessService.getRestBusinessDomainService();
		final ArrayList<GDispItem> dispLocation; // = service.getHistorialTrackDetail(groupId, displayName, startTime);	 
		List<TrackingSmsData> smsList;
		
		if (StringUtils.isSet(from) && from.equalsIgnoreCase("import")) {
			//get from import function.
			HttpSession session = request.getSession();
			TrackingDataWrapper trackingDataWrapper = (TrackingDataWrapper)session.getAttribute(TRACK_DATA_WRAPPER);
			ArrayList<TrackingMobileData> trackDataList = trackingDataWrapper.getLocList();
		    smsList = trackingDataWrapper.getSmsList();
			
			dispLocation = service.convertTrackingMobileDataListToGDispItemList(trackDataList);			 
		}
		else {
			//get from db
			smsList = service.findAllSms(groupId, displayName, startTime);
			dispLocation = service.getHistorialTrackDetail(groupId, displayName, startTime);
		}
		
		final ArrayList<GSmsItem> smsItemList = DomainServiceHelper.convertTrackingSmsListToGSmsItemList(smsList);
	 	 
		DisplayResponse response = new DisplayResponse();
	    response.setDispLocations(dispLocation);
	    response.setSmsList(smsItemList);
	    
	    logger.debug(" response " + response.toString());
	    
		return response;
	}
	
	private DisplayResponse processHistoryDisp(String groupId, String daysParam, final HttpServletRequest request) {
		HttpSession session = request.getSession();
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
		List<TrackingMobileData> locationList = service.retrieveLocations(customerProfile, todayCal, startCal);
		final ArrayList<GDispItem> dispLocation = DomainServiceHelper.constructHistoryDisplayItemList(locationList);
		
		DisplayResponse response = new DisplayResponse();
		response.setDispLocations(dispLocation);
	 	 
		logger.debug(response.toString());
		 
		
		return response;
	}
}
