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
}
