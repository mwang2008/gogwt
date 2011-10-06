package com.gogwt.apps.tracking.controllers;

import static com.gogwt.apps.tracking.AppConstants.CUSTOMER_PROFILE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.GLocation;
import com.gogwt.apps.tracking.data.Profile;
import com.gogwt.apps.tracking.data.request.LocationRequest;
import com.gogwt.apps.tracking.data.response.LocationResponse;
import com.gogwt.apps.tracking.services.domain.ActiveSharedLocation;
import com.gogwt.apps.tracking.services.domain.LookupBusinessService;
import com.gogwt.apps.tracking.services.domain.RestBusinessDomainService;

public class AddLocationController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(AddLocationController.class);

	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {
		final GLocation requestForm = new GLocation();

		HttpSession session = request.getSession();
        CustomerProfile customerProfile = (CustomerProfile)session.getAttribute(CUSTOMER_PROFILE);

        String groupId="";
        if (customerProfile != null) {
        	groupId = customerProfile.getGroupId();
        }
        
        requestForm.setGroupId(groupId);
        
		return requestForm;
	}
	
	protected ModelAndView showForm(final HttpServletRequest request,
			final HttpServletResponse response, final BindException errors,
			final Map controlModel) throws Exception {
		logger.debug("AddLocationController - In showForm()");

		return super.showForm(request, response, errors, controlModel);

	}
	
	public ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception {
		
		final GLocation formBean = (GLocation) command;

		String action = request.getParameter("action");
		if (StringUtils.equalsIgnoreCase("Add", action)) {
			// call login
			try {
				System.out.println(" === formBean === " + formBean.toString());
				LocationRequest locationRequest = new LocationRequest();
				
				Profile  profile = new Profile();
				profile.setGroupId(formBean.getGroupId());
				profile.setDisplayName(request.getParameter("displayName"));
				profile.setServerUsername("mwang");
				profile.setServerLastName("michael");
				profile.setServerLastName("wang");
				profile.setPhoneNumber("12344333");
				
				locationRequest.setProfile(profile);
				
				List<GLocation> locations = new ArrayList<GLocation>();
				locations.add(formBean);
				
				locationRequest.setLocations(locations);
				
				final RestBusinessDomainService service =  LookupBusinessService.getRestBusinessDomainService();
				LocationResponse locationResponse = service.processLocation(locationRequest);
				
				logger.debug(" ==== response: " + locationResponse.toString());			
			} 	 
	        catch (Throwable te) {
				te.printStackTrace();
				logger.debug("Unknown error code");        	
	        }
		}
		else if (StringUtils.equalsIgnoreCase("Remove The Track", action)) {
			 
			ActiveSharedLocation.removeClientFromMap(formBean.getGroupId(), request.getParameter("displayName")); 	
		}
	
		
		//final String targetURL = getSuccessURL(request);
		//String successView = getSuccessView();
		final ModelMap modelMap = new ModelMap();
		return super.showForm(request, response, errors, modelMap);
		
	 }
}
