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
package com.gogwt.framework.server.permutation;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * <code><B>PermutationObject<code><B>
 * 
 * <p/>
 */

public class PermutationObject {
	private Properties property;
	private Map<String, String> patternMap;

	public PermutationObject() {
		patternMap = new LinkedHashMap<String, String>();
	}
	public Properties getProperty() {
		return property;
	}

	public void setProperty(Properties property) {
		this.property = property;
	}

	public Map<String, String> getPatternMap() {
		return patternMap;
	}

	public void setPatternMap(Map<String, String> patternMap) {
		this.patternMap = patternMap;
	}

	public void addPatternMap(String name, String value) {
		
		patternMap.put(name, value);
	}

	public String toString() {
		StringBuilder sbud = new StringBuilder();
		
		sbud.append(this.getClass().getSimpleName());
		sbud.append(" [ ");
		for (Map.Entry<String,String> linked : patternMap.entrySet()) {
			sbud.append(linked.getKey()+ "=" + linked.getValue());
		}
		
		if (property != null) {
			sbud.append("\n ==== property ====\n");
		
			Set keys = property.keySet();
			Iterator itr = keys.iterator();
			String key;
			while(itr.hasNext()) {
				key = (String) itr.next();  		
				sbud.append( key + "=" + property.getProperty(key));
				sbud.append("\n");
			}
		}
		sbud.append(" ] ");
		return sbud.toString();
	}
}
