package com.gogwt.apps.tracking.services.dataaccess;

import java.util.Calendar;
import java.util.List;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.TrackingMobileData;
import com.gogwt.apps.tracking.exceptions.AppRemoteException;
import com.gogwt.apps.tracking.exceptions.DisplayNameAlreadyLoginException;
import com.gogwt.apps.tracking.exceptions.DuplicatedUserNameException;
import com.gogwt.apps.tracking.exceptions.InvalidUserException;
import com.gogwt.apps.tracking.formbean.LoginFormBean;

public interface CustomerDAO {
	public String enrollCustomer(final CustomerProfile request) throws DuplicatedUserNameException, AppRemoteException;
	public CustomerProfile getCustomerById(final String id) throws AppRemoteException;
	public CustomerProfile retrieveCustomerProfileByUsername(final LoginFormBean loginForm) throws InvalidUserException, AppRemoteException;
	public CustomerProfile retrieveCustomerProfileByGroupId(final String groupId) throws InvalidUserException, AppRemoteException;
	
	public void saveRemoteLoginUser(final CustomerProfile customerProfile) throws DisplayNameAlreadyLoginException, AppRemoteException;
	
	public int saveTrackingData(final List<TrackingMobileData> trackingMobileDataList) throws InvalidUserException, AppRemoteException;
	
	public List<TrackingMobileData> retrieveLocations(CustomerProfile customerProfile, Calendar endCal, Calendar startCal) throws InvalidUserException, AppRemoteException;
}
