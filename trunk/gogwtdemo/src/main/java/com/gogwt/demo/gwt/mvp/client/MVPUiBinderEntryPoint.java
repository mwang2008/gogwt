package com.gogwt.demo.gwt.mvp.client;

import com.gogwt.framework.arch.navigation.AbstractEntryPoint;
import com.gogwt.framework.arch.navigation.AbstractPageConfigAccessor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class MVPUiBinderEntryPoint extends AbstractEntryPoint {

 
	@Override
	protected void loadModule() {
		RootPanel.get("header").add(new HTML("  Common  Header <hr> "));
		
		addPageManagerToRootPanel("wrapperContent");
		
		RootPanel.get("footer").add(new HTML("   <hr> Common Footer  "));
		
	}

	@Override
	protected AbstractPageConfigAccessor obtainPageAccessor() {
		return GWT.create(MVPDemoProgressConfig.class);
	}

}
