package org.gwt.tutorial.remoteInterfaces.customer;

import org.gwt.tutorial.dto.dataObjects.common.EnrollResponse;
import org.gwt.tutorial.dto.dataObjects.common.GuestInfoDTO;
import org.gwt.tutorial.exceptions.clientserver.AppRemoteException;

import com.google.gwt.user.client.rpc.RemoteService;

public interface CustomerService extends RemoteService {
	
	/**
	 * Enroll customer
	 * @param guestInfo - input of guestinfo
	 * @return status of enroll
	 * @throws AppRemoteException
	 */
	public EnrollResponse enroll(final GuestInfoDTO guestInfo) throws AppRemoteException;

}
