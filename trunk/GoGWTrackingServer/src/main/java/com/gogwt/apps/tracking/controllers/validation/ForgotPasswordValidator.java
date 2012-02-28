package com.gogwt.apps.tracking.controllers.validation;

import org.springframework.validation.Errors;

import com.gogwt.apps.tracking.formbean.PasswordFormBean;
import com.gogwt.apps.tracking.utils.StringUtils;

public class ForgotPasswordValidator extends BaseValidateAdapter {
	@Override
	public boolean supports(Class<?> clazz) {
	 	return PasswordFormBean.class.isAssignableFrom(clazz);
	}


	@Override
	public void validate(Object obj, Errors errors) {
		final PasswordFormBean formBean = (PasswordFormBean) obj;
		 
		
	}

}
