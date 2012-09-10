package com.gogwt.apps.tracking;

/**
 * HOST 1: 
 * http://javaprovider.net 
 * 
 * @author michael.wang
 *
 */
public final class GwtConfig {
	public static int QA = 1;
	public static int PROD = 0;
	public static int PROD_javaprovider = 2;
	

	public static int env = QA; // QA PROD PROD_javaprovider
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

		if (env == GwtConfig.PROD_javaprovider) {
			if (secure) {
				return "http://74.122.199.171/";
			}
			return "http://74.122.199.171/";
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
