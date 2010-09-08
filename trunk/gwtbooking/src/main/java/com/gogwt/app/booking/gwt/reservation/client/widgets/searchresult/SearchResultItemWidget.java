package com.gogwt.app.booking.gwt.reservation.client.widgets.searchresult;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.app.booking.gwt.reservation.client.i18n.TagsReservationResources;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Panel;

public class SearchResultItemWidget extends AbstractWidget {
	
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();
	
	 //***Create Primary Panel for Hotel Item
    final Panel widgetPanel = WidgetStyleUtils.createVerticalPanel( "result_area_border" );

	public SearchResultItemWidget(final HotelBean hotel, final int index) {	 
		initWidget(widgetPanel);
		
		displayItem(hotel, index);
	}
	
	/*				flextable.setWidget(i, 0, new Label(hotel.getId()+ ":" + hotel.getName()));
		flextable.setWidget(i, 1, new Label(""+hotel.getLat()));
	flextable.setWidget(i, 2, new Label(""+hotel.getLng()));
	flextable.setWidget(i, 3, new Label(hotel.getCity()));				
	Anchor reserve = WidgetStyleUtils.createAnchor( "Reserve");
	reserve.addStyleName("progressEditHotelName");
 
	final int index = i;
	reserve.addClickListener(new ClickListener() {
		public void onClick(Widget widget) {
			GWTSession.getCurrentReservationContainer().setSelectHotelItem(index);
			GWTExtClientUtils.forward("guestinfo");						
		}					
	});				
	flextable.setWidget(i, 4, reserve);*/
	
	private void displayItem(final HotelBean hotel, final int index) {
		FlexTable flextable = new FlexTable();
		flextable.setWidth("100%");
		//flextable.setBorderWidth(1);
		
		flextable.setWidget(0, 0, new HTML(hotel.getName()));
		//flextable.getFlexCellFormatter().setStyleName(0, 0, "table-cell");
	 
		flextable.setWidget(0, 2, WidgetStyleUtils.createLabel("123.22 Miles"));
		flextable.getFlexCellFormatter().setAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		//flextable.getFlexCellFormatter().setStyleName(0, 2, "table-cell");
		
		// Let's put a button in the middle...
		flextable.setWidget(1, 0, new Button("Wide Button"));
 		flextable.getFlexCellFormatter().setColSpan(1, 0, 3);

 		flextable.setText(2, 2, "bottom-right corner");


		/*int row = flextable.getRowCount();
		
		Panel namePanel = WidgetStyleUtils.createHorizontalPanel("result_name_row");
		namePanel.add(new HTML(hotel.getName()));
		
		Label miles = WidgetStyleUtils.createLabel("123.22 Miles", "milesText");
		
		flextable.setWidget(row, 0, namePanel);
		flextable.setWidget(row, 1, miles);
 		
		row = flextable.getRowCount();
		
		Panel selectPanel = WidgetStyleUtils.createHorizontalPanel("content_area_table_even_row");
		selectPanel.add(new Label("Select"));
		
		flextable.setWidget(row, 0, selectPanel);*/
		
		
		
		widgetPanel.add(flextable);
		 
	}

	
    
}
