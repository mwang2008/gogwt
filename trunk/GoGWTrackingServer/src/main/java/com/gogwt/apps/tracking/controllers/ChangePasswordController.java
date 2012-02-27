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

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.exceptions.AppRemoteException;
import com.gogwt.apps.tracking.exceptions.DuplicatedUserNameException;
import com.gogwt.apps.tracking.formbean.EnrollCustomerFormBean;
import com.gogwt.apps.tracking.formbean.PasswordFormBean;
import com.gogwt.apps.tracking.services.domain.LookupBusinessService;
import com.gogwt.apps.tracking.services.domain.ProfileBusinessDomainService;
import com.gogwt.apps.tracking.utils.CookieUtils;
import com.gogwt.apps.tracking.utils.StringUtils;

public class ChangePasswordController extends BaseAbstractController {
	private static Logger logger = Logger
			.getLogger(ChangePasswordController.class);

	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		CustomerProfile customerProfile = (CustomerProfile) session
				.getAttribute(CUSTOMER_PROFILE);
		
		final PasswordFormBean requestForm = new PasswordFormBean();
		if (customerProfile != null) {
			requestForm.setCustomerId(customerProfile.getId());
			requestForm.setOldPass(customerProfile.getPassword());
			requestForm.setGroupId(customerProfile.getGroupId());
			requestForm.setUserName(customerProfile.getUserName());			
		}
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

		final PasswordFormBean formBean = (PasswordFormBean) command;

		// call login
		try {
			HttpSession session = request.getSession();
			CustomerProfile customerProfile = (CustomerProfile) session.getAttribute(CUSTOMER_PROFILE);
			
			if (!StringUtils.equals(formBean.getOldPass(), customerProfile.getPassword())) {
				final ModelMap modelMap = new ModelMap();
				errors.reject("error.oldpassword.wrong");
				return super.showForm(request, response, errors, modelMap);
			}
			
			ProfileBusinessDomainService businessService = LookupBusinessService
					.getProfileBusinessDomainService();

			customerProfile = businessService
					.changePassword(formBean);
			
			// set cookie
			CookieUtils.setProfileCookie(request, response, customerProfile);

			// set session
			session.setAttribute(CUSTOMER_PROFILE, customerProfile);
		} catch (DuplicatedUserNameException se) {
			final ModelMap modelMap = new ModelMap();
			errors.reject("error.username.duplicated");
			return super.showForm(request, response, errors, modelMap);
		} catch (AppRemoteException ae) {
			final ModelMap modelMap = new ModelMap();
			errors.reject("error.generic.msg");
			return super.showForm(request, response, errors, modelMap);
		} catch (Throwable te) {
			te.printStackTrace();
			logger.debug("Unknown error code");
			final ModelMap modelMap = new ModelMap();
			errors.reject("error.generic.msg");
			return super.showForm(request, response, errors, modelMap);
		}

		// final String targetURL = getSuccessURL(request);
		// String successView = getSuccessView();

		// String targetURL = getSuccessView();
		// return new ModelAndView(new RedirectView(targetURL));
		final ModelMap modelMap = new ModelMap();
		errors.reject("profile.changed.password");
		return super.showForm(request, response, errors, modelMap);

	}

}
