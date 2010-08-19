package com.gogwt.app.booking.gwt.reservation.client.processor;

import com.gogwt.app.booking.dto.dataObjects.BaseBean;
import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.rpc.proxy.RPCProxyInterface;
import com.gogwt.app.booking.rpc.proxy.reservation.RPCReservationProxy;

/**
 * <code><B>ReservationSessionBackupProcessor<code><B>
 * <p/>
 * Retrieve reservation container
 * 1. from GWT Sesion
 * 2. if not found, retrieve from backend server session.
 * 3. if data is not in both GWT session and server seesion, redirect to home page.
 * <p/>
 */
public class ReservationSessionBackupProcessor implements RPCProxyInterface<BaseBean> {
	private ReservationSessionBackup reservationSessionBackup;
	
	 public ReservationSessionBackupProcessor(ReservationSessionBackup sessionBackup, ProcessStatusEnum processStatusEnum, CommandBean command) { 
		 reservationSessionBackup = sessionBackup;
		    
		    //call rpc to reservation from session
		    RPCReservationProxy.getInstance().getReservationContainerBeanFromSession(processStatusEnum, command, this );
		  }
		  
		  /**
		   * @see com.ihg.dec.apps.hi.proxy.rpc.RPCProxyInterface#handleRPCError(java.lang.Throwable, com.ihg.dec.apps.hi.dto.dataObjects.common.CommandBean)
		   */
		  public void handleRPCError( Throwable caught, CommandBean command )
		  {
			  reservationSessionBackup.onErrorSessionBackup( caught, command );
		  }

		  /**
		   * @see com.ihg.dec.apps.hi.proxy.rpc.RPCProxyInterface#handleRPCSuccess(com.ihg.dec.apps.hi.dto.dataObjects.common.BaseObject, com.ihg.dec.apps.hi.dto.dataObjects.common.CommandBean)
		   */
		  public void handleRPCSuccess( BaseBean result, CommandBean command )
		  {
			  reservationSessionBackup.onSuccessSessionBackup( (ReservationContainerBean)result, command );
		  }
}
