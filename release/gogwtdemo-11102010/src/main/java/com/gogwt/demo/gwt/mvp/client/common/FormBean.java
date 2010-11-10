package com.gogwt.demo.gwt.mvp.client.common;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FormBean implements IsSerializable {
	private String detail;

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
}
