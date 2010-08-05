package org.gwt.tutorial.proxy.rpc;

import org.gwt.tutorial.dto.dataObjects.common.CommandBean;
import org.gwt.tutorial.dto.dataObjects.common.EnrollResponse;
import org.gwt.tutorial.dto.dataObjects.common.GuestInfoDTO;
import org.gwt.tutorial.remoteInterfaces.customer.CustomerServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public final class CustomerProxy {
	private static CustomerProxy instance;

	private CustomerProxy() {
	}

	public static CustomerProxy getInstance() {
		if (instance == null) {
			instance = new CustomerProxy();
		}
		return instance;
	}

	/**
	 * call proxy to remote interface
	 * @param guestInfo
	 * @param command
	 * @param callback
	 */
	public void enroll(GuestInfoDTO guestInfo, final CommandBean command,
			final RPCProxyInterfaces<EnrollResponse> callback) {

		CustomerServiceAsync.Util.getEnrollInstance().enroll(guestInfo,
				new AsyncCallback<EnrollResponse>() {

					public void onSuccess(EnrollResponse result) {
						callback.handleRPCSuccess(result, command);
					}

					public void onFailure(Throwable caught) {
						callback.handleRPCError(caught, command);
					}
				});
	}
}
