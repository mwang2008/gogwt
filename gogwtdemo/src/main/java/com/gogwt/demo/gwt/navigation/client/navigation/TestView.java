package com.gogwt.demo.gwt.navigation.client.navigation;

import com.gogwt.framework.arch.widgets.AbstractController;
import com.gogwt.framework.arch.widgets.PageMetaInfo;
import com.google.gwt.user.client.ui.Label;

public class TestView extends AbstractController {

	@Override
	public void process() {
		this.controlPanel.add(new Label("test view"));

	}

	@Override
	protected void fillMetaInfo(PageMetaInfo pageInfo) {
		// TODO Auto-generated method stub

	}
}
