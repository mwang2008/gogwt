package com.gogwt.apps.tracking.data;

import java.util.List;

public class GSmsItem {
	private List<GSmsData> smsList;
	private String dispName;
	
	public List<GSmsData> getSmsList() {
		return smsList;
	}
	public void setSmsList(List<GSmsData> smsList) {
		this.smsList = smsList;
	}
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}
	
	public String toString() {

		StringBuilder sbuf = new StringBuilder();
		sbuf.append(GDispItem.class.getSimpleName() + "=[");
		sbuf.append("dispName=" + dispName);
		 
		sbuf.append("\n,sms [");
	 	if (smsList != null && !smsList.isEmpty()) {
			int i=0; 
			for (GSmsData smsData : smsList) {
				sbuf.append("smsIndex=" + i++ + ","+ smsData.toString());
				sbuf.append("\n");
			}
		}
	 	else {
	 		sbuf.append(" not sms");
	 	}
	 	sbuf.append("]");
		sbuf.append("]");

		return sbuf.toString();

	}
	
	public String getContent() {
		return toString();
	}
}
