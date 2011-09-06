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
import com.gogwt.apps.tracking.exceptions.InvalidPasswordException;
import com.gogwt.apps.tracking.exceptions.InvalidUserException;
import com.gogwt.apps.tracking.formbean.LoginFormBean;
import com.gogwt.apps.tracking.services.domain.LookupBusinessService;
import com.gogwt.apps.tracking.services.domain.ProfileBusinessDomainService;
import com.gogwt.apps.tracking.utils.StringUtils;

public class LoginController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(LoginController.class);

	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {
		final LoginFormBean loginRequest = new LoginFormBean();

		return loginRequest;
	}

	protected ModelAndView showForm(final HttpServletRequest request,
			final HttpServletResponse response, final BindException errors,
			final Map controlModel) throws Exception {
		logger.debug("LoginController - In showForm()");

		return super.showForm(request, response, errors, controlModel);

	}

	public ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception {
		
		HttpSession session = request.getSession();
		
		final LoginFormBean formBean = (LoginFormBean) command;

		// call login
		try {
			String userName = formBean.getUserName();
			if (StringUtils.isSet(userName)) {
				userName = userName.trim();
			}

			String password = formBean.getPassword();
			if (StringUtils.isSet(password)) {
				password = password.trim();
			}

			ProfileBusinessDomainService businessService = LookupBusinessService
					.getProfileBusinessDomainService();

			CustomerProfile customerProfile = businessService.retrieveCustomerProfileByUsername(formBean);
			
			//if password does not match
			if (customerProfile == null || !StringUtils.equalsIgnoreCase(customerProfile.getPassword(), password)) {
				final ModelMap modelMap = new ModelMap();
				errors.reject("error.invalid.login");
				return super.showForm(request, response, errors, modelMap);
			}
			
			//so far, success login
	 		session.setAttribute(CUSTOMER_PROFILE, customerProfile);
			
	 		String targetURL =  getSuccessURL(request);
	 		if (StringUtils.isSet(targetURL)) {
	 			return new ModelAndView(new RedirectView(targetURL)); 
	 		}
	 		
			String successView = getSuccessView();
			return new ModelAndView(new RedirectView(successView));

		} catch (InvalidUserException e) {
			final ModelMap modelMap = new ModelMap();
			errors.reject("error.invalid.username");
			return super.showForm(request, response, errors, modelMap);
			
		} catch (InvalidPasswordException e) {
			final ModelMap modelMap = new ModelMap();
			errors.reject("error.invalid.password");
			return super.showForm(request, response, errors, modelMap);
		}

		catch (Throwable se) {
			// todo: add generic error message
			se.printStackTrace();
			logger.debug("Unknown error code");
		}

		final ModelMap modelMap = new ModelMap();
		errors.reject("error.generic.msg");
		return super.showForm(request, response, errors, modelMap);
 	}
}
