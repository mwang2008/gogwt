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

package com.gogwt.demo.gwt.navigation.client.navigation;

import com.gogwt.demo.gwt.navigation.client.i18n.TagNavigationResources;
import com.gogwt.framework.arch.utils.ActionForward;
import com.gogwt.framework.arch.widgets.AbstractPage;
import com.gogwt.framework.arch.widgets.PageMetaInfo;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;


public class HomeView extends AbstractPage {

	 	
	@Override
	public void process() {
		pagePanel.clear();
		
		this.pagePanel.add(new HTML(" <b>in Home page.</b> "));
		
		Button toDetail = new Button("To Detail");
		toDetail.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				ActionForward.forward("success");				
			}			
		});
		
		this.pagePanel.add(new Label("Welcome to Demo Page"));
		this.pagePanel.add(toDetail);	
		
	}

	@Override
	public void fillMetaInfo(PageMetaInfo pageInfo) {
		 
		//todo: those values can be from resource bundle. 
		pageInfo.setTitle("GoGWT demo home");
		pageInfo.setDescription("GoGWT home description");
		pageInfo.setKeywords("GWT, home, demo");
		
		pageInfo.addMetaMap("robots", "NOODP, NOYDIR");
		pageInfo.addMetaMap("currentToken", this.getCurrentToken());
		
	}
}
