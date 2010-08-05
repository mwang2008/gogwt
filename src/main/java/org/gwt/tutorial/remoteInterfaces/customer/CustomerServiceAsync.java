package org.gwt.tutorial.remoteInterfaces.customer;

import org.gwt.tutorial.dto.dataObjects.common.EnrollResponse;
import org.gwt.tutorial.dto.dataObjects.common.GuestInfoDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface CustomerServiceAsync {

	public static final String CUSTOMER_ENROLL_SERVICE_URI = "enroll.rpc";

	public void enroll(final GuestInfoDTO guestInfo, final AsyncCallback<EnrollResponse> callback);

	/**
	 * Utils to get service
	 * 
	 * @author mwang
	 */
	public static class Util {
		private static CustomerServiceAsync instance;

		public static CustomerServiceAsync getEnrollInstance() {
			return getInstance(CUSTOMER_ENROLL_SERVICE_URI);
		}

		/**
		 * <p> Convenient method used by all method calls in this service </p>
		 * 
		 * @param uri
		 * @return
		 */
		private static CustomerServiceAsync getInstance(String uri) {
			if (instance == null) {
				instance = (CustomerServiceAsync) GWT.create(CustomerService.class);
			}
			ServiceDefTarget target = (ServiceDefTarget) instance;
			Window.alert("== RPC GWT.getModuleBaseURL()="+GWT.getModuleBaseURL() + " , uri="+uri);
			target.setServiceEntryPoint(GWT.getModuleBaseURL() + uri);
			return instance;
		}
	}
}
