package com.gogwt.apps.tracking.services;

/**
 * 
 * @author Michael.Wang
 * @deprecated
 */
public class GPXServiceHelper {
	private static long firstTime = -1;
    private static boolean pause = false;
    
	public static long startFirstTime() {
		if (firstTime == -1) {
			firstTime = System.currentTimeMillis();
		}
		return firstTime;
	}

	public static void stopFirstTime() {
		firstTime = -1;
	}

	public static long getFirstTime() {
		return firstTime;
	}

	public static void setFirstTime(long firstTime) {
		GPXServiceHelper.firstTime = firstTime;
	}

	public static boolean isPause() {
		return pause;
	}

	public static void setPause(boolean pause) {
		GPXServiceHelper.pause = pause;
	}

	 
}
