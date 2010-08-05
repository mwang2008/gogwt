package com.gwtview.arch.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

/**
 * 
 * @author WangM
 *
 */
public abstract class AbstractView extends Composite implements View 
{
	protected final Panel viewPanel;
	protected ActionValue actionValue;
	protected ActionForm actionForm;
	
	public AbstractView() {
		  viewPanel = new FlowPanel();
		  initWidget(viewPanel);
    }
	
	/**
	 *  sub class should implemente it
	 */
	public abstract void process(ActionForm actionForm);
	
	public void preProcess() {}
	public void postProcess() {}
}
