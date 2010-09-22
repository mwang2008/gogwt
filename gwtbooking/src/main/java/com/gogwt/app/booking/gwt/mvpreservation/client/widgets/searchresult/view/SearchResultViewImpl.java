package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view;

import java.util.List;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class SearchResultViewImpl extends AbstractWidget implements
		SearchResultView<HotelBean, HotelSearchResponseBean> {
	private TagsReservationResources tags = TagsReservationResources.Util
			.getInstance();

	@UiTemplate("SearchResultView.ui.xml")
	interface SearchResultViewUiBinder extends
			UiBinder<Widget, SearchResultViewImpl> {
	}

	private static SearchResultViewUiBinder uiBinder = GWT.create(SearchResultViewUiBinder.class);

	private Presenter<HotelBean> presenter;

	@UiField Label destinationLabel;
	@UiField TabLayoutPanel tabPanel;
	@UiField (provided = true) MapWidget map;
	@UiField ResultDetailListSubView itemDetailList;
	
	//private Image mapviewOn, mapviewOff, listviewOn, listviewOff;
	
	private TabItem tabItem;
	
	enum TabItem {
		MAP_TAB,
		LIST_TAB
	}
	
/*	private final int MAP_TAB = 0;
	private final int LIST_TAB = 1;
*/
	public SearchResultViewImpl() {
		map = new MapWidget();
 
		initWidget(uiBinder.createAndBindUi(this));
		
		tabItem = TabItem.MAP_TAB;
		tabPanel.selectTab(tabItem.ordinal());
		
		initMap();
		
	 
	}

	/**
	 * 34.029392, -84.187895  atlantas
	 * 39.509, -98.434  //Kansas
	 */
    private void initMap() {
 		 map.setSize("500px", "300px");
		 map.addControl(new LargeMapControl());
    }
    
    /**
     * Click tab handler
     * @param event
     */
	@UiHandler("tabPanel")
	void selectTab(SelectionEvent<Integer>event) {
	    
	   if (event.getSelectedItem() == TabItem.LIST_TAB.ordinal()) {
	      map.setVisible(false);
	      tabItem = TabItem.LIST_TAB;
	   }
	   if (event.getSelectedItem() == TabItem.MAP_TAB.ordinal()) {
		  map.setVisible(true);
		  tabItem = TabItem.MAP_TAB;
	   }
	   
	   return;
	      
	}
	
	
	public void setPresenter(
			com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.view.SearchResultView.Presenter<HotelBean> presenter) {
		this.presenter = presenter; 		
	}

	public Widget asWidget() {
		return this;
	}

 	 
	
	/**
	 * 
	 * @param hotelSearchResponse
	 */
	public void processDisplay(
			final HotelSearchResponseBean hotelSearchResponse) {

		//1. display map if MAP VIEW is selected
		if (tabItem == TabItem.MAP_TAB) {
 		    showMap(hotelSearchResponse);
		}
		
		//2. display hotel list
		showHotelList(presenter, hotelSearchResponse);

 	}
	
	/**
	 * show hotel list
	 * @param hotelSearchResponseBea
	 */
	private void showHotelList(final Presenter<HotelBean> presenter, final HotelSearchResponseBean hotelSearchResponseBea) {
		itemDetailList.displayDetailItemList(presenter, hotelSearchResponseBea);
	}
	
	 
	/**
	 * Show google map
	 * @param hotelSearchResponseBean
	 */
	public void showMap(final HotelSearchResponseBean hotelSearchResponseBean) {
		final String contextPath = GWTExtClientUtils.getMappingElem().getContextPath();
		
		double centerLat = hotelSearchResponseBean.getCenterGeocode().getLat();
		double centerLng = hotelSearchResponseBean.getCenterGeocode().getLng();
		LatLng cityCenter = LatLng.newInstance(centerLat, centerLng);
			
		// Add a marker
		final Icon cityCenterIcon = Icon.newInstance();
		
		cityCenterIcon.setImageURL(contextPath + "/images/google_green_arrow.png");
		cityCenterIcon.setIconAnchor(Point.newInstance(9, 34));
		cityCenterIcon.setIconSize(Size.newInstance(40, 40));
		final MarkerOptions mOptions = MarkerOptions
				.newInstance(cityCenterIcon);

		final Marker cityCenterMarker = new Marker(cityCenter, mOptions);
		map.addOverlay(cityCenterMarker);
		
		map.setCenter(cityCenter);
		map.setZoomLevel(9);
		
		// Add hotel marker
	    Marker hotelMarker;
	    HotelBean hotel;
	    List<HotelBean> hotelList = hotelSearchResponseBean.getHotelList();
	    for (int i=0; i<hotelList.size(); i++) {
	    	hotel = hotelList.get(i);
	    	hotelMarker = createHotelMarker(hotel, i, contextPath);
	    	
	    	map.addOverlay(hotelMarker);
	    }
 	}
	
	private Marker createHotelMarker(final HotelBean hotel, int index, String imagePathContext) {
		
	    final String iconImage = imagePathContext + "/images/marker" + index + ".png";
		final Icon hotelMarker = Icon.newInstance();
		hotelMarker.setImageURL(iconImage);
		hotelMarker.setIconAnchor(Point.newInstance(9, 34));
		hotelMarker.setIconSize(Size.newInstance(20, 34));
		hotelMarker.setInfoWindowAnchor(Point.newInstance(9, 2));
		final MarkerOptions mOptions = MarkerOptions.newInstance(hotelMarker);			
 
		LatLng point = LatLng.newInstance(hotel.getLat(), hotel.getLng());
		
		final Marker marker = new Marker(point, mOptions);
		
		return marker;
    }
	
 
 
}
