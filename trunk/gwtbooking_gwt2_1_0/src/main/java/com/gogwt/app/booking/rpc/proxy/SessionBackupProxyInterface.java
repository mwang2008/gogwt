package com.gogwt.app.booking.rpc.proxy;

import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;

public interface SessionBackupProxyInterface <Model> {
	  /**
	   * <p>
	   * Handle success
	   * </p>
	   * @param result
	   */
	  void handleSessionBackupRPCSuccess(Model result,CommandBean command);
	  
	  /**
	   * <p>
	   * Handle error
	   * </p>
	   * @param caught
	   */
	  void handleSessionBackupRPCError(Throwable caught,CommandBean command);
}
