package com.gogwt.apps.tracking.data;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="location")
public class GLocation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5186657538530201175L;
	
	private String groupId;
	private int latitude;
	private int longitude;	
	private double altitude;
	private String provider;
	private double accuracy;
	private double bearing;
	private double distance;
	private double speed;
	private long time;
	private long startTime;
	private double totalDistance; 	
    
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public double getBearing() {
		return bearing;
	}

	public void setBearing(double bearing) {
		this.bearing = bearing;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

 	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

 

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}


	 

	public String toString() {
		StringBuilder sbuf = new StringBuilder();
		sbuf.append(GLocation.class.getSimpleName());
		sbuf.append("[");
		sbuf.append("groupId="+groupId);
		sbuf.append(", latitude="+latitude);
		sbuf.append(", longitude="+longitude);
		sbuf.append(", altitude="+altitude);
		sbuf.append(", totalDistance="+totalDistance);
		sbuf.append(", speed="+speed);
		sbuf.append(", provider="+provider);
		sbuf.append(", bearing="+bearing);
		sbuf.append(", accuracy="+accuracy);
	 
		
		sbuf.append("]");
		
		return sbuf.toString();
	}
	
	 
}
