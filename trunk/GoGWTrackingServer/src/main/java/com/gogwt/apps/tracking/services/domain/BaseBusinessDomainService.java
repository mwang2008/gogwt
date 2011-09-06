package com.gogwt.apps.tracking.services.domain;

import com.gogwt.apps.tracking.services.dataaccess.CustomerDAO;

public abstract class BaseBusinessDomainService {
	private CustomerDAO customerDAO;
	
	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}
}
