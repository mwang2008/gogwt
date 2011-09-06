package com.gogwt.apps.tracking.ws.rest;

import static com.gogwt.apps.tracking.AppConstants.CUSTOMER_PROFILE;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.gogwt.apps.tracking.data.GDispItem;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.TrackingMobileData;
import com.gogwt.apps.tracking.data.request.LocationRequest;
import com.gogwt.apps.tracking.data.response.DisplayResponse;
import com.gogwt.apps.tracking.data.response.LocationResponse;
import com.gogwt.apps.tracking.data.response.LoginResponse;
import com.gogwt.apps.tracking.services.domain.DomainServiceHelper;
import com.gogwt.apps.tracking.services.domain.LookupBusinessService;
import com.gogwt.apps.tracking.services.domain.RestBusinessDomainService;


@Controller
public class RestClientController {
	protected static Logger logger = Logger.getLogger("RestServiceController");
	
 	/**
	 * http://localhost/tracking/test
	 * @return
	 */
	@RequestMapping(value="/resttest", method=RequestMethod.GET)
	public String test() {
		logger.info("==== Spring Android Showcase");
		return "/tracking/home";
	}
	
	
	@RequestMapping(value="mobilelogin", method=RequestMethod.POST, headers="Content-Type=application/xml")
	public @ResponseBody LoginResponse sendLocationXML(@RequestBody Profile request) {
		logger.debug(" ==== sendLocation XML input: " + request.toString());
		
		final RestBusinessDomainService service =  LookupBusinessService.getRestBusinessDomainService();
		LoginResponse response = service.retrieveCustomerProfileByGroupId(request);
		
		logger.debug(" ==== response: " + response.toString());
		return response;
 	}
	
	@RequestMapping(value="mobilelogin", method=RequestMethod.POST, headers="Content-Type=application/json")
	public @ResponseBody LoginResponse sendLoginJSON(@RequestBody Profile request) {
		logger.debug(" ==== sendLocation JSON input: " + request.toString());
		final RestBusinessDomainService service =  LookupBusinessService.getRestBusinessDomainService();
		LoginResponse response = service.retrieveCustomerProfileByGroupId(request);
		
		logger.debug(" ==== response: " + response.toString());
		return response;
	}
	
	/**
	 * Send location 
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
	 * Send location
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
	

	/* 
	@RequestMapping(value="displaylocation", method=RequestMethod.GET, headers = "Content-Type=application/xml, application/json")	
	public @ResponseBody DisplayResponse displayLocationContent(@RequestParam("groupId") String groupId, @RequestParam("days") String daysParam, final HttpServletRequest request ) {
		return processDisp(groupId, daysParam, request);
	}
	*/
	
	/*@RequestMapping(value="displaylocation", method=RequestMethod.GET, headers="Accept=application/json")	
	public @ResponseBody DisplayResponse displayLocationJSON(@RequestParam("groupId") String groupId, @RequestParam("days") String daysParam, final HttpServletRequest request ) {
		System.out.println(" ==== displayLocationJSON");
		return processDisp(groupId, daysParam, request);
	}	
	
	@RequestMapping(value="displaylocation", method=RequestMethod.GET, headers="Accept=application/xml")	
	public @ResponseBody DisplayResponse displayLocationXML(@RequestParam("groupId") String groupId, @RequestParam("days") String daysParam, final HttpServletRequest request ) {
		System.out.println(" ==== displayLocationXML");
		return processDisp(groupId, daysParam, request);
	}
	
	@RequestMapping(value="displaylocation", method=RequestMethod.GET, headers="Content-Type=application/xml")	
	public @ResponseBody DisplayResponse displayLocationCXML(@RequestParam("groupId") String groupId, @RequestParam("days") String daysParam, final HttpServletRequest request ) {
		System.out.println(" ==== displayLocationCXML");
		return processDisp(groupId, daysParam, request);
	}
	
	@RequestMapping(value="displaylocation", method=RequestMethod.GET, headers="Content-Type=application/json")	
	public @ResponseBody DisplayResponse displayLocationCJSON(@RequestParam("groupId") String groupId, @RequestParam("days") String daysParam, final HttpServletRequest request ) {
		System.out.println(" ==== displayLocationCJSON");
		
		return processDisp(groupId, daysParam, request);
	}
	*/
	@RequestMapping(value="displaylocation", method=RequestMethod.GET, headers="Accept=application/json")	
	public @ResponseBody DisplayResponse displayLocationJSON(@RequestParam("groupId") String groupId, @RequestParam("days") String daysParam, final HttpServletRequest request ) {
		System.out.println(" ==== displayLocationJSON");
		return processHistoryDisp(groupId, daysParam, request);
	}	
	
	@RequestMapping(value="displaylocation", method=RequestMethod.GET, headers="Accept=application/xml")	
	public @ResponseBody DisplayResponse displayLocationXML(@RequestParam("groupId") String groupId, @RequestParam("days") String daysParam, final HttpServletRequest request ) {
		System.out.println(" ==== displayLocationXML");
		return processHistoryDisp(groupId, daysParam, request);
	}
	
	@RequestMapping(value="displaycurrentlocation", method=RequestMethod.GET, headers="Accept=application/json")	
	public @ResponseBody DisplayResponse displayCurrentLocationJSON(@RequestParam("groupId") String groupId, final HttpServletRequest request ) {
		System.out.println(" ====%%% displayCurrentlocation displayCurrentLocationJSON");
		return processCurrentDisp(groupId, request);
	}	
	
	@RequestMapping(value="displaycurrentlocation", method=RequestMethod.GET, headers="Accept=application/xml")	
	public @ResponseBody DisplayResponse displayCurrentLocationXML(@RequestParam("groupId") String groupId, final HttpServletRequest request ) {
		System.out.println(" ====%%% displayCurrentlocation displayCurrentLocationXML");
		return processCurrentDisp(groupId, request);
	}
	
	private DisplayResponse processCurrentDisp(String groupId, final HttpServletRequest request) { 
		HttpSession session = request.getSession();
        CustomerProfile customerProfile = (CustomerProfile)session.getAttribute(CUSTOMER_PROFILE);

        final RestBusinessDomainService service =  LookupBusinessService.getRestBusinessDomainService();
        final Collection<GDispItem> dispLocation = service.getActiveLocationsByGroupId(customerProfile.getGroupId());
        
         
        DisplayResponse response = new DisplayResponse();
        response.setDispLocations(dispLocation);
        
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
		final Collection<GDispItem> dispLocation = DomainServiceHelper.constructDisplayItemList(locationList);
		
		DisplayResponse response = new DisplayResponse();
		response.setDispLocations(dispLocation);
		
		response.setMylist(locationList);
		 Map<String,List<TrackingMobileData>> myMap = new  HashMap<String,List<TrackingMobileData>>();
		 myMap.put("dis 11", locationList);
		 myMap.put("dis 22", locationList);
		 myMap.put("dis 33", locationList);
		 response.setMyMap(myMap);
		 
		response.setTest(" hello " + groupId + ", " + daysParam);
		 
		logger.debug(response.toString());
		System.out.println(" === " + response.toString());
		
		return response;
	}
}
