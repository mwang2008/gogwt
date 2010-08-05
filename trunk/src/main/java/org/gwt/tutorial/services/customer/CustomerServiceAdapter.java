package org.gwt.tutorial.services.customer;

import org.gwt.tutorial.dto.dataObjects.common.EnrollResponse;
import org.gwt.tutorial.dto.dataObjects.common.GuestInfoDTO;
import org.gwt.tutorial.exceptions.UnsupportedException;
import org.gwt.tutorial.exceptions.clientserver.AppRemoteException;
import org.gwt.tutorial.remoteInterfaces.customer.CustomerService;

public class CustomerServiceAdapter implements CustomerService{

	public EnrollResponse enroll(GuestInfoDTO guestInfo) throws AppRemoteException {
		throw new UnsupportedException();
	}

}
