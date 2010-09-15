package com.gogwt.app.booking.gwt.reservation.client.widgets.reservation;

import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.gwt.common.helper.DisplayHelper;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;

 
public class ReservationConfirmationLayoutWidget extends AbstractWidget {
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();
	
	private Panel layoutPanel = new FlowPanel();

	public ReservationConfirmationLayoutWidget() {
		super();
		initWidget(layoutPanel);
	}
	
	/**
	 * Fill the layout
	 */
	public void prepareEntryLayout(ReserveResponseBean reserveResponse) {
		//layoutPanel.add(new Label("Reservation is success"));
		
		
		final FlexTable mainTable = WidgetStyleUtils.createFlexTable();
		mainTable.setCellPadding( 0 );
		mainTable.setCellSpacing( 0 );
		mainTable.setStyleName( "guestInformationTable" );
		
		//add white space
		int row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  new HTML("<br>"));
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);
		
	 	//reservation number
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  new HTML("<b>" + tags.reservation_resnumber() + "</b>"));
		mainTable.setText( row, 1,  DisplayHelper.dispReservationNum(reserveResponse.getReserveNum()));
		
		//add white space
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  new HTML("<br>"));
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);

	 	//CAP: guest info
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  WidgetStyleUtils.createLabel(tags.caption_guest_info(), "text12blue"));
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);
	 	
	 	//name
	 	String name = reserveResponse.getGuestInfo().getFirstName() + "  " + reserveResponse.getGuestInfo().getLastName();
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  new HTML(name));
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);
	 	
	 	//guest address
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  WidgetStyleUtils.createLabel(DisplayHelper.fullAddress(reserveResponse.getGuestInfo())));
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);
 	 	
		//add white space
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  new HTML("<br>"));
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);
	 	
	 	//CAP: hotel
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  WidgetStyleUtils.createLabel(tags.caption_hotel_info(), "text12blue"));
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);
	 	
	 	//hotel address
		row = mainTable.getRowCount();
		mainTable.setWidget( row, 0,  WidgetStyleUtils.createLabel(DisplayHelper.fullAddress(reserveResponse.getSelectHotel())));
	 	mainTable.getFlexCellFormatter().setColSpan(row, 0, 2);
  	 
		layoutPanel.add(mainTable);
		
		 
	}
}
