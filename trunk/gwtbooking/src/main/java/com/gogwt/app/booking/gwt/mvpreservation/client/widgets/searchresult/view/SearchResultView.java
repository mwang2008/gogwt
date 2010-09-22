package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view;

import com.google.gwt.user.client.ui.Widget;

public interface SearchResultView<T,V> {
	public interface Presenter<T> {
		void doSelect(int index, T t);
	}
	
	public void processDisplay(V v);
	
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
}
