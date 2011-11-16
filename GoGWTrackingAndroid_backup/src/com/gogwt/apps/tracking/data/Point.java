package com.gogwt.apps.tracking.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "point")
public class Point {
	@Element
	private int latitude;
	@Element
	private int longtitude;
	@Element
	private double altitude;
	@Element
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
