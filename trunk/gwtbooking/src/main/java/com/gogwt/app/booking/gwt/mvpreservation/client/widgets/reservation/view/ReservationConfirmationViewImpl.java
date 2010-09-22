package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.reservation.view;

import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.VIEW_HOME;

import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.gwt.common.helper.DisplayHelper;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.rpc.proxy.SessionBackupProxyInterface;
import com.gogwt.app.booking.rpc.proxy.reservation.SessionBackupProxy;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ReservationConfirmationViewImpl extends AbstractWidget implements ReservationConfirmationView <ReserveResponseBean>  {

	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();

    @UiTemplate("ReservationConfirmationView.ui.xml")
    interface ReservationConfirmationViewUiBinder extends UiBinder<Widget, ReservationConfirmationViewImpl> {}

    private static ReservationConfirmationViewUiBinder uiBinder = GWT.create(ReservationConfirmationViewUiBinder.class);

    private Presenter presenter;
    
    @UiField Label reservationNumber; 
    @UiField Label reservationNumberLabel;
    @UiField Label guestName;
    @UiField Label guestAddress;
    @UiField Label hotelName;
    @UiField Label hotelAddress;
    
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
	public void processDisplay(ReserveResponseBean reserveResponse) {
		reservationNumberLabel.setText(tags.reservation_resnumber());
		
		reservationNumber.setText(DisplayHelper.dispReservationNum(reserveResponse.getReserveNum()));
		
		final GuestInfoFormBean guestInfo = reserveResponse.getGuestInfo();
		
		guestName.setText(guestInfo.getFirstName() + " " + guestInfo.getLastName());
		guestAddress.setText(DisplayHelper.fullAddress(guestInfo));
		
		final HotelBean reservedHotel = reserveResponse.getSelectHotel();
		
		hotelName.setText(reservedHotel.getName());
		hotelAddress.setText(DisplayHelper.fullAddress(reservedHotel));
		
	}

}
