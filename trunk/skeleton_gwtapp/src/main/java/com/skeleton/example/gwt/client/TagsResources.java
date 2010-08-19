package com.skeleton.example.gwt.client;

import com.google.gwt.core.client.GWT;
import com.skeleton.example.resources.i18n.LabelResources;

public interface TagsResources extends LabelResources {
	public class Util {
		/**
		 * <p>
		 * Convenience method to instantiate this resource bundle
		 * </p>
		 * 
		 * @return
		 */
		public static TagsResources getInstance() {
			return GWT.create(TagsResources.class);
		}
	}
}
