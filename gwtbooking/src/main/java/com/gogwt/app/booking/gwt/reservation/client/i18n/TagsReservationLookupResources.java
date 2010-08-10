package com.gogwt.app.booking.gwt.reservation.client.i18n;

import com.gogwt.app.booking.resources.i18n.misc.MiscResources;
import com.google.gwt.core.client.GWT;

/**
 * TagsReservationLookupResources
 * 
 *
 */
public interface TagsReservationLookupResources extends MiscResources {
	public class Util {
		/**
		 * <p>
		 * Convenience method to instantiate this resource bundle
		 * </p>
		 * 
		 * @return
		 */
		public static TagsReservationLookupResources getInstance() {
			return GWT.create(TagsReservationLookupResources.class);
		}
	}
}
