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

import com.gogwt.demo.gwt.mvp.client.widgets.home.presenter.HomePresenter;
import com.gogwt.demo.gwt.mvp.client.widgets.home.view.HomeView;
import com.gogwt.demo.gwt.mvp.client.widgets.home.view.HomeViewImpl;
import com.gogwt.framework.arch.widgets.AbstractController;
import com.gogwt.framework.arch.widgets.PageMetaInfo;

/**
 * <code><B>HomePage<code><B>
 * 
 * <p/>
 */

public class HomeController extends AbstractController {
	private HomeView homeView;	 

	@Override
	public void process() {
		controlPanel.clear();
 		
		if (homeView == null) {
			homeView = new HomeViewImpl();
		}

		new HomePresenter(homeView).go(controlPanel);
	 
	}

	@Override
	protected void fillMetaInfo(PageMetaInfo pageInfo) {
		pageInfo.setTitle("Home");

	}

}
