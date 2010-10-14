package com.gogwt.framework.arch.utils;

import java.util.Map;

import com.google.gwt.user.client.History;

public final class ActionForward {
	private static Map<String, String> currentPageForward;

    
	public static Map<String, String> getCurrentPageForward() {
		return currentPageForward;
	}


	public static void setCurrentPageForward(Map<String, String> currentPageForward) {
		ActionForward.currentPageForward = currentPageForward;
	}


	/**
	 * display token page, no refreshed.
	 * 
	 * @param token
	 */
	public static void forward(final String forwardName) {
		if (!StringUtils.isSet(forwardName)) {
			return;
		}
		
		String forwardTO;
		if (currentPageForward != null && !currentPageForward.isEmpty()) {
			forwardTO = currentPageForward.get(forwardName);
			History.newItem(forwardTO);
		}
	}
	
}
