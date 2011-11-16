package com.gogwt.apps.tracking.data;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "points")
public class PointList {
	@ElementList(inline = true)
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
