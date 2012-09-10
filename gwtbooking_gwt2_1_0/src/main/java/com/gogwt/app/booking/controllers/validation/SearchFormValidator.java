package com.gogwt.app.booking.controllers.validation;

import org.springframework.validation.Errors;

import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.framework.arch.utils.StringUtils;

public class SearchFormValidator extends BaseValidateAdapter {

	
	public boolean supports(Class clazz) {
		return SearchFormBean.class.isAssignableFrom(clazz);
	}


	public void validate(Object obj, Errors errors) {
		final SearchFormBean searchFormBean = (SearchFormBean) obj;

		if (!StringUtils.isSet(searchFormBean.getLocation())) {
			errors.reject("error.empty.location");
		}
		
		 

	}

}
