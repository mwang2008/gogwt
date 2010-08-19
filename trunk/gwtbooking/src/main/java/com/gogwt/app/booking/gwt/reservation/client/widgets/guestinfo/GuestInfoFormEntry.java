package com.gogwt.app.booking.gwt.reservation.client.widgets.guestinfo;

import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.RESERVATION_CONFIRMATION;

import java.util.ArrayList;

import com.gogwt.app.booking.dto.dataObjects.BaseBean;
import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.ErrorPanel;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.HasFormEntry;
import com.gogwt.app.booking.rpc.proxy.RPCProxyInterface;
import com.gogwt.app.booking.rpc.proxy.reservation.RPCReservationProxy;
import com.gogwt.framework.arch.utils.GWTStringUtils;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class GuestInfoFormEntry implements ClickListener, RPCProxyInterface<BaseBean> {
	private HasFormEntry callback;
	 
	TextBox firstName = new TextBox();
	TextBox lastName = new TextBox();
	TextBox email = new TextBox();
	TextBox address = new TextBox();
	TextBox city = new TextBox();
	ListBox stateId = new ListBox();
	TextBox zipCode = new TextBox();
	Button btnReserve = new Button("Reserve");

	String firstNameLabel = "First Name";
	String lastNameLabel = "Last Name";
	String emailLabel = "Email Addrss";
	String addressLabel = "Address";
	String cityLabel = "City";
	String stateIdLabel = "State";
	String zipCodeLabel = "Zip Code";

	public GuestInfoFormEntry() {
		super();
		btnReserve.addClickListener(this);

		firstName.setMaxLength(30);
	    lastName.setMaxLength(30);
	    address.setMaxLength(30);	    
	    city.setMaxLength(30); 	    
	    zipCode.setMaxLength(30);
	    email.setMaxLength(30);
	    
		stateId.addItem("GA", "Georgia");
		stateId.addItem("NY", "New York");
	}

	/**
	 * 
	 * @param fromLayout
	 */
	public void prepareFormEntry(HasFormEntry fromLayout) {
		callback = fromLayout;

	}

	// @Override
	public void onClick(Widget eventWidget) {

		if (eventWidget == btnReserve) {

			// 1. validate: not
			ArrayList<String> errorList = new GuestInfoValidate().validate(this);
			if (GWTStringUtils.isSet(errorList)) {
				ErrorPanel.getInstance().displayError(errorList);
				return;
			}

			// 2. retrieve form data
			GuestInfoFormBean request = fillFormData();  
 			
			// 3. RPC call, the response under handleRPCSuccess,  handleRPCError
		 	RPCReservationProxy.getInstance()
					.reserveHotel(request, GWTExtClientUtils.getUserContext(),
							new CommandBean(), this);

			return;
		}

	}

	 
	public void handleRPCSuccess(BaseBean result, CommandBean command) {
		ReserveResponseBean reserveResponseBean = (ReserveResponseBean)result;
		GWTSession.getCurrentReservationContainer().setReserveResponse(reserveResponseBean);		 
		
		// 5. go to hotelsearchresult page
		GWTExtClientUtils.forward(RESERVATION_CONFIRMATION);
		
	}

	 
	public void handleRPCError(Throwable caught, CommandBean command) {
		// TODO Auto-generated method stub
		
	}

	private GuestInfoFormBean fillFormData() {
		GuestInfoFormBean request =  new GuestInfoFormBean();
		
		int hotelId = GWTSession.getCurrentReservationContainer().getSelectHotelItem();
		request.setId(hotelId);
		request.setFirstName(firstName.getText());
		request.setLastName(lastName.getText());
		request.setAddress(address.getText());
		request.setCity(city.getText());
		int selectedItem = stateId.getSelectedIndex();
		request.setStateId(stateId.getValue(selectedItem));
		request.setZipCode(zipCode.getText());
		request.setEmail(email.getText());
		
		return request;
	}
}
