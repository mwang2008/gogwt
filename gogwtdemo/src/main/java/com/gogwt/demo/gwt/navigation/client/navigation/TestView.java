package com.gogwt.demo.gwt.navigation.client.navigation;

import com.gogwt.framework.arch.widgets.AbstractPage;
import com.gogwt.framework.arch.widgets.PageMetaInfo;
import com.google.gwt.user.client.ui.Label;

public class TestView extends AbstractPage {

	@Override
	public void process() {
		this.pagePanel.add(new Label("test view"));

	}

	@Override
	protected void fillMetaInfo(PageMetaInfo pageInfo) {
		// TODO Auto-generated method stub

	}
}
