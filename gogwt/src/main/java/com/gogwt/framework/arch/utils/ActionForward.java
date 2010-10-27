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

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.History;

public final class ActionForward {
	private static Map<String, String> currentPageForward;
    private static Map<String, String> currentGlobalForward;
    
	public static Map<String, String> getCurrentPageForward() {
		return currentPageForward;
	}


	public static void setCurrentPageForward(Map<String, String> currentPageForward, Map<String, String> currentGlobalForward) {
		ActionForward.currentPageForward = currentPageForward;
		ActionForward.currentGlobalForward = currentGlobalForward;
	}


	/**
	 * forward to token page.
	 * 
	 * @param token
	 */
	public static void forward(final String forwardName) {
		if (!StringUtils.isSet(forwardName)) {
			return;
		}
		
		String forwardTO;
		if (currentPageForward != null && !currentPageForward.isEmpty()) {
			if (currentPageForward.containsKey(forwardName)) {
			   forwardTO = currentPageForward.get(forwardName);
			   History.newItem(forwardTO);
			   return;
			}
		}
		
		if (currentGlobalForward != null && !currentGlobalForward.isEmpty()) {
			if (currentGlobalForward.containsKey(forwardName)) {
			   forwardTO = currentGlobalForward.get(forwardName);
			   History.newItem(forwardTO);			
			   return;
			}
		}
		
		//todo: revisit -->  what to do if could not find  
		Log.fatal("Could not find token in config to forward for the token of: " + forwardName);
	}
	
}
