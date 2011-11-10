package com.gogwt.apps.tracking.controllers.validation;

import org.springframework.validation.Errors;

import com.gogwt.apps.tracking.formbean.UploadFormBean;

public class ImportValidator extends BaseValidateAdapter {

	public boolean supports(Class clazz) {
		return UploadFormBean.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		final UploadFormBean uploadFormBean = (UploadFormBean) obj;
		
		if (uploadFormBean.getFileData().getSize()==0) {
			errors.reject("error.required.fileUpload");
		}
		 
 	}
}
