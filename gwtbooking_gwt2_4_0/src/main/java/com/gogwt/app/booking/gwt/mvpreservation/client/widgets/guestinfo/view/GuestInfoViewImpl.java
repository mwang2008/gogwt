package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.view;

import java.util.ArrayList;

import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.populator.PopulatorDataCollection;
import com.gogwt.framework.arch.widgets.AbstractRequestWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class GuestInfoViewImpl extends AbstractRequestWidget implements
		GuestInfoView<GuestInfoFormBean> {
	private TagsReservationResources tags = TagsReservationResources.Util
			.getInstance();

	@UiTemplate("GuestInfoView.ui.xml")
	interface GuestInfoViewUiBinder extends UiBinder<Widget, GuestInfoViewImpl> {
	}

	private static GuestInfoViewUiBinder uiBinder = GWT.create(GuestInfoViewUiBinder.class);

	private Presenter<GuestInfoFormBean> presenter;

	@UiField ListBox title;
	@UiField TextBox firstName;
	@UiField TextBox lastName;
	@UiField TextBox address;
	@UiField TextBox city;
	@UiField ListBox stateId;
	@UiField TextBox zipCode;
	@UiField TextBox email;
	@UiField Button btnSubmit;
	 
	 
	public GuestInfoViewImpl() {
 		initWidget(uiBinder.createAndBindUi(this));
 		btnSubmit.setText("Submit");
 		fillStatePopulator();
 		fillTitlePopulator();
  	}
	
	@UiHandler("btnSubmit")
	void doSearch(ClickEvent event) {
 	   if (presenter != null) {		   
		   presenter.doReservation();
	   }	  
	}
	
	public void setPresenter(
			com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.view.GuestInfoView.Presenter<GuestInfoFormBean> presenter) {
		this.presenter = presenter;
	}

	public Widget asWidget() {
		return this;
	}

	public GuestInfoFormBean toValue() {
		GuestInfoFormBean guestInfo = new GuestInfoFormBean();
		
		String selectedTitle = title.getValue(title.getSelectedIndex());
		guestInfo.setTitle(selectedTitle);
		
		guestInfo.setFirstName(firstName.getText());
		guestInfo.setLastName(lastName.getText());
		guestInfo.setAddress(address.getText());
		guestInfo.setCity(city.getText());
		
		String selectedStateId = stateId.getValue(stateId.getSelectedIndex());
		
		guestInfo.setStateId(selectedStateId);
		
		guestInfo.setZipCode(zipCode.getText());
		guestInfo.setEmail(email.getText());
		
		return guestInfo;
	}

	public void fromValue(GuestInfoFormBean t) {
		// TODO Auto-generated method stub

	}

 

	public HasValue<String> getFirstName() {
 		return firstName;
	}

	public HasValue<String> getLastName() {
		return lastName;
	}

	public HasValue<String> getAddress() {
		return address;
	}

	public HasValue<String> getCity() {
		return city;
	}

	public HasValue<String> getZipCode() {
	   return zipCode;
	}

	public HasValue<String> getEmail() {
	   return email;
	}

	private void fillStatePopulator() {
		ArrayList<PopulatorItem> stateList = new PopulatorDataCollection().getStatePopulator();
		if (stateList != null) {
			for (PopulatorItem populator : stateList) {
				stateId.addItem(populator.getDisplay(), populator.getCode());
			}
		}
	}
	
	private void fillTitlePopulator() {
		ArrayList<PopulatorItem> titleList = new PopulatorDataCollection().getTitlePopulator();
		if (titleList != null) {
			for (PopulatorItem populator : titleList) {
				title.addItem(populator.getCode(), populator.getDisplay());
			}
		} 
	}
 }
