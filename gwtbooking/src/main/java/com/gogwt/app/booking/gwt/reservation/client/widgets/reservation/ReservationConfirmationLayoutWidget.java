package com.gogwt.app.booking.gwt.reservation.client.widgets.reservation;

import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.gwt.common.utils.GWTSession;
import com.gogwt.framework.arch.widgets.BaseWidget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

/**
 * 
 * @author WangM
 *
 */
public class ReservationConfirmationLayoutWidget extends BaseWidget {
	private Panel layoutPanel = new FlowPanel();

	public ReservationConfirmationLayoutWidget() {
		super();
		initWidget(layoutPanel);
	}
	
	/**
	 * Fill the layout
	 */
	public void prepareEntryLayout() {
		//layoutPanel.add(new Label("Reservation is success"));
		
		ReserveResponseBean reserveResponseBean = GWTSession.getCurrentReservationContainer().getReserveResponseBean();
		
		layoutPanel.add(new Label("Your reservation number is: " + reserveResponseBean.getReserveNum()));
	}
}
