/*
 * Copyright 2010 GoGWT.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gogwt.framework.arch.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractValidate{
	private String EMAIL_REGEX = "^[_A-Za-z0-9-']+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]*(\\.[A-Za-z0-9-]+)*[A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)";
	
	protected ArrayList<String> errorList;

 	
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
