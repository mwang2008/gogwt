package com.gogwt.apps.tracking.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.exceptions.AppRemoteException;
import com.gogwt.apps.tracking.exceptions.InvalidUserException;
import com.gogwt.apps.tracking.formbean.PasswordFormBean;
import com.gogwt.apps.tracking.services.domain.LookupBusinessService;
import com.gogwt.apps.tracking.services.domain.ProfileBusinessDomainService;
import com.gogwt.apps.tracking.utils.StringUtils;
import com.gogwt.apps.tracking.utils.ToStringUtils;

public class ForgotPasswordController extends BaseAbstractController {
	private static Logger logger = Logger
			.getLogger(ForgotPasswordController.class);

	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {

		final PasswordFormBean requestForm = new PasswordFormBean();

		return requestForm;
	}

	protected ModelAndView showForm(final HttpServletRequest request,
			final HttpServletResponse response, final BindException errors,
			final Map controlModel) throws Exception {
		logger.debug("ChangePasswordController - In showForm()");

		return super.showForm(request, response, errors, controlModel);

	}

	public ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception {

		String action = request.getParameter("action");
		if (StringUtils.equalsIgnoreCase(action, "forgetpassword")) {
			return submitForgotPassword(request, response, command, errors);
		}

		final ModelMap modelMap = new ModelMap();
		return super.showForm(request, response, errors, modelMap);

	}

	private ModelAndView submitForgotPassword(final HttpServletRequest request,
			final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception {
		final PasswordFormBean formBean = (PasswordFormBean) command;

		logger.info("forgetpassword " + ToStringUtils.toString(formBean));

		// validate
		if (!StringUtils.isSet(formBean.getGroupId())) {
			errors.reject("unknowcode", "Please provide Group Id");
		}
		if (!StringUtils.isSet(formBean.getUserName())) {
			errors.reject("unknowcode", "Please provide User Name");
		}
		if (!StringUtils.isSet(formBean.getEmail())) {
			errors.reject("unknowcode", "Please provide Email Address");
		}

		final ModelMap modelMap = new ModelMap();
		if (errors.hasErrors()) {
			return super.showForm(request, response, errors, modelMap);
		}

		ProfileBusinessDomainService businessService = LookupBusinessService.getProfileBusinessDomainService();
		
		CustomerProfile customerProfile = null;
		try {
		   customerProfile = businessService.retrieveCustomerProfileByUsername(formBean.getGroupId(), formBean.getUserName());
		   if (customerProfile == null) {
			   errors.reject("unknowcode", "Could not find the profile with your Group ID and User Name");
		   }		   
		   else if (!StringUtils.equalsIgnoreCase(formBean.getEmail(), customerProfile.getEmail())) {
			   errors.reject("unknowcode", "Could not find your email");
		   }
		}
		catch (AppRemoteException e) {
		   //invalid 
		   errors.reject("unknowcode", "Could not find the profile with your Group ID and User Name");
		}
		
		if (errors.hasErrors()) {
			return super.showForm(request, response, errors, modelMap);
		}
		
		try {
			businessService.forgotPassword(formBean, customerProfile);
			errors.reject("unknowcode", "The new password is sent to  " + formBean.getEmail());
			return super.showForm(request, response, errors, modelMap);

		} catch (InvalidUserException e) {
			errors.reject("unknowcode", "invalid Username and/or GroupId: ");
		}
		
		return super.showForm(request, response, errors, modelMap);
	}
}
