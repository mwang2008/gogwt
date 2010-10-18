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

package com.gogwt.demo.gwt.navigation.client.widgets.detail;

import com.gogwt.framework.arch.utils.ActionForward;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DetailWidget extends Composite {
   private Panel layoutPanel = new VerticalPanel();
	
	public DetailWidget() {
		super();		 
		initWidget(layoutPanel);
	}
	
	public void display() {
		//add label
		layoutPanel.add(new HTML(" <b>in Detail page.</b> "));
		
		//add home link
		Anchor toHome = new Anchor("Back To home ");
		toHome.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				ActionForward.forward("backToHome");				
			}			
		});
		
		layoutPanel.add(toHome);
		
		layoutPanel.add(new HTML("<br>"));
		
		//add info
		Button toInfo = new Button("To Info Page");
		toInfo.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				ActionForward.forward("success");				
			}
			
		});
		layoutPanel.add(toInfo);
		
	}
}
