package com.gogwt.apps.tracking.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.gogwt.apps.tracking.formbean.ContactUsFormBean;
import com.gogwt.apps.tracking.services.domain.LookupBusinessService;
import com.gogwt.apps.tracking.services.domain.ProfileBusinessDomainService;
import com.gogwt.apps.tracking.utils.StringUtils;
import com.gogwt.apps.tracking.utils.ToStringUtils;

public class ContactUsController extends BaseAbstractController {
	private static Logger logger = Logger
			.getLogger(ContactUsController.class);

	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {

		final ContactUsFormBean requestForm = new ContactUsFormBean();

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
		
		final ContactUsFormBean formBean = (ContactUsFormBean) command;

		logger.info("contactUs " + ToStringUtils.toString(formBean));

		// validate
		if (!StringUtils.isSet(formBean.getName())) {
			errors.reject("unknowcode", "Please provide Your Name");
		}
	
		if (StringUtils.isSet(formBean.getComment())) {
			if (formBean.getComment().length() > 2000) {
				formBean.setComment(formBean.getComment().substring(0,2000));
			}			
		}
		
		final ModelMap modelMap = new ModelMap();
		
		if (errors.hasErrors()) {
			return super.showForm(request, response, errors, modelMap);
		}
		
		ProfileBusinessDomainService businessService = LookupBusinessService.getProfileBusinessDomainService();
		
		//send email
		businessService.sendCustomerComments(formBean);
		
		errors.reject("unknowcode", "Your message is sent to us, thanks ");
		return super.showForm(request, response, errors, modelMap);
	}
}
