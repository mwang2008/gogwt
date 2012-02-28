package com.gogwt.apps.tracking.controllers.validation;

import org.springframework.validation.Errors;

import com.gogwt.apps.tracking.formbean.PasswordFormBean;
import com.gogwt.apps.tracking.utils.StringUtils;

public class ChangePasswordValidator extends BaseValidateAdapter {
	@Override
	public boolean supports(Class<?> clazz) {
	 	return PasswordFormBean.class.isAssignableFrom(clazz);
	}


	@Override
	public void validate(Object obj, Errors errors) {
		final PasswordFormBean formBean = (PasswordFormBean) obj;

		 
		validateRequiredField(errors, "oldPass", formBean.getOldPass(), "label.old.password");
		validateRequiredField(errors, "newPass", formBean.getNewPass(), "label.new.password");
	 	validateRequiredField(errors, "newPassConfirm", formBean.getNewPassConfirm(), "label.new.confirmPassword");

	 	if (StringUtils.isSet(formBean.getNewPass()) && StringUtils.isSet(formBean.getNewPassConfirm())) {
	 		if (StringUtils.equals(formBean.getNewPass(), formBean.getNewPassConfirm())) {
	 		}
	 		else {
	 			errors.reject("error.password.notmatch");
	 		}
	 	}
	 	 
	}
}
