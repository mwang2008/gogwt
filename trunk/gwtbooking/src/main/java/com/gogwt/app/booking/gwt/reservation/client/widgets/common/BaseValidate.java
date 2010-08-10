package com.gogwt.app.booking.gwt.reservation.client.widgets.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseValidate {
	protected ArrayList<String> errorList;

	public BaseValidate() {
		errorList = new ArrayList<String>();
	}

	public void resetError() {
		errorList = new ArrayList<String>();
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
