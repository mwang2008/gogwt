package com.gogwt.apps.tracking.services.domain;

import com.gogwt.apps.tracking.utils.BeanLookupService;


public final class LookupBusinessService {
	public static String PROFILE_DOMAIN_SERVICE = "name=domain/customer/ProfileBusinessDomainService";
	public static String REST_DOMAIN_SERVICE = "name=domain/customer/RestBusinessDomainService";
	
			
	public static ProfileBusinessDomainService getProfileBusinessDomainService() {
		return (ProfileBusinessDomainService) BeanLookupService.getBean(PROFILE_DOMAIN_SERVICE);
	}
	
	public static RestBusinessDomainService getRestBusinessDomainService() {
		return (RestBusinessDomainService) BeanLookupService.getBean(REST_DOMAIN_SERVICE);
	}
	
}
