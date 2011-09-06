package com.gogwt.apps.tracking.data;

import com.gogwt.apps.tracking.utils.StringUtils;

public class GLine {
	private int width;
	private String html;
	private String color;
	private String label;
	private int startLat;
	private int startLng;
	private int endLat;
	private int endLng;
	private String startAddr;
	private String endAddr;
	private String dispName;
	private long startTime;
	private long endTime;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getStartLat() {
		return startLat;
	}

	public void setStartLat(int startLat) {
		this.startLat = startLat;
	}

	public int getStartLng() {
		return startLng;
	}

	public void setStartLng(int startLng) {
		this.startLng = startLng;
	}

	public int getEndLat() {
		return endLat;
	}

	public void setEndLat(int endLat) {
		this.endLat = endLat;
	}

	public int getEndLng() {
		return endLng;
	}

	public void setEndLng(int endLng) {
		this.endLng = endLng;
	}

	public String getStartAddr() {
		return startAddr;
	}

	public void setStartAddr(String startAddr) {
		this.startAddr = startAddr;
	}

	public String getEndAddr() {
		return endAddr;
	}

	public void setEndAddr(String endAddr) {
		this.endAddr = endAddr;
	}

	public String getDispName() {
		return dispName;
	}

	public void setDispName(String dispName) {
		this.dispName = dispName;
	}

	 

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}


	@Override
	public boolean equals(Object other) {
		if (other == this)
			return true;
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;

		GLine otherGLine = (GLine) other;
		String key = this.dispName + "|" + this.startTime;
		String otherKey = otherGLine.getDispName() + "|"
				+ otherGLine.getStartTime();

		return StringUtils.equalsIgnoreCase(key, otherKey);
	}

	@Override
	public int hashCode() {

		return 31 * (int)startTime + (dispName == null ? 17 : dispName.hashCode());
	}
	
	public String toString() {

		StringBuilder sbuf = new StringBuilder();
		sbuf.append(GLine.class.getSimpleName() + "=[");
		sbuf.append("width=" + width);
		sbuf.append(",html=" + html);
		sbuf.append(",color=" + color);
		sbuf.append(",label=" + label);
		sbuf.append(",startLat=" + startLat);
		sbuf.append(",startLng=" + startLng);
		sbuf.append(",endLat=" + endLat);
		sbuf.append(",endLng=" + endLng);
		sbuf.append(",startAddr=" + startAddr);
		sbuf.append(",endAddr=" + endAddr);
		sbuf.append(",dispName=" + dispName);
		sbuf.append(",startTime=" + startTime);
		sbuf.append(",endTime=" + endTime);
		sbuf.append("]");

		return sbuf.toString();
	}

}
