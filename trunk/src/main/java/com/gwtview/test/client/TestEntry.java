/**
 * 
 */
package com.gwtview.test.client;


import com.google.gwt.core.client.GWT;
import com.gwtview.arch.navigation.AbstractEntryPoint;
import com.gwtview.arch.navigation.AbstractViewConfigAccessor;

/**
 * @author WangM
 *
 */
public class TestEntry extends AbstractEntryPoint {

	/**
	 *  The entry point
	 */
	@Override
	public void loadModule() {

		// 1. show middle part of GWT body, wrapperContent is defined in the
		// loading JSP.
		addViewManagerToRootPanel("wrapperContent");
 	}

	/**
	 * 
	 */
	@Override
	protected AbstractViewConfigAccessor obtainViewAccessor() {
		return GWT.create(TestProcessConfig.class);
	}

}
