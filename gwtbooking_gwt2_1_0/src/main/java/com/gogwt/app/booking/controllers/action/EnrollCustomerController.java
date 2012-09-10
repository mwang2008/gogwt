package com.gogwt.app.booking.controllers.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.gogwt.app.booking.businessService.domainService.LookupBusinessService;
import com.gogwt.app.booking.businessService.domainService.ProfileBusinessService;
import com.gogwt.app.booking.controllers.BaseAbstractController;
import com.gogwt.app.booking.dto.dataObjects.common.CustomerProfile;
import com.gogwt.app.booking.dto.dataObjects.request.EnrollCustomerFormBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;
import com.gogwt.app.booking.exceptions.clientserver.DuplicatedUserNameException;

public class EnrollCustomerController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(GuestInfoController.class);
	
	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {
		final EnrollCustomerFormBean requestForm = new EnrollCustomerFormBean();

		return requestForm;
	}

	protected ModelAndView showForm(final HttpServletRequest request,
			final HttpServletResponse response, final BindException errors,
			final Map controlModel) throws Exception {
		logger.debug("EnrollCustomerController - In showForm()");

		return super.showForm(request, response, errors, controlModel);

	}

	public ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception {

		final EnrollCustomerFormBean formBean = (EnrollCustomerFormBean) command;

		// call login
		try {
			 

			ProfileBusinessService businessService = LookupBusinessService
					.getProfileBusinessService();

			CustomerProfile customerProfile = businessService.enrollCustomer(formBean);
			
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
		
		String targetURL = getSuccessView();
		return new ModelAndView(new RedirectView(targetURL));
		
	 }
}
