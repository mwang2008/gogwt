package com.gogwt.app.booking.businessService.domainService;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gogwt.app.booking.businessService.dataaccess.CustomerDAO;
import com.gogwt.app.booking.dto.dataObjects.common.CustomerProfile;
import com.gogwt.app.booking.dto.dataObjects.common.CustomerProfile.EnrollStatus;
import com.gogwt.app.booking.dto.dataObjects.request.EnrollCustomerFormBean;
import com.gogwt.app.booking.dto.dataObjects.request.LoginFormBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;
import com.gogwt.app.booking.exceptions.clientserver.DuplicatedUserNameException;
import com.gogwt.app.booking.exceptions.clientserver.InvalidPasswordException;
import com.gogwt.app.booking.exceptions.clientserver.InvalidUserException;

public class ProfileBusinessService {
	private static Logger logger = Logger
			.getLogger(ProfileBusinessService.class);

	private CustomerDAO customerDAO;

	public CustomerProfile retrieveCustomerProfileByUsername(final LoginFormBean loginForm)
			throws InvalidUserException, AppRemoteException {
		
		CustomerProfile customerProfile;
		 
		customerProfile = getCustomerDAO().retrieveCustomerProfileByUsername(loginForm);
 		
		if (customerProfile != null) {
			if (!StringUtils.equals(customerProfile.getPassword(), loginForm.getPassword())) {
				throw new InvalidPasswordException("Password does not match: " + customerProfile.getPassword() + ", " + loginForm.getPassword());
			}
		}
		return customerProfile;
	}

	public CustomerProfile enrollCustomer(final EnrollCustomerFormBean formBean)
			throws DuplicatedUserNameException, AppRemoteException {
		final CustomerProfile request = toCustomerProfile(formBean);
		
		String id = getCustomerDAO().enrollCustomer(request);
		//String id = "47fc1437-75fb-422f-8238-efb0cd0210bb";
		CustomerProfile profile = getCustomerDAO().getCustomerById(id);
		
		return profile;
	}

	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	
	private CustomerProfile toCustomerProfile(final EnrollCustomerFormBean formBean) {
		final CustomerProfile request = new CustomerProfile();
		
		request.setTitle(formBean.getTitle());
		request.setFirstName(formBean.getFirstName());
		request.setLastName(formBean.getLastName());
		request.setGender(formBean.getGender());
		request.setUserName(formBean.getUserName());
		request.setEmail(formBean.getEmail());
		request.setPassword(formBean.getPassword());
		request.setStatus(EnrollStatus.PENDING.ordinal());
		
		//birthday
		Calendar birthDay = Calendar.getInstance();
		birthDay.set(formBean.getYear(), formBean.getMonth(), formBean.getDay());
		request.setBirthday(birthDay.getTime());
		
		return request;
	}
}
