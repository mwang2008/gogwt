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
import com.gogwt.app.booking.dto.dataObjects.request.LoginFormBean;
import com.gogwt.app.booking.exceptions.clientserver.InvalidPasswordException;
import com.gogwt.app.booking.exceptions.clientserver.InvalidUserException;
import com.gogwt.framework.arch.utils.StringUtils;

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

			ProfileBusinessService businessService = LookupBusinessService
					.getProfileBusinessService();

			CustomerProfile customerProfile;

			customerProfile = businessService
					.retrieveCustomerProfileByUsername(formBean);

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

		final String targetURL = getSuccessURL(request);
		String successView = getSuccessView();
		/*
		 * By default show success view
		 */
		if (StringUtils.isSet(targetURL)) {
			return new ModelAndView(new RedirectView(targetURL));
		} else {
			return new ModelAndView(new RedirectView(successView));
		}
	}

}
