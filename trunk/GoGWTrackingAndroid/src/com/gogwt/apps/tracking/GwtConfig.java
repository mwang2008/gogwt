package com.gogwt.apps.tracking;

public final class GwtConfig {
	public static int QA = 1;
	public static int PROD = 0;

	public static int env = QA; // QA PROD
	public static boolean DEBUG = true; // /true; //false

	public static String getHost() {
		return getHost(false);
	}

	public static String getHost(boolean secure) {
		if (env == GwtConfig.QA) {
			if (secure) {
				return "https://10.0.101.244/"; // ipconfig /all - IPv4
			}

			return "http://10.0.101.244/"; // ipconfig /all - IPv4
		}

		if (env == GwtConfig.PROD) {
			// return "http://74.244.61.229/"; //gogwt.com
			if (secure) {
				return "https://www.gogwt.com/";
			}
			return "http://www.gogwt.com/";
		}

		if (secure) {
			return "https://10.0.101.244/";
		}
		return "http://10.0.101.244/";
	}

	public static int getMapViewId() {
		if (env == QA) {
			return R.id.mapview;
		}
		/*
		 * if (env == PROD) { return R.id.mapview_prod; }
		 */
		return R.id.mapview;
	}
}
