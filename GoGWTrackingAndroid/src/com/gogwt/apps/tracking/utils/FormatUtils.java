package com.gogwt.apps.tracking.utils;

import java.text.DecimalFormat;


/**
 * 		
 * @author michael.wang
 * @deprecated use StringUtils.
 */
public class FormatUtils {
	public static String feetToMile(double strInFeet) {
		//1 Mile = 5280 Feet
		int numOfMile = (int)strInFeet/5280;
		int feetLeft = (int)(strInFeet - numOfMile*5280);
		
		if (numOfMile != 0) {
			return feetLeft + " feet";
		}
		
		return numOfMile + " miles, " + feetLeft + " feet";
	}
	 
	public static double meterPerSecToMilePerHour(double meterInSec) {
		//1 meter / second = 2.23693629 mile / hour
		return meterInSec* 2.23693629f;
						
	}
	
	public static String format(double dl) {
		DecimalFormat dec = new DecimalFormat("#0.00");
		return dec.format(dl);
	}
}
