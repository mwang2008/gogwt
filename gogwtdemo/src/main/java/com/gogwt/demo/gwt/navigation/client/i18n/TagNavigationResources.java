package com.gogwt.demo.gwt.navigation.client.i18n;

import com.gogwt.demo.resources.i18n.LabelResources;
import com.google.gwt.core.client.GWT;


public interface TagNavigationResources extends LabelResources {
	public class Util {
		/**
		 * <p>
		 * Convenience method to instantiate this resource bundle
		 * </p>
		 * 
		 * @return
		 */
		public static TagNavigationResources getInstance() {
			return GWT.create(TagNavigationResources.class);
		}
	}
}
