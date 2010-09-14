package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.reservation.view;

import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.VIEW_HOME;

import com.gogwt.app.booking.dto.dataObjects.BaseBean;
import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.mvpreservation.client.i18n.TagsReservationResources;
import com.gogwt.app.booking.rpc.proxy.RPCProxyInterface;
import com.gogwt.app.booking.rpc.proxy.reservation.RPCReservationProxy;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ReservationConfirmationViewImpl extends AbstractWidget implements ReservationConfirmationView, RPCProxyInterface<BaseBean> {

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
    
	public void process() {
		final ReservationContainerBean currentContainer = GWTSession.getCurrentReservationContainer();
		//ReserveResponseBean reserveResponse = (ReserveResponseBean)GWTSession.getCurrentReservationContainer().getReserveResponse();
		
		/*----------------------------------------------------------+
		 * Session backup in case user click refersh button. 
		 *----------------------------------------------------------*/
		if (currentContainer != null
				&& currentContainer.getReserveResponse() != null) {
			displayConfirmation(currentContainer.getReserveResponse());
			return;
		}
		
		// could not find, call session backup.
		RPCReservationProxy.getInstance()
					.getReservationContainerBeanFromSession(
							ProcessStatusEnum.CONFIRMATION, new CommandBean(),
							this);
		
	}

	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		
	}

	public Widget asWidget() {
		return this;
	}


	
	public void handleRPCSuccess(BaseBean result, CommandBean command) {
		ReservationContainerBean reservationContainer = (ReservationContainerBean)result;
		 // update gwtsession
	    if ( reservationContainer != null
	      && reservationContainer.getReserveResponse() != null ) {
	    
	      GWTSession.setCurrentReservationContainer( reservationContainer );
	      
	      displayConfirmation( reservationContainer.getReserveResponse() );
	      
	    } else {
			// invoke event bus for target page
			//todo: use eventBus.fireEvent(new HotelSearchEvent());	
	    	GWTExtClientUtils.redirect( VIEW_HOME );
	    } 
		
	}

	public void handleRPCError(Throwable caught, CommandBean command) {
		GWTExtClientUtils.redirect( VIEW_HOME );
		
	}
	
	/**
	 * Display reservation result
	 */
	private void displayConfirmation(ReserveResponseBean reserveResponse) {
		reservationNumberLabel.setText(tags.reservation_resnumber());
		
		reservationNumber.setText(dispReservationNum(reserveResponse.getReserveNum()));
		
		final GuestInfoFormBean guestInfo = reserveResponse.getGuestInfo();
		
		guestName.setText(guestInfo.getFirstName() + " " + guestInfo.getLastName());
		guestAddress.setText(fullAddress(guestInfo));
		
		final HotelBean reservedHotel = reserveResponse.getSelectHotel();
		
		hotelName.setText(reservedHotel.getName());
		hotelAddress.setText(fullAddress(reservedHotel));
		
	}

	private String dispReservationNum(int reservationNum) {
		//DecimalFormat df = new DecimalFormat("000000000");
		String resNum = Integer.toString(reservationNum);
		StringBuilder sbbuild = new StringBuilder();
		
		if (resNum.length() < 10) {
		   for (int i=0; i<10-resNum.length(); i++) {
			   sbbuild.append("0");
		   }
		}
		sbbuild.append(resNum);
		
		return sbbuild.toString();
	}
	
	private String fullAddress(final HotelBean reservedHotel) {
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(reservedHotel.getAddress());
		sbuilder.append(", " + reservedHotel.getCity());
		sbuilder.append(", " + reservedHotel.getState());
		sbuilder.append(" " + reservedHotel.getZipCode());
		
		
		return sbuilder.toString();
	}
	
	private String fullAddress(final GuestInfoFormBean guestInfo) {
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(guestInfo.getAddress());
		sbuilder.append(", " + guestInfo.getCity());
		sbuilder.append(", " + guestInfo.getStateId());
		sbuilder.append(" " + guestInfo.getZipCode());
		
		
		return sbuilder.toString();
	}

	 




}
