package org.gwt.tutorial.proxy.rpc;

import org.gwt.tutorial.dto.dataObjects.common.CommandBean;

/**
 * <code><B>RPCProxyInterface<code><B>
 * <p/>
 * RPC Proxy, RPC call should implement real interface.
 * <p/>
 * @author mwang
 */

public interface RPCProxyInterfaces <Model>
{
  /**
   * <p>
   * Handle success
   * </p>
   * @param result
   */
  public void handleRPCSuccess(Model result,CommandBean command);
  
  /**
   * <p>
   * Handle error
   * </p>
   * @param caught
   */
  public void handleRPCError(Throwable caught,CommandBean command);
}

