package com.gogwt.framework.arch.navigation;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author WangM
 * 
 */
public abstract class AbstractViewConfigAccessor implements ViewConfigAccessor {
	protected static String[] viewTokens;
	protected Map<String, ViewConfig> viewConfigInstances;
	protected static Map<String, Map<String, String>> applicationProperties = new HashMap<String, Map<String, String>>();

	/**
	 * <p>
	 * See {@link #setViewInstances(Map<String,Composite>)}
	 * </p>
	 * 
	 * @return Returns the viewInstances.
	 */
	public Map<String, ViewConfig> getViewConfigInstances() {
		return viewConfigInstances;
	}

	/**
	 * <p>
	 * See {@link #setViewTokens(String[])}
	 * </p>
	 * 
	 * @return Returns the viewTokens.
	 */
	public String[] getViewTokens() {
		return viewTokens;
	}

	protected static void addPropertyValue(String propName, String propValName,
			String propVal, Map<String, Map<String, String>> properties) {
		if (properties.containsKey(propName)) {
			properties.get(propName).put(propValName, propVal);
		} else {
			Map<String, String> values = new HashMap<String, String>();
			values.put(propValName, propVal);
			properties.put(propName, values);
		}
	}

	/**
	 * <p>
	 * See {@link #setproperties(Map<String,Map<String,String>>)}
	 * </p>
	 * 
	 * @return Returns the properties.
	 */
	public Map<String, Map<String, String>> getProperties() {
		return applicationProperties;
	}
}
