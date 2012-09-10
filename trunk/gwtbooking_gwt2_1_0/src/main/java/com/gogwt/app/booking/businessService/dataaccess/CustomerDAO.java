package com.gogwt.app.booking.businessService.dataaccess;

import com.gogwt.app.booking.dto.dataObjects.common.CustomerProfile;
import com.gogwt.app.booking.dto.dataObjects.request.LoginFormBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;
import com.gogwt.app.booking.exceptions.clientserver.DuplicatedUserNameException;
import com.gogwt.app.booking.exceptions.clientserver.InvalidUserException;

public interface CustomerDAO {
	public CustomerProfile retrieveCustomerProfileByUsername(final LoginFormBean loginForm) throws InvalidUserException, AppRemoteException;
	public String enrollCustomer(final CustomerProfile request) throws DuplicatedUserNameException, AppRemoteException;
	public CustomerProfile getCustomerById(final String id) throws AppRemoteException;
}
