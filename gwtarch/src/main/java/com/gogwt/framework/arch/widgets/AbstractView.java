package com.gogwt.framework.arch.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

 
public abstract class AbstractView extends Composite implements View 
{
	protected final Panel viewPanel;
	 
	public AbstractView() {
		  viewPanel = new FlowPanel();
		  initWidget(viewPanel);
    }
	
	/**
	 *  sub class should implemente it
	 */
	public abstract void process();
	
	public void preProcess() {}
	public void postProcess() {}
}

