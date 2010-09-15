package com.gogwt.framework.arch.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractValidate{
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
}
