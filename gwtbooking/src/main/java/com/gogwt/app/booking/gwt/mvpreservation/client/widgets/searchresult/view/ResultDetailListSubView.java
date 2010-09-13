package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.gwt.mvpreservation.client.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view.SearchResultView.Presenter;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class ResultDetailListSubView extends AbstractWidget {
	private TagsReservationResources tags = TagsReservationResources.Util
			.getInstance();

	@UiTemplate("I_ResultDetailListSubView.ui.xml")
	interface ResultDetailListViewUiBinder extends
			UiBinder<Widget, ResultDetailListSubView> {
	}

	private static ResultDetailListViewUiBinder uiBinder = GWT
			.create(ResultDetailListViewUiBinder.class);

	@UiField
	FlexTable table;
	
	public ResultDetailListSubView() {		 
		initWidget(uiBinder.createAndBindUi(this));
		initTable();
 	}
 	
	private void initTable() {	  
	    // Initialize the table.
	    table.getColumnFormatter().setWidth(0, "128px");
	    table.getColumnFormatter().setWidth(1, "192px");
	}
	
	
	public void displayDetailItemList(final Presenter<HotelBean> presenter, final HotelSearchResponseBean hotelSearchResponseBea) {
	 	int i = 0;
		//ResultDetailItemSubView subItemView = new ResultDetailItemSubView();
		//subItemView.setPresenter(presenter);
		
		for (HotelBean hotelBean : hotelSearchResponseBea.getHotelList()) {
			ResultDetailItemSubView subItemView = new ResultDetailItemSubView(i, hotelBean, presenter);
			
 		    table.setWidget(i, 0, subItemView);
		    i++;
		}
	}
	
	 
}
