/*
 * Copyright 2010 GoGWT.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

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
