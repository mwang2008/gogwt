package com.gogwt.apps.tracking.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "setting")
public class Setting {
	@Element
	private int servreCallIntervalInSec;
	@Element
	private int gpsIntervalInSec;
	@Element
	private boolean gpsEnable;

	public Setting() {
		
	}
	public Setting(int servreCallIntervalInSec, int gpsIntervalInSec,
			boolean gpsEnable) {
		super();
		this.servreCallIntervalInSec = servreCallIntervalInSec;
		this.gpsIntervalInSec = gpsIntervalInSec;
		this.gpsEnable = gpsEnable;
	}

	public int getServreCallIntervalInSec() {
		return servreCallIntervalInSec;
	}

	public void setServreCallIntervalInSec(int servreCallIntervalInSec) {
		this.servreCallIntervalInSec = servreCallIntervalInSec;
	}

	public int getGpsIntervalInSec() {
		return gpsIntervalInSec;
	}

	public void setGpsIntervalInSec(int gpsIntervalInSec) {
		this.gpsIntervalInSec = gpsIntervalInSec;
	}

	public boolean isGpsEnable() {
		return gpsEnable;
	}

	public void setGpsEnable(boolean gpsEnable) {
		this.gpsEnable = gpsEnable;
	}

	public String toString() {

		StringBuilder sbuf = new StringBuilder();
		sbuf.append(Setting.class.getSimpleName() + "=[");
		sbuf.append("servreCallIntervalInSec=" + servreCallIntervalInSec);
		sbuf.append(",gpsIntervalInSec=" + gpsIntervalInSec);
		sbuf.append(",gpsEnable=" + gpsEnable);
		sbuf.append("]");

		return sbuf.toString();

	}

}
