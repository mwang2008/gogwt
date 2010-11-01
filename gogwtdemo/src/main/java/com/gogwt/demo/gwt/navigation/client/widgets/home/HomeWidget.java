package com.gogwt.demo.gwt.navigation.client.widgets.home;

import com.gogwt.demo.gwt.navigation.client.common.GWTSession;
import com.gogwt.framework.arch.utils.ActionForward;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HomeWidget extends Composite {
	private Panel layoutPanel = new VerticalPanel();

	public HomeWidget() {
		super();
		initWidget(layoutPanel);
	}
	
	public void display() {
		layoutPanel.add(new HTML(" <b>in Home page.</b> "));
		
		Button toDetailBtn = new Button("To Detail");
		
		final TextBox detailText = new TextBox();
		
		toDetailBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				String detail = detailText.getText();
				GWTSession.setDetail(detail);
				ActionForward.forward("success");				
			}			
		});
		
		layoutPanel.add(new Label("Welcome to Demo Page"));
		layoutPanel.add(detailText);
		layoutPanel.add(toDetailBtn);
	}
}
