package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.view;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public interface HomeLayoutView<T> {
	public interface Presenter<T> {
		void doSearch();
	}

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
	
	void fillErrorMsg(ArrayList<String> msg);
}
