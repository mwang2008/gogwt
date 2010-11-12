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
package com.gogwt.framework.arch.utils;

import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;

/**
 * <code><B>GWTValueUtils<code><B>
 * 
 * <p/>
 */

public class GWTParamValueUtils {

	/**
	 * <p>
	 * The following setValue(a1,a2) methods are used for FormBindingInterfaceGenerator 
	 * annotation based widgets
	 * </p>
	 * 
	 * @param w
	 * @param value
	 */
	public static void setValue(TextBox w, String value) {
		if (StringUtils.isSet(value)) {
			w.setText(value);
		}
	}
	public static String getValue(TextBox w) {
		return (w != null) ? w.getText() : null;
	}
	
	public static void setValue(PasswordTextBox w, String value) {
		if (StringUtils.isSet(value)) {
			w.setText(value);
		}
	}
	public static String getValue(PasswordTextBox w) {
		return (w != null) ? w.getText() : null;
	}
	
/*	public static void setValue(CheckBox w, List<String> value) {
		if (value != null) {
			w.setValue(value);
			 
		}
	}

	public static void setValue(ListBox w, String value) {
		if (StringUtils.isSet(value)) {
			w.setSelectedValue(value);
		}
	}*/

	public static void setValue(SuggestBox w, String value) {
		if (StringUtils.isSet(value)) {
			w.setText(value);
		}
	}
	public static String getValue(SuggestBox w) {
		return (w != null) ? w.getText() : null;
	}
	 
	
	public static void setValue(Hidden w, String value) {
		if (StringUtils.isSet(value)) {
			w.setValue(value);
		}
	}
	public static String getValue(Hidden w) {
		return (w != null) ? w.getValue() : null;
	}
}
