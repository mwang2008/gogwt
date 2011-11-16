package com.gogwt.apps.tracking.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class GRequestXML {
	@Element
	private Setting setting;

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}
	
	
}
