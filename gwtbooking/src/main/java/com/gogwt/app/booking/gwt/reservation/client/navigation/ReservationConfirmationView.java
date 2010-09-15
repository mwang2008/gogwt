package com.gogwt.app.booking.gwt.reservation.client.navigation;

import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.VIEW_HOME;

import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.app.booking.gwt.reservation.client.widgets.reservation.ReservationConfirmationLayoutWidget;
import com.gogwt.app.booking.rpc.proxy.RPCProxyInterface;
import com.gogwt.app.booking.rpc.proxy.reservation.RPCReservationProxy;
import com.gogwt.framework.arch.widgets.AbstractPage;

public class ReservationConfirmationView extends AbstractPage implements RPCProxyInterface<ReservationContainerBean> {

	@Override
	public void process() {
		
		this.pagePanel.clear();
		
 	 	final ReservationContainerBean currentContainer = GWTSession
				.getCurrentReservationContainer();

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

 
	public void handleRPCSuccess(ReservationContainerBean reservationContainer,
			CommandBean command) {
		 
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
		  // could not find in backend server session, redirect back to
	    // hotelsearch/home page.
		GWTExtClientUtils.redirect( VIEW_HOME );		
	}

	/**
	 * Display reservation result
	 */
	private void displayConfirmation(ReserveResponseBean reserveResponseBean) {
		ReservationConfirmationLayoutWidget layout = new ReservationConfirmationLayoutWidget();

		this.pagePanel.add(layout);

		layout.prepareEntryLayout(reserveResponseBean);
		
	}
 
}
