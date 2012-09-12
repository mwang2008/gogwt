package com.gogwt.app.booking.gwt.hoteldetail.client.widgets.info.view;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.gwt.common.helper.DisplayAmenitiesGWTHelper;
import com.gogwt.app.booking.gwt.common.helper.DisplayHelper;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SubHotelDetailView extends AbstractWidget {
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();
	
	@UiField Element name;
	@UiField Element description;
	@UiField Element address;
	@UiField Element amenities;
	@UiField Element amenityLabel;
	@UiField Element latLng;
	@UiField Element checkInTime;
	@UiField Element checkOutTime;
	@UiField Element numberOfRooms;
	
	@UiTemplate("SubHotelDetailView.ui.xml")
	interface SubHotelDetailViewUiBinder extends UiBinder<Widget, SubHotelDetailView> {}
	private static SubHotelDetailViewUiBinder uiBinder = GWT.create(SubHotelDetailViewUiBinder.class);

	
	public SubHotelDetailView() {			 
 	    initWidget(uiBinder.createAndBindUi(this));
        
	}
	
	public void displayHotelDetail(HotelBean hotelBean) {
		if (hotelBean == null) {
			return;
		}
		name.setInnerHTML(hotelBean.getName());
		address.setInnerText(DisplayHelper.fullAddress(hotelBean));
		amenityLabel.setInnerText(" Amenities ");
		amenities.setInnerText(DisplayAmenitiesGWTHelper.fillAmenities(hotelBean, tags));
		
		description.setInnerHTML(hotelBean.getDescription());
		
		latLng.setInnerHTML("<b>Geocode:</b> " + hotelBean.getLat() + ", " + hotelBean.getLng());
		checkInTime.setInnerHTML("<b>CheckIn Time:</b> " +  hotelBean.getCheckInTime());
		checkOutTime.setInnerHTML("<b>CheckOut Time:</b> " +  hotelBean.getCheckOutTime());
		numberOfRooms.setInnerHTML("<b>Number Of Room:</b> " +  hotelBean.getNumberOfRooms());
	}
}
