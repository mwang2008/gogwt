package com.gogwt.app.booking.gwt.reservation.client.widgets.searchresult;

import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.*;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.gwt.common.helper.DisplayHelper;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.framework.arch.utils.GWTFormatUtils;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
 	
	private void displayItem(final HotelBean hotel, final int index) {
		FlexTable flextable = new FlexTable();
		flextable.setWidth("100%");
		
		String fullAddress = DisplayHelper.fullAddress(hotel);
		String amenities = DisplayHelper.fillAmenities(hotel, tags);
		
		Panel hoteNamePanel = WidgetStyleUtils.createVerticalPanel();
		hoteNamePanel.add( new HTML("&nbsp;&nbsp;<B>" + hotel.getName() + "</B>"));
		hoteNamePanel.add(new HTML("&nbsp;&nbsp;" + fullAddress));
		hoteNamePanel.add(new HTML("&nbsp;&nbsp;<b>Amenities:</b> " + amenities));
		
		flextable.setWidget(0, 0, hoteNamePanel);
		flextable.getFlexCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_MIDDLE);
		
		Panel rightPanelInsider = WidgetStyleUtils.createVerticalPanel();
		rightPanelInsider.add(new HTML(GWTFormatUtils.formatDistance(hotel.getDistance()) + " miles &nbsp;&nbsp;"));
		
		final Button selectButton = new Button("&nbsp;Select&nbsp;");
		selectButton.getElement().setAttribute("index", Integer.toString(index));
		
		selectButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				if (event.getSource() instanceof Button) {
					Button selectedButton = (Button)event.getSource();
					
					String indexStr = selectedButton.getElement().getAttribute("index");
					int selectedIndex = Integer.parseInt(indexStr);
					final ReservationContainerBean currentContainer = GWTSession
						.getCurrentReservationContainer();
					   
					final HotelBean selectHotel = currentContainer.getHotelSearchResponse().getHotelList().get(selectedIndex);
					   
					//set selected hotel to GWTSession
					currentContainer.setSelectedHotel(selectHotel);
					currentContainer.setSelectHotelItem(selectedIndex);
					
					GWTExtClientUtils.forward( GUEST_INFO );
				}
				 				
			}			
		});
		
		rightPanelInsider.add(selectButton);
		
		flextable.setWidget(0, 1, rightPanelInsider);
		flextable.getFlexCellFormatter().setAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
 		
		widgetPanel.add(flextable);
		 
	}

	
    
}
