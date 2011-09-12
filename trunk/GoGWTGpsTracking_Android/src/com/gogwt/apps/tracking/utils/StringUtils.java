package com.gogwt.apps.tracking.utils;

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
	 
	public static double feetInSecToMileInHour(double feetInSec) {
		double milesInSec = feetInSec/5280.00;
		//in hour
		double milesInHour = milesInSec*3600;
		
		return milesInHour;
				
	}
	
	
}
