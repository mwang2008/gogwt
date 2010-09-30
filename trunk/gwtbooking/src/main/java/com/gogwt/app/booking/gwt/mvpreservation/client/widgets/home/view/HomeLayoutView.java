package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.view;

import java.util.ArrayList;

import com.gogwt.app.booking.gwt.common.widget.DestinationSuggestion;
import com.google.gwt.user.client.ui.Widget;

public interface HomeLayoutView<T> {
	public interface Presenter<T> {
		void doSearch();
		void handlerSuggestionSelection(DestinationSuggestion destinationSuggestion);
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
	
	/**
	 * Fill error message
	 * @param msg
	 */
	void dispErrorMsg(ArrayList<String> msg);
}
