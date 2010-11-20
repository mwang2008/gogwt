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
package com.gogwt.demo.gwt.mvp.client.navigation;

import com.gogwt.demo.gwt.mvp.client.widgets.detail.presenter.DetailPresenter;
import com.gogwt.demo.gwt.mvp.client.widgets.detail.view.DetailView;
import com.gogwt.demo.gwt.mvp.client.widgets.detail.view.DetailViewImpl;
import com.gogwt.framework.arch.utils.StringUtils;
import com.gogwt.framework.arch.widgets.AbstractController;
import com.gogwt.framework.arch.widgets.PageMetaInfo;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.HTML;

/**
 * <code><B>DetailPage<code><B>
 * 
 * <p/>
 */

public class DetailController extends AbstractController {
	private DetailView view;	 

	@Override
	public void process() {
		controlPanel.clear();
 		
		if (view == null) {
			view = new DetailViewImpl();
		}

		new DetailPresenter(view).go(controlPanel);
	} 

	@Override
	protected void fillMetaInfo(PageMetaInfo pageInfo) {
		//todo: those values can be from resource bundle. 
		pageInfo.setTitle("GoGWT demo detail");
		pageInfo.setDescription("GoGWT detail description");
		pageInfo.setKeywords("GWT, detail, demo");
		
		pageInfo.addMetaMap("robots", "NOODP, NOYDIR");
		pageInfo.addMetaMap("currentToken", this.getCurrentToken());		
	}

	@Override
	public void postProcess() {
		//add crwal section
		controlPanel.add(new HTML(ControllerHelper.constructSEOURL()));
	}
	
	
}
