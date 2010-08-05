package com.gwtview.arch.rebind;

public class ConfigXMLForm {
	private String formName;
	private String formClass;
	
	
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getFormClass() {
		return formClass;
	}
	public void setFormClass(String formClass) {
		this.formClass = formClass;
	}
	
	public String toString() {
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(" ConfigXMLForm ");
		sbuilder.append(" formName="+formName);
		sbuilder.append(", formClass="+formClass);
		 
		
		return sbuilder.toString();
	}
}
