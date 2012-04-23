package com.gogwt.apps.tracking.controllers.validation;

import org.springframework.validation.Errors;

import com.gogwt.apps.tracking.formbean.C2DMSendMessageFormBean;

public class C2DMSendMessageValidator extends BaseValidateAdapter {
	@Override
	public boolean supports(Class<?> clazz) {
	 	return C2DMSendMessageFormBean.class.isAssignableFrom(clazz);
	}


	@Override
	public void validate(Object obj, Errors errors) {
		final C2DMSendMessageFormBean formBean = (C2DMSendMessageFormBean) obj;

		validateRequiredField(errors, "message", formBean.getMessage(), "label.Message"); 
		 
	 	if (formBean.getRecipientList() == null || formBean.getRecipientList().isEmpty() ) {
 			errors.reject("error.c2dm.empty.selection");
	 	}
	 	
	 	if (formBean.getMessage() != null && formBean.getMessage().length()>300) {
	 		errors.reject("error.c2dm.message.too.long");
	 	}
	 	 
	}

}
