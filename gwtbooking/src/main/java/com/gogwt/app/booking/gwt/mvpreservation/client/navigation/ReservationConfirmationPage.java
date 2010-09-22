package com.gogwt.app.booking.gwt.mvpreservation.client.navigation;

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
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.AppHandlerManager;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.event.BackHomeEvent;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.reservation.presenter.ReservationConfirmationPresenter;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.reservation.view.ReservationConfirmationView;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.reservation.view.ReservationConfirmationViewImpl;
import com.gogwt.app.booking.rpc.proxy.SessionBackupProxyInterface;
import com.gogwt.app.booking.rpc.proxy.reservation.SessionBackupProxy;
import com.gogwt.framework.arch.widgets.AbstractPage;

public class ReservationConfirmationPage extends AbstractPage implements SessionBackupProxyInterface<ReservationContainerBean>{
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();
	
	private ReservationConfirmationView<ReserveResponseBean> theView;

	public ReservationConfirmationPage() {		// 
 		if (theView == null) {
			theView = new ReservationConfirmationViewImpl();
		}		
 	}

	
	@Override
	public void process() {
		  
		final ReservationContainerBean currentContainer = GWTSession.getCurrentReservationContainer();
	 	
		/*----------------------------------------------------------+
		 * Session backup in case user click refresh button. 
		 *----------------------------------------------------------*/
		if (currentContainer != null
				&& currentContainer.getReserveResponse() != null) {
			displayConfirmation(currentContainer.getReserveResponse());
			return;
		}
		
		// could not find, call session backup.
		SessionBackupProxy.getInstance()
					.getReservationContainerBeanFromSession(
							ProcessStatusEnum.CONFIRMATION, new CommandBean(),
							this);
		
	}

	/**
	 * Callback of SessionBackupProxy.getInstance().getReservationContainerBeanFromSession
	 */
	public void handleSessionBackupRPCSuccess(ReservationContainerBean reservationContainer, CommandBean command) {
		 // update gwtsession
	    if ( reservationContainer != null
	      && reservationContainer.getReserveResponse() != null ) {
	    
	      GWTSession.setCurrentReservationContainer( reservationContainer );
	      
	      displayConfirmation( reservationContainer.getReserveResponse() );
	      
	    } else {
 	    	//GWTExtClientUtils.redirect( VIEW_HOME );
	    	AppHandlerManager.getEventBus().fireEvent(new BackHomeEvent());
	    } 
		
	}

	public void handleSessionBackupRPCError(Throwable caught, CommandBean command) {
		AppHandlerManager.getEventBus().fireEvent(new BackHomeEvent());
		
	}

	/**
	 * Display reservation result
	 */
	private void displayConfirmation(ReserveResponseBean reserveResponse) {
		new ReservationConfirmationPresenter(theView).go(pagePanel);
		theView.processDisplay(reserveResponse);
		
	}
}
