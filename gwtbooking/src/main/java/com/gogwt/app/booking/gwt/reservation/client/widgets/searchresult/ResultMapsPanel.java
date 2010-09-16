package com.gogwt.app.booking.gwt.reservation.client.widgets.searchresult;

import java.util.List;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GoogleUtils;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

/**
 * Display Map
 * @author WangM
 *
 */
public class ResultMapsPanel extends AbstractWidget  {
	private Panel layoutPanel = new FlowPanel();
	private final FlowPanel mapPanel;
 
	public ResultMapsPanel() {
		super();

		mapPanel = new FlowPanel();
		mapPanel.setHeight("300px");
		mapPanel.setWidth("500px");

		initWidget(layoutPanel);
		layoutPanel.add(mapPanel);
	}

	/**
	 * show google map
	 * 
	 * @param hotelSearchResponseBean
	 */
	public void showMapWithResult(
			final HotelSearchResponseBean hotelSearchResponseBean) {
		 
		initializeAndShowMap(hotelSearchResponseBean);
	}

	/**
	 * Show map, called from GoogleUtils.loadGoogleMap call back if Google key
	 * is not loaded.
	 */
	public void showMap(final HotelSearchResponseBean hotelSearchResponseBean) {
		
		//Window.alert("showMap");
		final String contextPath = GWTExtClientUtils.getMappingElem().getContextPath();
		
		// map center:
		double centerLat = hotelSearchResponseBean.getCenterGeocode().getLat();
		double centerLng = hotelSearchResponseBean.getCenterGeocode().getLng();
		LatLng cityCenter = LatLng.newInstance(centerLat, centerLng);

		final MapWidget map = new MapWidget(cityCenter, 9);
		map.setSize("97%", "100%");
	    map.addControl(new LargeMapControl());
	    
		// Add a marker
		final Icon cityCenterIcon = Icon.newInstance();
		
		cityCenterIcon.setImageURL(contextPath + "/images/google_green_arrow.png");
		cityCenterIcon.setIconAnchor(Point.newInstance(9, 34));
		cityCenterIcon.setIconSize(Size.newInstance(40, 40));
		final MarkerOptions mOptions = MarkerOptions
				.newInstance(cityCenterIcon);

		final Marker cityCenterMarker = new Marker(cityCenter, mOptions);
		map.addOverlay(cityCenterMarker);
		
		// Add hotel marker
	    Marker hotelMarker;
	    HotelBean hotel;
	    List<HotelBean> hotelList = hotelSearchResponseBean.getHotelList();
	    for (int i=0; i<hotelList.size(); i++) {
	    	hotel = hotelList.get(i);
	    	hotelMarker = createHotelMarker(hotel, i, contextPath);
	    	
	    	map.addOverlay(hotelMarker);
	    }
	    
		// Add the map to the HTML host page
	    mapPanel.add(map);
	}
	
	/**
	 * If Google map key is loaded, no need to load again. Otherwise, such as
	 * refresh the page, reload Google map key
	 */
	private void initializeAndShowMap(
			final HotelSearchResponseBean hotelSearchResponseBean) {
	 
		Maps.loadMapsApi(GoogleUtils.getGoogleMapKey(), "2", false, new Runnable() {
		      public void run() {
		    	  showMap(hotelSearchResponseBean);
		      }
		 });
	 
	}
	
	/**
	 * Create hotel marker
	 * @param hotel
	 * @param index
	 * @return
	 */
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
