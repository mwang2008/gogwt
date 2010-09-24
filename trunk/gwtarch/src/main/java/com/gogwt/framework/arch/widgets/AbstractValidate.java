package com.gogwt.framework.arch.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractValidate{
	private String EMAIL_REGEX = "^[_A-Za-z0-9-']+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+[A-Za-z0-9-]*(\\.[A-Za-z0-9-]+)*[A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)";
	
	protected ArrayList<String> errorList;

	//public abstract ArrayList<String> validate(T t);
		
	
	public AbstractValidate() {
		errorList = new ArrayList<String>();
	}
	public void resetError() {
		if (errorList != null) {
			errorList.clear();
		}
		else {
		  errorList = new ArrayList<String>();
		}
	}

	public ArrayList<String> getErrorList() {
		return errorList;
	}

	public void addError(final List<String> errors) {
		ensureErrorList();
		for (String error : errors) {
			errorList.add(error);
		}
	}

	public void addError(final String... errors) {
		addError(Arrays.asList(errors));
	}

	private void ensureErrorList() {
		if (errorList == null) {
			errorList = new ArrayList<String>();
		}
	}
	
	/**
	 * validate email address should have @ in it
	 * 
	 * @param errors
	 * @param fieldValue
	 */
	protected boolean isValidEmailFormat(final String fieldValue) {
 		if (fieldValue != null && !(fieldValue.matches(EMAIL_REGEX))) {
			return false;
		}
		return true;
	}
}
