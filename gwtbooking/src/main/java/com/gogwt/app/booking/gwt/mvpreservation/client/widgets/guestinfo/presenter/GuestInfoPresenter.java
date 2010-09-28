package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.presenter;

import java.util.ArrayList;

import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.exceptions.clientserver.SessionTimedoutException;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.AppHandlerManager;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.common.presenter.Presenter;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.event.GuestInfoEvent;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.validate.GuestInfoValidate;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.view.GuestInfoView;
import com.gogwt.app.booking.rpc.proxy.RPCProxyInterface;
import com.gogwt.app.booking.rpc.proxy.reservation.RPCReservationProxy;
import com.gogwt.framework.arch.utils.StringUtils;
import com.google.gwt.user.client.ui.HasWidgets;

public class GuestInfoPresenter implements Presenter, GuestInfoView.Presenter<GuestInfoFormBean>, RPCProxyInterface<ReserveResponseBean> {
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();
	
	private final GuestInfoView<GuestInfoFormBean> view; 
 	
	public GuestInfoPresenter(GuestInfoView<GuestInfoFormBean> view) {
		this.view = view;
		this.view.setPresenter(this);				 
	}
	
	public void go(HasWidgets container) {
	    container.clear();
	    container.add(view.asWidget());		
	}

	/**
	 * call from viewImpl
	 */
	public void doReservation() {
        
		//1. validate form
		ArrayList<String> errorList = new GuestInfoValidate().validate(view);
		if (StringUtils.isSet(errorList)) {
 			view.dispErrorMsg(errorList);
			return;
		}
		
		//2. get form Value
		GuestInfoFormBean guestInfo = view.toValue();
		
		final ReservationContainerBean currentContainer = GWTSession
		.getCurrentReservationContainer();
 		 
		guestInfo.setId(currentContainer.getSelectedHotel().getId());
		
		//3. call RPC
 		CommandBean passCommand = new CommandBean();
		passCommand.addArg("guestInfo", guestInfo);
		
		RPCReservationProxy.getInstance().reserveHotel(guestInfo, currentContainer.getSelectHotelItem(),
				GWTExtClientUtils.getUserContext(), passCommand, this);

	}

	/**
	 * Callback of RPCReservationProxy.getInstance().reserveHotel
	 */
	public void handleRPCSuccess(ReserveResponseBean reserveResponseBean, CommandBean command) {
		GuestInfoFormBean guestInfo = (GuestInfoFormBean)command.getArg("guestInfo");
		
		GWTSession.getCurrentReservationContainer().setGuestInfoBean(guestInfo);
        GWTSession.getCurrentReservationContainer().setReserveResponse(reserveResponseBean);		 
		
		//go to reservation confirmation page
		//GWTExtClientUtils.forward(RESERVATION_CONFIRMATION);
		AppHandlerManager.getEventBus().fireEvent(new GuestInfoEvent());
	}

	public void handleRPCError(Throwable caught, CommandBean command) {
		//add error message
		if (caught instanceof SessionTimedoutException) {
			ArrayList<String> errorList = new ArrayList<String>();
			errorList.add(tags.error_session_timedout());
			view.dispErrorMsg(errorList);
		}
 	}
}
