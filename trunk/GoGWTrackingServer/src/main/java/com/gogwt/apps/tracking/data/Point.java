package com.gogwt.apps.tracking.data;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="point")
public class Point {
	private int latitude;
	private int longtitude;
	private double altitude;
	private String provider;

	public Point() {
		
	}
	public Point(int latitude, int longtitude, double altitude, String provider) {
		super();
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.altitude = altitude;
		this.provider = provider;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(int longtitude) {
		this.longtitude = longtitude;
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
		sbuf.append(Point.class.getSimpleName() + "=[");
		sbuf.append("latitude=" + latitude);
		sbuf.append(",longtitude=" + longtitude);
		sbuf.append(",altitude=" + altitude);
		sbuf.append(",provider=" + provider);
		sbuf.append("]");

		return sbuf.toString();

	}

}
