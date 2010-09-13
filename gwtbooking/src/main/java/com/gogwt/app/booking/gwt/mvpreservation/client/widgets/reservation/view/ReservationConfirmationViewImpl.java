package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.reservation.view;

import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.mvpreservation.client.i18n.TagsReservationResources;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ReservationConfirmationViewImpl extends AbstractWidget implements ReservationConfirmationView {

	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();

    @UiTemplate("ReservationConfirmationView.ui.xml")
    interface ReservationConfirmationViewUiBinder extends UiBinder<Widget, ReservationConfirmationViewImpl> {}

    private static ReservationConfirmationViewUiBinder uiBinder = GWT.create(ReservationConfirmationViewUiBinder.class);

    private Presenter presenter;
    
    @UiField Label reservationNumber; 
    @UiField Label reservationNumberLabel;
    
    public ReservationConfirmationViewImpl() {
 		initWidget(uiBinder.createAndBindUi(this)); 	
  	}
    
    

	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		
	}

	public Widget asWidget() {
		return this;
	}



	/**
	 * Display reservation result
	 */
	public void displayConfirmation() {
		ReserveResponseBean reserveResponse = (ReserveResponseBean)GWTSession.getCurrentReservationContainer().getReserveResponse();
		reservationNumber.setText(reserveResponse.getReserveNum()+"");
	}
}
