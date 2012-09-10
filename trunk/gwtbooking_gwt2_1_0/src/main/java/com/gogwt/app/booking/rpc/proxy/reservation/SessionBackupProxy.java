package com.gogwt.app.booking.rpc.proxy.reservation;

import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.rpc.interfaces.reservation.ReservationProcessServiceAsync;
import com.gogwt.app.booking.rpc.proxy.SessionBackupProxyInterface;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SessionBackupProxy {
    private static SessionBackupProxy instance = new SessionBackupProxy();
	
	private SessionBackupProxy() {
	}

	public static SessionBackupProxy getInstance() {
		return instance;
	}
	
	/**
	 * Used for session backup
	 * @param processStatusEnum
	 * @param command
	 * @param callback
	 */
	public void getReservationContainerBeanFromSession(final ProcessStatusEnum processStatusEnum, final CommandBean command,
			final SessionBackupProxyInterface<ReservationContainerBean> callback) {
        
		ReservationProcessServiceAsync service = ReservationProcessServiceAsync.Util.getSeesionBackupAsync();
		
		service.getReservationContainerBeanFromSession(processStatusEnum, new AsyncCallback<ReservationContainerBean>() {
 		 
			//@Override
			public void onFailure(Throwable caught) {
				callback.handleSessionBackupRPCError(caught, command);				
			}

			//@Override
			public void onSuccess(ReservationContainerBean result) {
				callback.handleSessionBackupRPCSuccess(result, command);				
			}		 
		});
	   	
	}
}
