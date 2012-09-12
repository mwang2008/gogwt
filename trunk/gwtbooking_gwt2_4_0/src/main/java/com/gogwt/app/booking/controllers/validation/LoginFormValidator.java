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
package com.gogwt.app.booking.controllers.validation;

import org.springframework.validation.Errors;

import com.gogwt.app.booking.dto.dataObjects.request.LoginFormBean;

/**
 * <code><B>LoginFormValidator<code><B>
 * 
 * <p/>
 */

public class LoginFormValidator extends BaseValidateAdapter {

	public boolean supports(Class clazz) {
		return LoginFormBean.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		final LoginFormBean loginFormBean = (LoginFormBean) obj;

		validateRequiredField(errors, "userName", loginFormBean.getUserName(), "label.userName");
		validateRequiredField(errors, "password", loginFormBean.getPassword(), "Label.password");
	 
		
	}

}
