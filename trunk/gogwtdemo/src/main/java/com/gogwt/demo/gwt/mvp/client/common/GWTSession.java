package com.gogwt.demo.gwt.mvp.client.common;

public class GWTSession {
	private static FormBean formBean;

	public static FormBean getFormBean() {
		return formBean;
	}

	public static void setFormBean(FormBean formBean) {
		GWTSession.formBean = formBean;
	}
}
