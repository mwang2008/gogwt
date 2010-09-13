package com.gogwt.app.booking.gwt.reservation.client.widgets.home;

import static com.gogwt.app.booking.BookingConstants.LENGTH_75;
import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.VIEW_SEARCH_RESULT;

import java.util.ArrayList;

import com.gogwt.app.booking.dto.dataObjects.BaseBean;
import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.reservation.client.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.ErrorPanel;
import com.gogwt.app.booking.rpc.proxy.RPCProxyInterface;
import com.gogwt.app.booking.rpc.proxy.reservation.RPCReservationProxy;
import com.gogwt.framework.arch.utils.GWTStringUtils;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
 

/**
 * The form entry.
 * Response for interactive different form widget
 * @author WangM
 *
 */
public class HomeFormEntry  implements ClickListener, RPCProxyInterface<HotelSearchResponseBean> {
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance(); 
	 
	protected Button btnSelectDestination = new Button();
	//private final TextBox destination = new TextBox();
	private final ListBox radius = new ListBox();
	protected SuggestBox destination = null;
	
	public HomeFormEntry() {
		super();
	 	
		radius.addItem("10");
		radius.addItem("20");
		radius.addItem("30");
		radius.addItem("40");
		
		btnSelectDestination.setText("Search");
		btnSelectDestination.addClickListener(this);
		
		DestinationSuggestOracle oracle = new DestinationSuggestOracle();	 
		TextBox destinationText = new TextBox();
		destinationText.setText("Please enter city or full address");
		destinationText.setVisibleLength(30);
		destinationText.setMaxLength(LENGTH_75);
		destination = new SuggestBox(oracle, destinationText);
		
 		 
	}

	/**
	 * Called from LayoutWidget. 
	 */
	public void prepareFormEntry() {
				
	}

	/**
	 * Action when user click the search button
	 */
	public void onClick(Widget eventWidget) {
		if (eventWidget == btnSelectDestination) {
			
			if (GWTStringUtils.equals(destination.getText(), "tags.label_Search_box_colon")) {
				destination.setText("");
			}
		 
			//1. validate: not 
			ArrayList<String> errorList = new SearchValidate().validate(this);
			if (GWTStringUtils.isSet(errorList)) {
				ErrorPanel.getInstance().displayError(errorList);
				return;
			}
			
			//2. call RPC
			SearchFormBean request = new SearchFormBean();
			
			int radiusValue = Integer.parseInt(radius.getValue(radius.getSelectedIndex()));
			
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
	
 
	//@Override
	public void handleRPCSuccess(HotelSearchResponseBean hotelSearchResponse, CommandBean command) {
	 	
		//1. if no result
		
		//2. if multiple result
		
		//3. only one result
		// 4. save to session		
		GWTSession.getCurrentReservationContainer().setHotelSearchResponse(hotelSearchResponse);

		// 5. go to hotelsearchresult page
		GWTExtClientUtils.forward(VIEW_SEARCH_RESULT);
		 
		
	}

	public void handleRPCError(Throwable caught, CommandBean command) {
		// TODO Auto-generated method stub
		
	}
	

	public Button getBtnSelectDestination() {
		return btnSelectDestination;
	}


	public void setBtnSelectDestination(Button btnSelectDestination) {
		this.btnSelectDestination = btnSelectDestination;
	}

	 
	public SuggestBox getDestination() {
		return destination;
	}

	public void setDestination(SuggestBox destination) {
		this.destination = destination;
	}

	public ListBox getRadius() {
		return radius;
	}
	
}
