package com.gogwt.apps.tracking.data;

import java.util.List;

public class GDispItem {
	private List<GLocation> locs;
	private GLine line;
    private String dispName;
    
	public List<GLocation> getLocs() {
		return locs;
	}

	public void setLocs(List<GLocation> locs) {
		this.locs = locs;
	}

	public GLine getLine() {
		return line;
	}

	public void setLine(GLine line) {
		this.line = line;
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
		sbuf.append("line=" + line.toString());
		sbuf.append("\n,locs [");
	 	if (locs != null && !locs.isEmpty()) {
			int i=0; 
			for (GLocation location : locs) {
				sbuf.append("gndx=" + i++ + ","+ location.toString());
				sbuf.append("\n");
			}
		}
	 	else {
	 		sbuf.append(" not location");
	 	}
	 	sbuf.append("]");
		sbuf.append("]");

		return sbuf.toString();

	}
	
	public String getContent() {
		return toString();
	}

}
