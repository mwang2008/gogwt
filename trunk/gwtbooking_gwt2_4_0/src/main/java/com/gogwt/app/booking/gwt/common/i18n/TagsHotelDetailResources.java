package com.gogwt.app.booking.gwt.common.i18n;

import com.gogwt.app.booking.resources.i18n.error.ErrorResources;
import com.gogwt.app.booking.resources.i18n.view.LabelResources;
import com.google.gwt.core.client.GWT;

public interface TagsHotelDetailResources extends LabelResources, ErrorResources {
	public class Util {
		/**
		 * <p>
		 * Convenience method to instantiate this resource bundle
		 * </p>
		 * 
		 * @return
		 */
		public static TagsHotelDetailResources getInstance() {
			return GWT.create(TagsHotelDetailResources.class);
		}
	}
}
