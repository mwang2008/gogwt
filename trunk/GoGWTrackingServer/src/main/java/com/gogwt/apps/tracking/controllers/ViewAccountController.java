package com.gogwt.apps.tracking.controllers;

import static com.gogwt.apps.tracking.AppConstants.CUSTOMER_PROFILE;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.exceptions.AppRemoteException;
import com.gogwt.apps.tracking.exceptions.DuplicatedUserNameException;
import com.gogwt.apps.tracking.formbean.EnrollCustomerFormBean;
import com.gogwt.apps.tracking.services.domain.LookupBusinessService;
import com.gogwt.apps.tracking.services.domain.ProfileBusinessDomainService;
import com.gogwt.apps.tracking.utils.CookieUtils;

/**
 * View Account, Change password etc
 * @author Michael.Wang
 *
 */
public class ViewAccountController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(EnrollCustomerController.class);
	
	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		CustomerProfile customerProfile = (CustomerProfile)session.getAttribute(CUSTOMER_PROFILE);
		final EnrollCustomerFormBean requestForm = new EnrollCustomerFormBean();
		if (customerProfile != null) {
		    requestForm.setGroupId(customerProfile.getGroupId());
		    requestForm.setGroupName(customerProfile.getGroupName());
		    requestForm.setUserName(customerProfile.getUserName());
		    requestForm.setPhoneNumber(customerProfile.getPhoneNumber());
		    requestForm.setFirstName(customerProfile.getFirstName());
		    requestForm.setLastName(customerProfile.getLastName());
		    requestForm.setEmail(customerProfile.getEmail());		     
		}
		return requestForm;
	}
	
	protected ModelAndView showForm(final HttpServletRequest request,
			final HttpServletResponse response, final BindException errors,
			final Map controlModel) throws Exception {
		logger.debug("ViewAccountController - In showForm()");

		return super.showForm(request, response, errors, controlModel);

	}
	
	public ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception {

		final EnrollCustomerFormBean formBean = (EnrollCustomerFormBean) command;

		// call login
		try {
			ProfileBusinessDomainService businessService = LookupBusinessService
					.getProfileBusinessDomainService();

			CustomerProfile customerProfile = 
					businessService.updateCustomer(formBean);
						
			HttpSession session = request.getSession();
			
			//set cookie
	 		CookieUtils.setProfileCookie(request, response, customerProfile);
	 		
	 		//set session
			session.setAttribute(CUSTOMER_PROFILE, customerProfile);
		} 
		catch (DuplicatedUserNameException se) {
			final ModelMap modelMap = new ModelMap();
			errors.reject("error.username.duplicated");			 
			return super.showForm(request, response, errors, modelMap);
		}
		catch (AppRemoteException ae) {
			final ModelMap modelMap = new ModelMap();
			errors.reject("error.generic.msg");			 
			return super.showForm(request, response, errors, modelMap);
		}
        catch (Throwable te) {
			te.printStackTrace();
			logger.debug("Unknown error code");        	
        }
		
		//final String targetURL = getSuccessURL(request);
		//String successView = getSuccessView();
		
		//String targetURL = getSuccessView();
		//return new ModelAndView(new RedirectView(targetURL));
        final ModelMap modelMap = new ModelMap();
        errors.reject("profile.update");
        return super.showForm(request, response, errors, modelMap);
		
	 }

}
