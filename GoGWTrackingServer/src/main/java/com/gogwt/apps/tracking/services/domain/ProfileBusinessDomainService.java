package com.gogwt.apps.tracking.services.domain;


import org.apache.log4j.Logger;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.exceptions.AppRemoteException;
import com.gogwt.apps.tracking.exceptions.DuplicatedUserNameException;
import com.gogwt.apps.tracking.exceptions.InvalidUserException;
import com.gogwt.apps.tracking.formbean.ContactUsFormBean;
import com.gogwt.apps.tracking.formbean.EnrollCustomerFormBean;
import com.gogwt.apps.tracking.formbean.LoginFormBean;
import com.gogwt.apps.tracking.formbean.PasswordFormBean;
import com.gogwt.apps.tracking.services.communication.NotificationEmail;
import com.gogwt.apps.tracking.utils.PasswordEncoder;
import com.gogwt.apps.tracking.utils.StringUtils;
import com.gogwt.apps.tracking.utils.ToStringUtils;

public final class ProfileBusinessDomainService extends BaseBusinessDomainService {
	private static Logger logger = Logger.getLogger(ProfileBusinessDomainService.class);
	
 	
 	public CustomerProfile enrollCustomer(final EnrollCustomerFormBean formBean)
			throws DuplicatedUserNameException, AppRemoteException {
		
		logger.debug(ToStringUtils.toString(formBean));
		final CustomerProfile request = toCustomerProfile(formBean);
		
		//encode password
		String encrypedPassword = PasswordEncoder.getInstance().encode(formBean.getPassword());
		request.setPassword(encrypedPassword);
		
		String id = getCustomerDAO().enrollCustomer(request);
		
		CustomerProfile profile = getCustomerDAO().getCustomerById(id);
	 	
		//send email by using Google App Engine. 
		new NotificationEmail().sendEnrollEmail(profile, formBean.getPassword());
		
		return profile;
	}
	
 	public CustomerProfile retrieveCustomerProfileByUsername(final String groupId, final String userName) throws InvalidUserException, AppRemoteException {
 		final LoginFormBean loginForm = new LoginFormBean();
 		loginForm.setGroupId(groupId);
 		loginForm.setUserName(userName);
 		
 		return retrieveCustomerProfileByUsername(loginForm);
 	}
 	
	public CustomerProfile retrieveCustomerProfileByUsername(final LoginFormBean loginForm)
			throws InvalidUserException, AppRemoteException {
		
		CustomerProfile customerProfile;
		 
		customerProfile = getCustomerDAO().retrieveCustomerProfileByUsernameAndGroupId(loginForm);
 		
		/*
		if (customerProfile != null) {
			if (!StringUtils.equals(customerProfile.getPassword(), loginForm.getPassword())) {
				throw new InvalidPasswordException("Password does not match: " + customerProfile.getPassword() + ", " + loginForm.getPassword());
			}
		}
		*/
		return customerProfile;
	}
	
    public CustomerProfile updateCustomer(final EnrollCustomerFormBean formBean) throws InvalidUserException, AppRemoteException {
    	
    	 
    	CustomerProfile customerProfile = getCustomerDAO().retrieveCustomerProfileByUsernameAndGroupId(formBean.getUserName(), formBean.getGroupId());
    	customerProfile.setFirstName(formBean.getFirstName());
    	customerProfile.setLastName(formBean.getLastName());
    	customerProfile.setEmail(formBean.getEmail());
    	//customerProfile.setPassword(formBean.getPassword());
    	
    	return getCustomerDAO().updateCustomer(customerProfile);
    }
    
    public void deleteAccountByGroupIdNuserName(final String groupId, final String userName) throws AppRemoteException {
    	
    	 getCustomerDAO().deleteAccountByGroupIdNuserName(groupId, userName);
    }
    
    public CustomerProfile changePassword(final PasswordFormBean formBean) throws InvalidUserException, AppRemoteException {
    	//CustomerProfile customerProfile = getCustomerDAO().getCustomerById(formBean.getCustomerId());
    	CustomerProfile customerProfile = getCustomerDAO().retrieveCustomerProfileByUsernameAndGroupId(formBean.getUserName(), formBean.getGroupId());
    	//encode password
		String encrypedPassword = PasswordEncoder.getInstance().encode(formBean.getNewPass());
		
    	//customerProfile.setPassword(formBean.getNewPass());
		customerProfile.setPassword(encrypedPassword);
		
    	return getCustomerDAO().updateCustomer(customerProfile);
    }

    /**
     * 
     * @param formBean
     * @throws AppRemoteException
     */
    public void forgotPassword(final PasswordFormBean formBean, CustomerProfile customerProfile) throws AppRemoteException {
    	//1. generate ramdom password
    	String newPassword = StringUtils.randomString(6);
    		
    	//2. encrpt 
    	String encrypedPassword = PasswordEncoder.getInstance().encode(newPassword);
    	customerProfile.setPassword(encrypedPassword);
    	
    	//3. set new password to database   	
    	getCustomerDAO().updateCustomer(customerProfile);
    	
    	//4, then send email
    	new NotificationEmail().sendForgotPasswordEmail(customerProfile, newPassword);
    	
    }
    
    public void sendCustomerComments(ContactUsFormBean formBean) {
    	new NotificationEmail().sendCustomerComments(formBean);	
    }
    
	private CustomerProfile toCustomerProfile(final EnrollCustomerFormBean formBean) {
		final CustomerProfile request = new CustomerProfile();
		
		request.setGroupId(formBean.getGroupId());
		request.setGroupName(formBean.getGroupName());
		request.setFirstName(formBean.getFirstName());
		request.setLastName(formBean.getLastName());
		request.setUserName(formBean.getUserName());
		request.setPhoneNumber(formBean.getPhoneNumber());
		request.setEmail(formBean.getEmail());
		request.setPassword(formBean.getPassword());
		
		
		return request;
	}
	
}
