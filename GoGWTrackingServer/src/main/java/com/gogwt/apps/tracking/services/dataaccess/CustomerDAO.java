package com.gogwt.apps.tracking.services.dataaccess;

import java.util.Calendar;
import java.util.List;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.TrackingMobileData;
import com.gogwt.apps.tracking.data.TrackingSmsData;
import com.gogwt.apps.tracking.exceptions.AppRemoteException;
import com.gogwt.apps.tracking.exceptions.DisplayNameAlreadyLoginException;
import com.gogwt.apps.tracking.exceptions.DuplicatedUserNameException;
import com.gogwt.apps.tracking.exceptions.InvalidUserException;
import com.gogwt.apps.tracking.formbean.LoginFormBean;

public interface CustomerDAO {
	public String enrollCustomer(final CustomerProfile request) throws DuplicatedUserNameException, AppRemoteException;
	public CustomerProfile getCustomerById(final String id) throws AppRemoteException;
	public CustomerProfile retrieveCustomerProfileByUsernameAndGroupId(final LoginFormBean loginForm) throws InvalidUserException, AppRemoteException;
	public CustomerProfile retrieveCustomerProfileByUsernameAndGroupId(final String userName, final String groupId) throws InvalidUserException, AppRemoteException;
	public CustomerProfile retrieveCustomerProfileByGroupId(final String groupId) throws InvalidUserException, AppRemoteException;
	public CustomerProfile updateCustomer(CustomerProfile profile) throws AppRemoteException;
	public void saveRemoteLoginUser(final CustomerProfile customerProfile) throws DisplayNameAlreadyLoginException, AppRemoteException;
  	
	public int saveTrackingData(final List<TrackingMobileData> trackingMobileDataList) throws InvalidUserException, AppRemoteException;
	public List<TrackingMobileData> retrieveMinLocationsSnapShot(CustomerProfile customerProfile) throws InvalidUserException, AppRemoteException;
	public List<TrackingMobileData> retrieveMaxLocationsSnapShot(CustomerProfile customerProfile) throws InvalidUserException, AppRemoteException;
	public List<TrackingMobileData> getHistorialTrackDetail(String groupId, String displayName, long startTimeLong) throws InvalidUserException, AppRemoteException;
	public List<TrackingMobileData> retrieveLocations(CustomerProfile customerProfile, Calendar endCal, Calendar startCal) throws InvalidUserException, AppRemoteException;
	public int deleteTrack(String userName, String groupId, String displayName, long startTiemLong) throws InvalidUserException, AppRemoteException;
	public List<TrackingMobileData> getTrack(String userName, String groupId, String displayName, long startTimeLong) throws InvalidUserException, AppRemoteException;
	
    public int saveSmsData(List<TrackingSmsData> smsDataList) throws InvalidUserException, AppRemoteException;
    public List<TrackingSmsData> findAllTrackingSmsData(String groupId, String displayName, long startTimeLong) throws InvalidUserException, AppRemoteException;
}
