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

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public abstract class AbstractRequestWidget extends Composite {
	public @UiField HTML errorMessage;
	
	public void dispErrorMsg(ArrayList<String> msgs) {
		StringBuilder sb = new StringBuilder();		
		if (msgs != null && !msgs.isEmpty()) {
			for (String msg : msgs) {
				sb.append("<li>" + msg + "</li>");
 			}
		}			 	
		errorMessage.setHTML(sb.toString());
	}
}