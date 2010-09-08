package com.gogwt.app.booking.gwt.reservation.client.processor;

import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;

/**
 * 
 * @author WangM
 * @deprecated
 */
public interface ReservationSessionBackup
{
  public void onSuccessSessionBackup(ReservationContainerBean rpcResult, CommandBean command);
  public void onErrorSessionBackup(Throwable caught, CommandBean command);
}