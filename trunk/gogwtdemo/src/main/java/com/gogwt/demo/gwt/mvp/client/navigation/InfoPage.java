package com.gogwt.demo.gwt.mvp.client.navigation;

import com.gogwt.framework.arch.utils.ActionForward;
import com.gogwt.framework.arch.widgets.AbstractPage;
import com.gogwt.framework.arch.widgets.PageMetaInfo;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class InfoPage extends AbstractPage {

	@Override
	public void process() {
		this.pagePanel.clear();

		Panel layoutPanel = new VerticalPanel();

		layoutPanel.add(new HTML(" <b>in Info page.</b> "));

		layoutPanel
				.add(new Label("Global forward: NavigationProcessConfig.xml"));

		Anchor toHome = new Anchor("Global forward: Back To home");
		toHome.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				ActionForward.forward("mvpToHome");
			}
		});

		layoutPanel.add(toHome);

		Button toDetail = new Button("Global forwrd: Back To Detail");
		toDetail.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent arg0) {
				ActionForward.forward("mvpToDetail");
			}
		});

		layoutPanel.add(toDetail);
		
		this.pagePanel.add(layoutPanel);

	}

	@Override
	protected void fillMetaInfo(PageMetaInfo pageInfo) {
		// TODO Auto-generated method stub

	}

}
