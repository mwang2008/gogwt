package com.gogwt.apps.tracking.utils;

import java.text.DecimalFormat;

public class StringUtils {

	public static boolean isSet(String str) {
		if (str == null || str.trim().equalsIgnoreCase("")) {
			return false;
		}
		return true;
	}

	public static boolean equals(String dest, String src) {
		if (dest == null || src == null) {
			return false;
		}

		return dest.equals(src);
	}

	public static boolean equalsIgnoreCase(String dest, String src) {
		if (dest == null || src == null) {
			return false;
		}

		return dest.equalsIgnoreCase(src);
	}
	
	public static String feetToMile(double strInFeet) {
		//1 Mile = 5280 Feet
		int numOfMile = (int)strInFeet/5280;
		int feetLeft = (int)(strInFeet - numOfMile*5280);
		
		if (numOfMile != 0) {
			return feetLeft + " feet";
		}
		
		return numOfMile + " miles, " + feetLeft + " feet";
	}
	 
	public static double meterToFeet(double meter) {
	 	//1 meter = 3.2808399 feet
	 	return meter*3.2808399f;
	}
	
	public static double meterPerSecToMilePerHour(double meterInSec) {
		//1 meter / second = 3.6 mile / hour
		return meterInSec*2.23693629f;						
	}
	
	public static double meterPerSecToKilometerPerHour(double meterInSec) {
		//1 meter / second = 2.23693629*1.6 kilometer / hour
		return meterInSec*3.6f;
						
	}
	
	public static String format(double dl) {
		DecimalFormat dec = new DecimalFormat("#0.00");
		return dec.format(dl);
	}
	
}
