package com.gogwt.apps.tracking.controllers;

import static com.gogwt.apps.tracking.AppConstants.ENV;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.gogwt.apps.tracking.c2md.AuthenticationUtil;
import com.gogwt.apps.tracking.c2md.C2DMResponse;
import com.gogwt.apps.tracking.c2md.MessageUtil;
import com.gogwt.apps.tracking.c2md.SecureStorage;
import com.gogwt.apps.tracking.config.interceptor.UrlMappingElem;
import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.LoginStatus;
import com.gogwt.apps.tracking.exceptions.AppRemoteException;
import com.gogwt.apps.tracking.formbean.C2DMRegisterBean;
import com.gogwt.apps.tracking.formbean.C2DMSendMessageFormBean;
import com.gogwt.apps.tracking.services.domain.C2DMBusinessDomainService;
import com.gogwt.apps.tracking.services.domain.LookupBusinessService;
import com.gogwt.apps.tracking.utils.StringUtils;

public class C2DMRegisterListController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(C2DMRegisterListController.class);


	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {
		final C2DMSendMessageFormBean requestBean = new C2DMSendMessageFormBean();

		return requestBean;
	}

	protected ModelAndView showForm(final HttpServletRequest request,
			final HttpServletResponse response, final BindException errors,
			final Map controlModel) throws Exception {
		
		logger.debug("LoginController - In showForm()");
		final UrlMappingElem env = (UrlMappingElem)request.getAttribute(ENV);
		HttpSession session = request.getSession();
		CustomerProfile customerProfile = env.getCustomerProfile(); //(CustomerProfile)session.getAttribute(CUSTOMER_PROFILE);
		
		//if implicit, require login
		if (!(customerProfile.getStatus() == LoginStatus.EXPLICIT)) {
			//redirect to login page
			String targetURL = env.getPrefix() + "/login?from=viewaccount&successURL=" + env.getPrefix()+"/viewaccount";
			return new ModelAndView(new RedirectView(targetURL));
	 	}
		
		final ModelMap modelMap = new ModelMap();
		List<C2DMRegisterBean> regList = getC2DMRegisterList(request, customerProfile.getGroupId());
		//put in session for onSubmit
		session.setAttribute("c2dm.registerlist", regList);
		
		if (regList != null && !regList.isEmpty()) {
		   modelMap.addAttribute( "hasResult", true );
		   modelMap.addAttribute( "c2dmRegList", regList );
		}
		else {
		   modelMap.addAttribute( "hasResult", false );
		}
		
		return super.showForm(request, response, errors, controlModel).addAllObjects(modelMap);

	}
	
	public ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception {
		
		final C2DMSendMessageFormBean formBean = (C2DMSendMessageFormBean) command;
		HttpSession session = request.getSession();
		
		//send c2dm message
		String token = AuthenticationUtil.getToken(SecureStorage.USER, SecureStorage.PASSWORD);
		
		List<C2DMRegisterBean> regList = (List<C2DMRegisterBean>)session.getAttribute("c2dm.registerlist");
		if (regList == null) {
			//retrieve again
			final UrlMappingElem env = (UrlMappingElem)request.getAttribute(ENV);			 
			CustomerProfile customerProfile = env.getCustomerProfile(); 		
			regList = getC2DMRegisterList(request, customerProfile.getGroupId());
		}
		
		String regId = null;
		C2DMResponse responseCode = null;
		for (String phone : formBean.getRecipientList()) {
			regId = getRegistrationId(regList, phone);
			if (regId != null) {
				try {
		           responseCode = MessageUtil.sendMessage(token, regId, phone, formBean.getMessage());
				}
				catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
		
		if (responseCode != null && responseCode.getResponseCode() == 200) {
		   final ModelMap modelMap = new ModelMap();
		   errors.reject("c2dm.success.send");
		   return this.showForm(request, response, errors, modelMap);
		}
		else {
		   final ModelMap modelMap = new ModelMap();		
		   errors.reject("c2dm.wrong.send");
 		   return this.showForm(request, response, errors, modelMap);
		}
	}
	
	private String getRegistrationId(List<C2DMRegisterBean> regList, String phone) {
		if (regList == null || regList.isEmpty()) {
			return null;
		}
		
		for (C2DMRegisterBean regBean : regList) {
			if (regBean.getPhone().equalsIgnoreCase(phone)) {
				return regBean.getRegistrationid();
			}
		}
		return null;
	}
	
	protected ModelAndView handleRequestInternalXXXXX(
			final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("handleRequestInternal");

		final UrlMappingElem env = (UrlMappingElem)request.getAttribute(ENV);
		HttpSession session = request.getSession();
		CustomerProfile customerProfile = env.getCustomerProfile(); //(CustomerProfile)session.getAttribute(CUSTOMER_PROFILE);
		
		//if implicit, require login
		if (!(customerProfile.getStatus() == LoginStatus.EXPLICIT)) {
			//redirect to login page
			String targetURL = env.getPrefix() + "/login?from=viewaccount&successURL=" + env.getPrefix()+"/viewaccount";
			return new ModelAndView(new RedirectView(targetURL));
			//response.sendRedirect(targetURL);
		}

		final ModelMap modelMap = new ModelMap();
		/*
		C2DMBusinessDomainService service = LookupBusinessService.getC2DMBusinessDomainService();
		List<C2DMRegisterBean> regList;
		try {
		   regList = service.getRegisterListByGroupId(customerProfile.getGroupId());
		   modelMap.addAttribute( "hasResult", true );
		   modelMap.addAttribute( "c2dmRegList", regList );
		}
		catch (AppRemoteException e) {
			e.printStackTrace();
			modelMap.addAttribute( "hasResult", false );
		}
		*/
		List<C2DMRegisterBean> regList = getC2DMRegisterList(request, customerProfile.getGroupId());
		
		String formView = this.getFormView();
		if (StringUtils.isSet(formView)) {
			return new ModelAndView(formView).addAllObjects(modelMap);
		}
	    
        //otherwise, default
		return new ModelAndView("/tracking/c2dm_user_list").addAllObjects(modelMap);
	}


	private List<C2DMRegisterBean> getC2DMRegisterList(final HttpServletRequest request, String goupdId) {
		 

		final ModelMap modelMap = new ModelMap();
		C2DMBusinessDomainService service = LookupBusinessService.getC2DMBusinessDomainService();
		List<C2DMRegisterBean> regList = null;
		try {
		   regList = service.getRegisterListByGroupId(goupdId);
		}
		catch (AppRemoteException e) {
			e.printStackTrace();			
		}
		
		
		return regList;
	}
}
