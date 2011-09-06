package com.gogwt.apps.tracking.data;

import java.util.Date;

public class TrackingMobileData extends GLocation {

	private String id;	 
	private String phoneNumber;
	private String displayName;	 
    private Date createDate;
    
    //used for UI only
    private String color;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
 
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String toString() {

		StringBuilder sbuf = new StringBuilder();
		sbuf.append(Setting.class.getSimpleName() + "=[");
		sbuf.append("id=" + id);
		sbuf.append(",groupId=" + getGroupId());
		sbuf.append(",phoneNumber=" + phoneNumber);
		sbuf.append(",displayName=" + displayName);
		sbuf.append(",latitude=" + getLatitude());
		sbuf.append(",longitude=" + getLongitude());
		sbuf.append(",altitude=" + getAltitude());
		sbuf.append(",provider=" + getProvider());
		sbuf.append(",accuracy=" + getAccuracy());
		sbuf.append(",bearing=" + getBearing());
		sbuf.append(",distance=" + getDistance());
		sbuf.append(",speed=" + getSpeed());
		sbuf.append(",time=" + getTime());
		sbuf.append(",startTime=" + getStartTime());
		sbuf.append(",totalDistance=" + getTotalDistance());
		sbuf.append(",createDate=" + createDate);
		
		sbuf.append("]");

		return sbuf.toString();

	}
	 
}
