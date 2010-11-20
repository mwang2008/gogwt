package com.gogwt.demo.gwt.navigation.client.widgets.home;


import com.gogwt.demo.gwt.navigation.client.common.GWTSession;
import com.gogwt.demo.gwt.navigation.client.dataobject.FormBean;
import com.gogwt.framework.arch.utils.ActionForward;
import com.gogwt.framework.arch.widgets.AbstractFormComposite;
import com.gogwt.framework.arch.widgets.FormBindingManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HomeWidget extends AbstractFormComposite<FormBean> {
	private Panel layoutPanel = new VerticalPanel();

	final TextBox detail = new TextBox();
	
	public HomeWidget() {
		super();
		initWidget(layoutPanel);
	}
	
	public void display() {
		layoutPanel.add(new HTML(" <b>in Home page.</b> "));
		
		Button toDetailBtn = new Button("To Detail");
		
		
		
		toDetailBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.runAsync(new RunAsyncCallback() {
			          public void onFailure(Throwable caught) {
			            Window.alert("Code download failed");
			          }

			          public void onSuccess() {
							//String detail = detailText.getText();
							FormBean formBean = toValue();
							GWTSession.setDetail(formBean.getDetail());
							 
							ActionForward.forward("success");	
			          }
			        });
 			}			
		});
		
		layoutPanel.add(new Label("Welcome to Demo Page"));
		layoutPanel.add(detail);
		layoutPanel.add(toDetailBtn);
	}

	@Override
	protected FormBindingManager<FormBean> obtainFormBindingManager() {
		return GWT.create(HomeWidget.class);
	}
}
