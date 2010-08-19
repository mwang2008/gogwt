package com.gogwt.app.booking.gwt.reservation.client.widgets.searchresult;

import java.util.List;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.framework.arch.widgets.BaseWidget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ResultListPanel extends BaseWidget {
	Panel layoutPanel = new FlowPanel();

	public ResultListPanel() {
		super();
		initWidget(layoutPanel);
	}
	
	public void showListResult(final HotelSearchResponseBean hotelSearchResponseBean) {
		VerticalPanel panel = new VerticalPanel();
	    
	    FlexTable flextable = new FlexTable();
        panel.add(flextable);
   	
		HotelBean hotel;
		List<HotelBean> hotelList = hotelSearchResponseBean.getHotelList();
		if (hotelList != null && !hotelList.isEmpty()) {
			for (int i=0; i<hotelList.size(); i++) {
				
				hotel = hotelList.get(i);
				
				//display each hotel items
				SearchResultItemWidget itemWidget = new SearchResultItemWidget(hotel, i);
				flextable.setWidget(i, 1, itemWidget);
				
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
			}
		}
		
		layoutPanel.add(panel);		 
	}
	
 
}
