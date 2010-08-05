package com.gwtview.arch.rebind;

public class ConfigXMLValue {
	private String valueName;
	private String valueClass;
	
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	public String getValueClass() {
		return valueClass;
	}
	public void setValueClass(String valueClass) {
		this.valueClass = valueClass;
	}
	
	public String toString() {
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(" ConfigXMLValue ");
		sbuilder.append(" valueName="+valueName);
		sbuilder.append(", valueClass="+valueClass);
		 
		
		return sbuilder.toString();
	}
}
