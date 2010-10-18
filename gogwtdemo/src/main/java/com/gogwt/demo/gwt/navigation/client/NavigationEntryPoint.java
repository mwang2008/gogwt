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

package com.gogwt.demo.gwt.navigation.client;

import com.gogwt.framework.arch.navigation.AbstractEntryPoint;
import com.gogwt.framework.arch.navigation.AbstractPageConfigAccessor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class NavigationEntryPoint extends AbstractEntryPoint {

	@Override
	protected void loadModule() {
		RootPanel.get("header").add(new HTML("  Common  Header <hr> "));
		
		addPageManagerToRootPanel("wrapperContent");
		
		RootPanel.get("footer").add(new HTML("   <hr> Common Footer  "));
	}

	@Override
	protected AbstractPageConfigAccessor obtainPageAccessor() {
		return GWT.create(NavigationProcessConfig.class);	
	}
}
