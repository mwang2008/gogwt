package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.view;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public interface GuestInfoView<T> {
	public interface Presenter<T> {
		public void doReservation();
	}
	
	//form entries
	HasValue<String> getFirstName();
	HasValue<String> getLastName();
	HasValue<String> getAddress();
	HasValue<String> getCity();
 	HasValue<String> getZipCode();
 	HasValue<String> getEmail();
	
  
 	
	/**
	 * Validate
	 * @return
	 */
	//ArrayList<String> validate();
	
	/**
	 * Connect to presenter
	 * @param presenter
	 */
	void setPresenter(Presenter<T> presenter);

	/**
	 * 
	 * @return
	 */
	Widget asWidget();

	/**
	 * Set widget value to value object
	 * 
	 * @return the value object
	 */
	T toValue();

	/**
	 * Fill widget with value object
	 * 
	 * @param t  
	 *            the value object
	 */
	void fromValue(T t);
	
	/**
	 * Fill error message
	 * @param msg
	 */
	void dispErrorMsg(ArrayList<String> msg);	 
}
