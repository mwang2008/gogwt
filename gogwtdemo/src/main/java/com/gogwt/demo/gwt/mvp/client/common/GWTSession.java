package com.gogwt.demo.gwt.mvp.client.common;

import com.gogwt.demo.gwt.mvp.client.dataobject.FormBean;

public class GWTSession {
	private static FormBean formBean;

	public static FormBean getFormBean() {
		return formBean;
	}

	public static void setFormBean(FormBean formBean) {
		GWTSession.formBean = formBean;
	}
}
