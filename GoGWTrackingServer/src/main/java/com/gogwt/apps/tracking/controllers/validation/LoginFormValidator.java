package com.gogwt.apps.tracking.controllers.validation;

import org.springframework.validation.Errors;

import com.gogwt.apps.tracking.formbean.LoginFormBean;

public class LoginFormValidator extends BaseValidateAdapter {

	public boolean supports(Class clazz) {
		return LoginFormBean.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		final LoginFormBean loginFormBean = (LoginFormBean) obj;
		
		validateRequiredField(errors, "groupId", loginFormBean.getUserName(), "label.GroupId");
		validateRequiredField(errors, "userName", loginFormBean.getUserName(), "label.userName");
		validateRequiredField(errors, "password", loginFormBean.getPassword(), "Label.password");
 	}

}
