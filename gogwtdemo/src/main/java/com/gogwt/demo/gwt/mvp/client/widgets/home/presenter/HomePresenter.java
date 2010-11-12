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
package com.gogwt.demo.gwt.mvp.client.widgets.home.presenter;

import com.gogwt.demo.gwt.mvp.client.common.FormBean;
import com.gogwt.demo.gwt.mvp.client.common.GWTSession;
import com.gogwt.demo.gwt.mvp.client.widgets.home.view.HomeView;
import com.gogwt.demo.gwt.mvp.client.widgets.home.view.HomeViewImpl;
import com.gogwt.framework.arch.utils.ActionForward;
import com.google.gwt.user.client.ui.HasWidgets;

/**
 * <code><B>HomePresenter<code><B>
 * 
 * <p/>
 */
 
public class HomePresenter implements Presenter, HomeView.Presenter<FormBean> {
	private final HomeView<FormBean> view; 
	
	
	public HomePresenter(HomeView<FormBean> view) {
		this.view = view;
		this.view.setPresenter(this);	
	}


	/**
	 * First time goes here
	 */
	public void go(HasWidgets container) {
	    container.clear();
	    container.add(view.asWidget());		    
	}


	/**
	 * button action
	 */
	public void toDetail() {
		//1. get form value; todo: need to find a better way to avoid cast. 
		FormBean formBean = ((HomeViewImpl)view).toValue();
		
		GWTSession.setFormBean(formBean);
		
		//2. call RPC etc
		
		//3. forward to detail page
		ActionForward.forward("success");	
		
	}

}
