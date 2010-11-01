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
import com.gogwt.framework.arch.widgets.AbstractPage;
import com.gogwt.framework.arch.widgets.PageMetaInfo;

/**
 * <code><B>DetailPage<code><B>
 * 
 * <p/>
 */

public class DetailPage extends AbstractPage {
	private DetailView view;	 

	@Override
	public void process() {
		pagePanel.clear();
 		
		if (view == null) {
			view = new DetailViewImpl();
		}

		new DetailPresenter(view).go(pagePanel);
	} 

	@Override
	protected void fillMetaInfo(PageMetaInfo pageInfo) {
		pageInfo.setTitle("Detail");
		
	}

}
