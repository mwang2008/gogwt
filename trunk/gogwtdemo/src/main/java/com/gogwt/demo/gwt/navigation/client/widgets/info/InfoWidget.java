package com.gogwt.demo.gwt.navigation.client.widgets.info;

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

public class InfoWidget extends Composite {
    private Panel layoutPanel = new VerticalPanel();
	
	public InfoWidget() {
		super();		 
		initWidget(layoutPanel);
	}
	
	public void display() {
		layoutPanel.add(new HTML(" <b>in Info page.</b> "));
		
		layoutPanel.add(new Label("Global forward: NavigationProcessConfig.xml"));
		
		Anchor toHome = new Anchor("Global forward: Back To home");
		toHome.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				ActionForward.forward("gToHome");				
			}			
		});
		
		layoutPanel.add(toHome);
		
		Button toDetail = new Button("Global forwrd: Back To Detail");
		toDetail.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				ActionForward.forward("gToDetail");				
			}			
		});
		
		layoutPanel.add(toDetail);
	}
}
