package com.gogwt.app.booking.gwt.reservation.client.widgets.home;

import static com.gogwt.app.booking.BookingConstants.LENGTH_75;

import java.util.ArrayList;

import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.common.GeoCodeBean;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.exceptions.clientserver.InvalidateGeocodeException;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.common.widget.DestinationSuggestOracle;
import com.gogwt.app.booking.gwt.common.widget.DestinationSuggestion;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.ErrorPanel;
import com.gogwt.app.booking.rpc.proxy.RPCProxyInterface;
import com.gogwt.app.booking.rpc.proxy.reservation.RPCReservationProxy;
import com.gogwt.framework.arch.utils.ActionForward;
import com.gogwt.framework.arch.utils.StringUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
 

/**
 * The form entry.
 * Response for interactive different form widget
 * @author WangM
 *
 */
public class HomeFormEntry  implements ClickHandler, SelectionHandler,  RPCProxyInterface<HotelSearchResponseBean> {
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance(); 
	 
	protected Button btnSelectDestination = new Button();
	//private final TextBox destination = new TextBox();
	private final ListBox radius = new ListBox();
	protected SuggestBox destination = null;
	private GeoCodeBean selectedGeoCodeBean;
	private String selectedFromSuggetionValue;
	 
	public HomeFormEntry() {
		super();
	 	
		radius.addItem("1");
		radius.addItem("10");
		radius.addItem("20");
		radius.addItem("30");
		radius.addItem("40");
		radius.setSelectedIndex(1);
		
		btnSelectDestination.setText(tags.button_Alt_FindHotel());
		btnSelectDestination.addClickHandler(this);
		
		DestinationSuggestOracle oracle = new DestinationSuggestOracle();	 
		TextBox destinationText = new TextBox();
		//destinationText.setText("Please enter city or full address");
		destinationText.setVisibleLength(30);
		destinationText.setMaxLength(LENGTH_75);
		
		destination = new SuggestBox(oracle, destinationText);
		destination.addSelectionHandler(this);
 	}

	/**
	 * Called from LayoutWidget. 
	 */
	public void prepareFormEntry() {
				
	}

	/**
	 * Action when user click the search button
	 */
 
	public void onClick(ClickEvent eventWidget) {
		 
 		if (eventWidget.getSource() == btnSelectDestination) {
			
			if (StringUtils.equals(destination.getText(), "tags.label_Search_box_colon")) {
				destination.setText("");
			}
		 
			//1. validate: not 
			ArrayList<String> errorList = new SearchValidate().validate(this);
			if (StringUtils.isSet(errorList)) {
				ErrorPanel.getInstance().displayError(errorList);
				return;
			}
			
			//2. call RPC
			SearchFormBean request = new SearchFormBean();
			int radiusValue = Integer.parseInt(radius.getValue(radius.getSelectedIndex()));
			
			if (StringUtils.equals(selectedFromSuggetionValue, destination.getText())) {
			   request.setGeoCode(selectedGeoCodeBean);
			}
			
 			request.setLocation(destination.getText());
			request.setRadius(radiusValue);
			
			/*-------------------------------------------------
			 * The purpose of using RPC proxy is:
			 * 1. Easy to follow/read the logic for the main flow, such as this method of onClick. 
			 *    No big block of handle RPC onSuccess and onError 
			 * 2. add control function to RPC proxy, such as set timed out, avoid duplicate call etc.  
			 *------------------------------------------------*/
			GWTSession.getCurrentReservationContainer().setHotelSearchRequest(request);
			RPCReservationProxy.getInstance().searchHotels(request,
					GWTExtClientUtils.getUserContext(), new CommandBean(), this);

			return;
		}
		
	}
	
	/**
	 * When suggestion is selected
	 */
	public void onSelection(SelectionEvent event) {
		
		DestinationSuggestion destinationSuggestion = (DestinationSuggestion)event.getSelectedItem();
		if (destinationSuggestion != null) {
			selectedFromSuggetionValue = destinationSuggestion.getReplacementString();
			selectedGeoCodeBean = new GeoCodeBean(
					destinationSuggestion.getKeywordBean().getLat(),
					destinationSuggestion.getKeywordBean().getLng());
		}
	}
 
	 
	public void handleRPCSuccess(HotelSearchResponseBean hotelSearchResponse, CommandBean command) {
	 	
		// if no result
		if (hotelSearchResponse == null || !hotelSearchResponse.hasSearchResult()) {
			ErrorPanel.getInstance().displayError(tags.error_no_search_result());
			return;
		}
		 
		// save result to session		
		GWTSession.getCurrentReservationContainer().setHotelSearchResponse(hotelSearchResponse);

		// 5. go to hotelsearchresult page 
		ActionForward.forward("success");
 	}

	public void handleRPCError(Throwable caught, CommandBean command) {
        if (caught instanceof InvalidateGeocodeException) {        	 
			ErrorPanel.getInstance().displayError(tags.error_invalid_geocode());
			return;
        }		
        
        //generic error
        ErrorPanel.getInstance().displayError(tags.error_generic_message());
		 		
	}
	
	
	public Button getBtnSelectDestination() {
		return btnSelectDestination;
	}
  	 
	 
	public SuggestBox getDestination() {
		return destination;
	}

	public ListBox getRadius() {
		return radius;
	}
}
