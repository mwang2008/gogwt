package org.gwt.tutorial.gwt.homepage.client;

import org.gwt.tutorial.gwt.homepage.client.i18n.TagsResources;

import com.google.gwt.core.client.GWT;
import com.gwtview.arch.navigation.AbstractEntryPoint;
import com.gwtview.arch.navigation.AbstractViewConfigAccessor;

/**
 * 
 * @author WangM
 * 
 */
public class HomeEntry extends AbstractEntryPoint {
	private final static TagsResources tags = TagsResources.Util.getInstance();

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
		return GWT.create(CustomerProcessConfig.class);
	}

}
