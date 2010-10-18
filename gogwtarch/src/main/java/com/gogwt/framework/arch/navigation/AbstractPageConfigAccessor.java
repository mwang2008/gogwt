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

package com.gogwt.framework.arch.navigation;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPageConfigAccessor implements PageConfigAccessor{
	protected static String[] pageTokens;
	protected Map<String, PageConfig> pageConfigInstances;
	protected static Map<String, Map<String, String>> applicationProperties = new HashMap<String, Map<String, String>>();

 
	public String[] getPageTokens() {
		return pageTokens;
	}

	public Map<String, PageConfig> getPageConfigInstances() {
		return pageConfigInstances;
	}
	 
	public static Map<String, Map<String, String>> getApplicationProperties() {
		return applicationProperties;
	}

	 
	protected static void addPropertyValue(String propName, String propValName,
			String propVal, Map<String, Map<String, String>> properties) {
		if (properties.containsKey(propName)) {
			properties.get(propName).put(propValName, propVal);
		} 
		else {
			Map<String, String> values = new HashMap<String, String>();
			values.put(propValName, propVal);
			properties.put(propName, values);
		}
	}
	
	protected static void addForwardValue(String name, String token, Map<String, String> forward) {
		if (forward != null) {
			forward.put(name, token);
		}
	}
	
	protected static void addGlobalForwardValue(String name, String token, Map<String, String> globalForward) {
		if (globalForward != null) {
			globalForward.put(name, token);
		}
	}
}
