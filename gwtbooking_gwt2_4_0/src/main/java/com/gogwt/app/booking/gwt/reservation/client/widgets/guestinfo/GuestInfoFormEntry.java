package com.gogwt.app.booking.gwt.reservation.client.widgets.guestinfo;

import java.util.ArrayList;

import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.common.widget.populator.ExtendedListBox;
import com.gogwt.app.booking.gwt.common.widget.populator.PopulatorUtils;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.ErrorPanel;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.HasFormEntry;
import com.gogwt.app.booking.rpc.proxy.RPCProxyInterface;
import com.gogwt.app.booking.rpc.proxy.reservation.RPCReservationProxy;
import com.gogwt.framework.arch.utils.ActionForward;
import com.gogwt.framework.arch.utils.StringUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.TextBox;

public class GuestInfoFormEntry implements ClickHandler, RPCProxyInterface<ReserveResponseBean> {
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();
	
	private HasFormEntry callback;
	
	//private ListBox title = new ListBox();
	private TextBox firstName = new TextBox();
	private TextBox lastName = new TextBox();
	private TextBox email = new TextBox();
	private TextBox address = new TextBox();
	private TextBox city = new TextBox();
	//private ListBox stateId = new ListBox();
	
	private TextBox zipCode = new TextBox();
	private Hidden hotelId = new Hidden();
	private Hidden selectedIndex = new Hidden();
	Button btnReserve = new Button(tags.button_Alt_Submit());

	private final ExtendedListBox title = 
		PopulatorUtils.createPopulatorListBox( "titlePopulator", tags.label_Title(), " ", "-- Title --");
    private final ExtendedListBox stateId = 
    	PopulatorUtils.createPopulatorListBox( "statesProvincesPopulator", tags.Label_state(), " ", "-------- State --------"  );

	 

	public GuestInfoFormEntry() {
		super();
		btnReserve.addClickHandler(this);

		firstName.setMaxLength(30);
	    lastName.setMaxLength(30);
	    address.setMaxLength(30);	    
	    city.setMaxLength(30); 	    
	    zipCode.setMaxLength(30);
	    email.setMaxLength(30);
  	}


	/**
	 * 
	 * @param fromLayout
	 */
	public void prepareFormEntry(HasFormEntry fromLayout) {
		callback = fromLayout;
	}

	// @Override
	public void onClick(ClickEvent eventWidget) {

		if (eventWidget.getSource() == btnReserve) {
			doReservation();
 		}
	}
	 
	private void doReservation() {
		// 1. validate: not
		ArrayList<String> errorList = new GuestInfoValidate().validate(this);
		if (StringUtils.isSet(errorList)) {
			ErrorPanel.getInstance().displayError(errorList);
			return;
		}

		// 2. retrieve form data
		GuestInfoFormBean guestInfo = fillFormData();  
			
		final ReservationContainerBean currentContainer = GWTSession
		.getCurrentReservationContainer();
		   
		if (currentContainer != null && currentContainer.getSelectedHotel() != null) {
		   guestInfo.setId(currentContainer.getSelectedHotel().getId());
		}
		else {
		   //bug, will fix later
		   guestInfo.setId(1);
		}
		
		// 3. RPC call, the response under handleRPCSuccess,  handleRPCError
  		CommandBean passCommand = new CommandBean();
		passCommand.addArg("guestInfo", guestInfo);

		int selectedHotelIndex = currentContainer.getSelectHotelItem();
	 	RPCReservationProxy.getInstance()
				.reserveHotel(guestInfo, selectedHotelIndex, GWTExtClientUtils.getUserContext(),
						passCommand, this);		 
	}
	
	public void handleRPCSuccess(ReserveResponseBean reserveResponseBean, CommandBean command) {
		GuestInfoFormBean guestInfo = (GuestInfoFormBean)command.getArg("guestInfo");
		
		GWTSession.getCurrentReservationContainer().setGuestInfoBean(guestInfo);
		GWTSession.getCurrentReservationContainer().setReserveResponse(reserveResponseBean);		 
		
		// go to confirm page
		//GWTExtClientUtils.forward(RESERVATION_CONFIRMATION);
		ActionForward.forward("success");
 	}

	 
	public void handleRPCError(Throwable caught, CommandBean command) {
		//caught.fillInStackTrace();
		
        //generic error
        ErrorPanel.getInstance().displayError(tags.error_generic_message());

	}

	private GuestInfoFormBean fillFormData() {
		GuestInfoFormBean request =  new GuestInfoFormBean();
		
		int hotelId = GWTSession.getCurrentReservationContainer().getSelectHotelItem();
		request.setId(hotelId);
		request.setTitle(title.getValue(title.getSelectedIndex()));
		request.setTitle(title.getSelectedValue());
		
		request.setFirstName(firstName.getText());
		request.setLastName(lastName.getText());
		request.setAddress(address.getText());
		request.setCity(city.getText());
		//int selectedItem = stateId.getSelectedIndex();
		//request.setStateId(stateId.getValue(selectedItem));
		request.setStateId(stateId.getSelectedValue());
		
		request.setZipCode(zipCode.getText());
		request.setEmail(email.getText());
		
		return request;
	}

	public TextBox getFirstName() {
		return firstName;
	}

	public TextBox getLastName() {
		return lastName;
	}

 
	public TextBox getEmail() {
		return email;
	}

 
	public TextBox getAddress() {
		return address;
	}

 
	public TextBox getCity() {
		return city;
	}

	 
	 
 
	public TextBox getZipCode() {
		return zipCode;
	}

 
	public Hidden getHotelId() {
		return hotelId;
	}

	 

	public Hidden getSelectedIndex() {
		return selectedIndex;
	}

	 
	 

	public ExtendedListBox getTitle() {
		return title;
	}


	public ExtendedListBox getStateId() {
		return stateId;
	}
 
 }
