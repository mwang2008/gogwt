package com.gogwt.app.booking.rpc.proxy;

import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;

public interface RPCProxyInterface <Model>
{
  /**
   * <p>
   * Handle success
   * </p>
   * @param result
   */
  void handleRPCSuccess(Model result,CommandBean command);
  
  /**
   * <p>
   * Handle error
   * </p>
   * @param caught
   */
  void handleRPCError(Throwable caught,CommandBean command);
}
