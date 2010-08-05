package com.gwtview.arch.rebind;

public class ConfigXMLView {
	private String viewName;
	private String viewClass;
    private String form;
    private String value;
    
	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getViewClass() {
		return viewClass;
	}

	public void setViewClass(String viewClass) {
		this.viewClass = viewClass;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(" ConfigView ");
		sbuilder.append(" viewName="+viewName);
		sbuilder.append(", viewClass="+viewClass);
		sbuilder.append(" form="+form);
		sbuilder.append(", value="+value);
		
		return sbuilder.toString();
	}
}

