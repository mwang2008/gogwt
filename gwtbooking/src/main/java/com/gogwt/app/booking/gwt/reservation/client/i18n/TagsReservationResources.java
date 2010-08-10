package com.gogwt.app.booking.gwt.reservation.client.i18n;

import com.gogwt.app.booking.resources.i18n.error.ErrorResources;
import com.gogwt.app.booking.resources.i18n.view.LabelResources;
import com.google.gwt.core.client.GWT;


/**
 * Tags for Label, Error
 *
 */
public interface TagsReservationResources extends LabelResources, ErrorResources {

	public class Util {
		/**
		 * <p>
		 * Convenience method to instantiate this resource bundle
		 * </p>
		 * 
		 * @return
		 */
		public static TagsReservationResources getInstance() {
			return GWT.create(TagsReservationResources.class);
		}
	}
}
