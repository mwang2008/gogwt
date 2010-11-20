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
package com.gogwt.demo.gwt.mvp.client.widgets.detail.view;

import com.gogwt.demo.gwt.mvp.client.dataobject.FormBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * <code><B>DetailViewImpl<code><B>
 * 
 * <p/>
 */

public class DetailViewImpl extends Composite implements DetailView {
	
	@UiTemplate("DetailView.ui.xml")
	interface DetailViewUiBinder extends UiBinder<Widget, DetailViewImpl> {
	}

	private static DetailViewUiBinder uiBinder = GWT
			.create(DetailViewUiBinder.class);
	
	private Presenter presenter;
	
	@UiField Label userInputText;
	@UiField HTML backToHome;
	@UiField Button toInfoBtn;
	
	public DetailViewImpl() {
		initWidget(uiBinder.createAndBindUi(this)); 
		toInfoBtn.setText("To Info Page");
		backToHome.setHTML("<a href=\"javascript:\"> Back To Home </a>");
	}
	
	public void setPresenter(
			com.gogwt.demo.gwt.mvp.client.widgets.detail.view.DetailView.Presenter presenter) {
		this.presenter = presenter;		
	}

	public Widget asWidget() {
		return this;
	}


	public void display(FormBean formBean) {
	    if (formBean != null) {
		   userInputText.setText(formBean.toString());
	    }
	    else {
	    	userInputText.setText(" please go to home page and try again");
	    }
				
	}
	
	@UiHandler("backToHome")
	void onBackToHomeClicked(ClickEvent event) {
		presenter.toHomePage();
	}
	 
	@UiHandler("toInfoBtn")
	void onToInfoButtonClicked(ClickEvent event) {
		presenter.toInfoPage(); 
	}

}
