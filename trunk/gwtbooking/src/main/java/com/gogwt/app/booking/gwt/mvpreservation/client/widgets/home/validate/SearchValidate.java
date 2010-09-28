package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.validate;

import java.util.ArrayList;

import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.framework.arch.utils.StringUtils;
import com.gogwt.framework.arch.widgets.AbstractValidate;

public class SearchValidate extends AbstractValidate {
	
	
    /**
     * Validate search form
     * Note: use ArrayList is for quick response, per Google document.
     * @param formEntry
     * @return error list
     */
	public ArrayList<String> validate(SearchFormBean formBean) {
		
		this.resetError();
		
		if (!StringUtils.isSet(formBean.getLocation())) {
			this.addError("Please type in Destination field");			
		}
		
		return this.getErrorList();
	}

}
