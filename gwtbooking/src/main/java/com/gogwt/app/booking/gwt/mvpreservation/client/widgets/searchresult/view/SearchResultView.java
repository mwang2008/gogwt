package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view;

import com.google.gwt.user.client.ui.Widget;

public interface SearchResultView<T> {
	public interface Presenter<T> {
		void doSelect(int index, T t);
	}
	
	public void process();
	
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
