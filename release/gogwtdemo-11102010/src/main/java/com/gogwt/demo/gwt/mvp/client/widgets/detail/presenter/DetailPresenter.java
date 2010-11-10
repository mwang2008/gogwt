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
package com.gogwt.demo.gwt.mvp.client.widgets.detail.presenter;

import com.gogwt.demo.gwt.mvp.client.common.FormBean;
import com.gogwt.demo.gwt.mvp.client.common.GWTSession;
import com.gogwt.demo.gwt.mvp.client.widgets.detail.view.DetailView;
import com.gogwt.demo.gwt.mvp.client.widgets.home.presenter.Presenter;
import com.gogwt.framework.arch.utils.ActionForward;
import com.google.gwt.user.client.ui.HasWidgets;

/**
 * <code><B>DetailPresenter<code><B>
 * 
 * <p/>
 */

public class DetailPresenter implements Presenter,  DetailView.Presenter{
private final DetailView  view; 
	
	
	public DetailPresenter(DetailView view) {
		this.view = view;
		this.view.setPresenter(this);	
	}


	public void go(HasWidgets container) {
	    container.clear();
	    container.add(view.asWidget());	
	    display();
	}
	 
	private void display() {
		FormBean formBean = GWTSession.getFormBean();
		
		view.display(formBean);
	}


	/**
	 * Called from viewImpl
	 */
	public void toHomePage() {
		ActionForward.forward("backToHome");	 		
	}

    /**
     * Called from viewImpl
     */
	public void toInfoPage() {
		ActionForward.forward("success");			
	}
	
    
}
