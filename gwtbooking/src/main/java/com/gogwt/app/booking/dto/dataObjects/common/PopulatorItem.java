package com.gogwt.app.booking.dto.dataObjects.common;

import com.gogwt.app.booking.dto.dataObjects.BaseBean;

public class PopulatorItem extends BaseBean {
	private String code;
	private String display;

	public PopulatorItem() {
		super();
	}

	public PopulatorItem(String code, String display) {
		super();
		this.code = code;
		this.display = display;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
	
	
}
