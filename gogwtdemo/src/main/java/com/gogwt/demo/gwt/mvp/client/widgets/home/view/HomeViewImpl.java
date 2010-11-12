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

package com.gogwt.demo.gwt.mvp.client.widgets.home.view;

import com.gogwt.demo.gwt.mvp.client.common.FormBean;
import com.gogwt.framework.arch.widgets.AbstractFormComposite;
import com.gogwt.framework.arch.widgets.FormBindingManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
 

//public class HomeViewImpl extends Composite implements HomeView<FormBean> {
public class HomeViewImpl extends AbstractFormComposite<FormBean> implements HomeView<FormBean> {
	
	@UiTemplate("HomeView.ui.xml")
	interface HomeViewUiBinder extends UiBinder<Widget, HomeViewImpl> {
	}

	private static HomeViewUiBinder uiBinder = GWT
			.create(HomeViewUiBinder.class);

	private Presenter<FormBean> presenter;
	
	@UiField Label pageDescLabel;
	@UiField Label welcomeLabel;
	@UiField Button toDetailBtn;
	@UiField TextBox detail;
	
	public HomeViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
 	
		pageDescLabel.setText("in Home page.");		
		welcomeLabel.setText("Welcome to Demo Page");
		toDetailBtn.setText("To Detail");
	}

	@UiHandler("toDetailBtn")
	void onToDetailClicked(ClickEvent event) {
         if (presenter != null) {
        	 presenter.toDetail();
         }         
	}
	
	 
	public void setPresenter(
			com.gogwt.demo.gwt.mvp.client.widgets.home.view.HomeView.Presenter<FormBean> presenter) {
		this.presenter = presenter;
	}
 
	public Widget asWidget() {
		return this;
	}
 

/*	public FormBean toValue() {
		FormBean formBean = new FormBean();
		formBean.setDetail(detail.getText());
		
		return formBean;
	}

	public void fromValue(FormBean t) {
		// TODO Auto-generated method stub
		
	}*/

	@Override
	protected FormBindingManager<FormBean> obtainFromBindingManager() {
		return GWT.create(HomeViewImpl.class);
	}	
}
