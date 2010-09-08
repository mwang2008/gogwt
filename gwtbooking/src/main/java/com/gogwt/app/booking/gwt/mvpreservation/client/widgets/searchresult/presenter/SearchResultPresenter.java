package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.presenter;

import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.common.presenter.Presenter;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view.SearchResultView;
import com.google.gwt.user.client.ui.HasWidgets;

public class SearchResultPresenter implements Presenter, SearchResultView.Presenter<SearchFormBean> {
	private final SearchResultView<SearchFormBean> view; 
	
	public SearchResultPresenter(SearchResultView<SearchFormBean> view) {
		this.view = view;
		this.view.setPresenter(this);		
		//this.presenter = presenter;
	}
	
	public void go(HasWidgets container) {
	    container.clear();
	    container.add(view.asWidget());
 	}

    
}
