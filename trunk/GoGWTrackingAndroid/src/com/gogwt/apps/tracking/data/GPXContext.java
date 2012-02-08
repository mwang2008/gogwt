package com.gogwt.apps.tracking.data;

import java.util.Date;

public final class GPXContext {
	private boolean isAppStart = false;
	private boolean isGpsStart = false;
	private Date appStartTime;
	private Date gpsStartTime;
	private boolean isGPSBound = false;
	private boolean pause = false;
	private long firstTime = -1;
	
	/**@deprecated*/
	private boolean startSmsService = false;
	private boolean startGPXService = false;
 	
 

	public boolean isStartGPXService() {
		return startGPXService;
	}

	public void setStartGPXService(boolean startGPXService) {
		this.startGPXService = startGPXService;
	}
	
	/**@deprecated*/
	public boolean isStartSmsService() {
		return startSmsService;
	}
	/**@deprecated*/
	public void setStartSmsService(boolean startSmsService) {
		this.startSmsService = startSmsService;
	}

	public GPXContext() {
		firstTime = System.currentTimeMillis(); 
	}
    
	public void startApp() {
		if (firstTime == -1) {
			firstTime = System.currentTimeMillis(); 
		}
		isAppStart = true;
	}
	public void startTrack() {
		reset();
		setAppStartTime(new Date());
		if (firstTime == -1) {
			firstTime = System.currentTimeMillis(); 
		}
		isAppStart = true;
		setGPSBound(true);
	}

	public void stopTrack() {
		reset();
	}

	
	public void reset() {
		firstTime = -1;	
		isAppStart = false;
		isGpsStart = false;
		appStartTime = null;
		gpsStartTime = null;
		isGPSBound = false;
		pause = false;
	}

	
	public boolean isAppStart() {
		return isAppStart;
	}

	public void setAppStart(boolean isAppStart) {
		this.isAppStart = isAppStart;
	}

	public boolean isGpsStart() {
		return isGpsStart;
	}

	public void setGpsStart(boolean isGpsStart) {
		this.isGpsStart = isGpsStart;
	}

	public Date getAppStartTime() {
		return appStartTime;
	}

	public void setAppStartTime(Date appStartTime) {
		this.appStartTime = appStartTime;
	}

	public Date getGpsStartTime() {
		return gpsStartTime;
	}

	public void setGpsStartTime(Date gpsStartTime) {
		this.gpsStartTime = gpsStartTime;
	}

	public boolean isGPSBound() {
		return isGPSBound;
	}

	public void setGPSBound(boolean isGPSBound) {
		this.isGPSBound = isGPSBound;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}
	 

	/*
	public void setFirstTime(long firstTime) {
		this.firstTime = firstTime;
	}
	*/
 
	
	
	
}
