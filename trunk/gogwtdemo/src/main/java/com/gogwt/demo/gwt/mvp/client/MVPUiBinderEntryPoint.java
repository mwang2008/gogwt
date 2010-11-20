package com.gogwt.demo.gwt.mvp.client;

import com.gogwt.framework.arch.navigation.AbstractControllerConfigAccessor;
import com.gogwt.framework.arch.navigation.AbstractEntryPoint;
import com.gogwt.framework.arch.utils.StringUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class MVPUiBinderEntryPoint extends AbstractEntryPoint {

 
	@Override
	protected void loadModule() {
		RootPanel.get("header").add(new HTML("  Common  Header <hr> "));
		
		addPageManagerToRootPanel("wrapperContent");
		
		RootPanel.get("footer").add(new HTML("<hr> Common Footer"));		
	}

	@Override
	protected AbstractControllerConfigAccessor obtainPageAccessor() {
		return GWT.create(MVPDemoProgressConfig.class);
	}
}
