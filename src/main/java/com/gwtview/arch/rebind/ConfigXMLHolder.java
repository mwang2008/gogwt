package com.gwtview.arch.rebind;

import java.util.HashMap;

/**
 * 
 * @author WangM
 *
 */
public class ConfigXMLHolder {
	private HashMap<String, ConfigXMLView> viewMap;
	private HashMap<String, ConfigXMLForm> formMap;
	private HashMap<String, ConfigXMLValue> valueMap;

	public HashMap<String, ConfigXMLView> getViewMap() {
		return viewMap;
	}

	public void setViewMap(HashMap<String, ConfigXMLView> viewMap) {
		this.viewMap = viewMap;
	}

	public HashMap<String, ConfigXMLForm> getFormMap() {
		return formMap;
	}

	public void setFormMap(HashMap<String, ConfigXMLForm> formMap) {
		this.formMap = formMap;
	}

	public HashMap<String, ConfigXMLValue> getValueMap() {
		return valueMap;
	}

	public void setValueMap(HashMap<String, ConfigXMLValue> valueMap) {
		this.valueMap = valueMap;
	}

}
