package com.gogwt.apps.tracking.data;

public class TrackingMobileDataCol {
    private TrackingMobileData start;
    private TrackingMobileData end;
    
    
	public TrackingMobileDataCol(TrackingMobileData start,
			TrackingMobileData end) {
		super();
		this.start = start;
		this.end = end;
	}
	
	public TrackingMobileData getStart() {
		return start;
	}
	
	public TrackingMobileData getEnd() {
		return end;
	}
    
}
