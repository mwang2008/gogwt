package com.gogwt.app.booking.gwt.common.utils;

import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;

/**
 * <code><B>GWTSession<code><B>
 * <p/>
 * GWTSession to hold temp value, the life cycle of it is with current module.
 * When user refresh the browser, the GWTSession value will be gone.
 * <p/>
 * 
 */

public final class GWTSession {
	private static ReservationContainerBean currentReservationContainer;
	
	
	
	/**
	 * <p>
	 * See {@link #setcurrentReservationContainer(ReservationContainerBean)}
	 * </p>
	 * 
	 * @return Returns the currentReservationContainer.
	 */
	public static ReservationContainerBean getCurrentReservationContainer() {
		if (currentReservationContainer == null) {
			currentReservationContainer = new ReservationContainerBean();
		}
		return currentReservationContainer;
	}

	/**
	 * <p>
	 * Set the value of <code>currentReservationContainer</code>.
	 * </p>
	 * 
	 * @param currentReservationContainer
	 *            The currentReservationContainer to set.
	 */
	public static void setCurrentReservationContainer(
			ReservationContainerBean currentReservationContainer) {
		GWTSession.currentReservationContainer = currentReservationContainer;
	}
}
