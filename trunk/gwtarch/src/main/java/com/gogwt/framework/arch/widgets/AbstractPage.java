package com.gogwt.framework.arch.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

public abstract class AbstractPage extends Composite {
	protected final Panel pagePanel;
	 
	public AbstractPage() {
		pagePanel = new FlowPanel();
		  initWidget(pagePanel);
    }
	
	/**
	 *  sub class should implemente it
	 */
	public abstract void process();
	
	public void preProcess() {}
	public void postProcess() {}
}
