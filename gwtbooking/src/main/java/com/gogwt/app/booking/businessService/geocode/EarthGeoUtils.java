package com.gogwt.app.booking.businessService.geocode;

public class EarthGeoUtils {
	
	public static double RA2DE = 57.2957795129;
	public static double R = 3963.1; // earth radius in miles.

	/**
	 * arcDistance in mile
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 * @throws Exception
	 */
	public static double arcDistance(double lat1, double lng1, double lat2,
			double lng2) {
		if (lat1 == lat2 && lng1 == lng2)
			return 0.0D;

		double a = lat1 / RA2DE;
		double b = lng1 / RA2DE; // convert to radians:
									// degree/57.296739815504497794075517103077
		double c = lat2 / RA2DE;
		double d = lng2 / RA2DE;

		if ((Math.sin(a) * Math.sin(c) + Math.cos(a) * Math.cos(c)
				* Math.cos(b - d)) > 1) {
			return R * Math.acos(1);
		} else {
			return R
					* Math.acos(Math.sin(a) * Math.sin(c) + Math.cos(a)
							* Math.cos(c) * Math.cos(b - d));
		}
	}
/*
	public static double arcDistance(BWLatLng centerLL, String lat2Str,
			String lng2Str) {
		double lat2 = new Double(lat2Str).doubleValue();
		double lng2 = new Double(lng2Str).doubleValue();

		return arcDistance(centerLL.getLat(), centerLL.getLng(), lat2, lng2);
	}

	public static double arcDistance(BWLatLng centerLL, double lat2, double lng2) {
		return arcDistance(centerLL.getLat(), centerLL.getLng(), lat2, lng2);
	}
*/
	/**
	 * getDeltaLongitudeFromDistances, this assume that second lat is the same
	 * as the first one
	 * 
	 * @param lat
	 * @param lng
	 * @param distance ,
	 *            in miles
	 * @return
	 */
	public static double getDeltaLongitudeFromDistance(double lat, double lng,
			double distance) {

		double a = lat / RA2DE;
		double b = lng / RA2DE; // convert to radians: degree/57.29673981

		// 2. cal lng
		// d = b +/- cos(distance/3963.1)/(sin(a)*sin(a)+cos(a)*cos(a)
		// Math.sin(a)*Math.sin(a) + Math.cos(a)*Math.cos(a) == 1.

		double m = Math.cos(distance / R);
		double n = Math.sin(a) * Math.sin(a);
		double p = Math.cos(a) * Math.cos(a);
		double q = (m - n) / p;

		double delta = Math.acos(q) * RA2DE;

		return delta;
	}

	/**
	 * getDeltaLatitudeFromDistance, this assume that second lng is the same as
	 * the first one.
	 * 
	 * @param lat
	 * @param lng
	 * @param distance ,
	 *            in miles
	 * @return
	 */
	public static double getDeltaLatitudeFromDistance(double lat, double lng,
			double distance) {

		double a = lat / RA2DE;
		double b = lng / RA2DE; // convert to radians: degree/RA2DE

		double delta = Math.acos(Math.cos(distance / R)) * RA2DE;

		return delta;

	}
}

