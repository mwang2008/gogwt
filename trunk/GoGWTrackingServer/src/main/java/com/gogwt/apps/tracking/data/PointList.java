package com.gogwt.apps.tracking.data;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name="points")
public class PointList {
	private List<Point> point;

	public PointList() {
	}

	public PointList(List<Point> point) {
	    this.point = point;	
	}

	public List<Point> getPoint() {
		return point;
	}

	public void setPoint(List<Point> point) {
		this.point = point;
	}
}
