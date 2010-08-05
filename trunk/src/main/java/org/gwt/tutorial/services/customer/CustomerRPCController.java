package org.gwt.tutorial.services.customer;

import org.gwt.tutorial.dto.dataObjects.common.EnrollResponse;
import org.gwt.tutorial.dto.dataObjects.common.GuestInfoDTO;
import org.gwt.tutorial.exceptions.clientserver.AppRemoteException;

/**
 * 
 * @author WangM
 *
 */
public class CustomerRPCController extends CustomerServiceAdapter {
    
	/**
	 * Called from client through RPC
	 */
	public EnrollResponse enroll(GuestInfoDTO guestInfo) throws AppRemoteException {
		
		System.out.println("--- guestInfo in server side = " + guestInfo.getFirstname() + " " + guestInfo.getLastname());
		
		EnrollResponse enrollResponse = new EnrollResponse();
		enrollResponse.setStatus("Success enroll");
				
		return enrollResponse;
	}
}
