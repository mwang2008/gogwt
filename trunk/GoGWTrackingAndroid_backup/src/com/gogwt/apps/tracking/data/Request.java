package com.gogwt.apps.tracking.data;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Request {
	@Element
	private Setting setting;
	@Element
	private List<Point> gpoints;

	public Request() {
		
	}
	public Request(Setting setting, List<Point> gpoints) {
		super();
		this.setting = setting;
		this.gpoints = gpoints;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public List<Point> getGpoints() {
		return gpoints;
	}

	public void setGpoints(List<Point> gpoints) {
		this.gpoints = gpoints;
	}

	public String toString() {

		StringBuilder sbuf = new StringBuilder();
		sbuf.append(Request.class.getSimpleName() + "=[");
		
		if (setting != null) {
		   sbuf.append("setting" + setting.toString());
		}
		if (gpoints != null && !gpoints.isEmpty()) {
			for (Point point : gpoints)  {
				sbuf.append(point.toString());
				sbuf.append(" || " );
			}
		}
		else {
				sbuf.append("No point availabe");
		}
	  
		sbuf.append("]");

		return sbuf.toString();

	}
}
