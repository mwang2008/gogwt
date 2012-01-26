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
	
    /**
	   * Returns string with non digit characters removed.
	   * @param pValue
	   *          - the value.
	   * @return string with characters removed.
	   */
	  public static String removeNonDigits( final String pValue )
	  {
	    final StringBuilder returnValue = new StringBuilder();
	    final char chars[] = pValue.toCharArray();

	    for( final char element : chars ) {
	      if ( Character.isDigit( element ) ) {
	        returnValue.append( element );
	      }
	    }
	    return returnValue.toString();
	  }
	
	  /**
	   * Returns string with non white space.
	   * @param pValue
	   * @return
	   */
	  public static String removeWhiteSpace(final String pValue) {
		final StringBuilder returnValue = new StringBuilder();
		final char chars[] = pValue.toCharArray();
		
		for (final char element : chars) {
			if (!Character.isWhitespace(element)) {
				returnValue.append(element);
			}
		}
		
		return returnValue.toString();		
	  }
	  
	  public static boolean isPhoneMatched(final String profilePhone, final String smsPhone) {
			if (!StringUtils.isSet(profilePhone) || !StringUtils.isSet(smsPhone)) {
				return false;
			}
			
			final String pureProfilePhone = StringUtils.removeNonDigits(profilePhone);
			final String pureSmsPhone = StringUtils.removeNonDigits(smsPhone);
			
			//check length
			if (pureSmsPhone.length() < 10 || pureProfilePhone.length()<10) {
				return false;
			}
			
			String minPhone, maxPhone;
			if (pureProfilePhone.length() >= pureSmsPhone.length()) {
				minPhone = pureSmsPhone;
				maxPhone = pureProfilePhone;
			}
			else {
				minPhone = pureProfilePhone;
				maxPhone = pureSmsPhone;
			}
			
			if (maxPhone.length() > minPhone.length()) {
				maxPhone = maxPhone.substring(maxPhone.length()-minPhone.length());			
			}
			
			if (!StringUtils.equalsIgnoreCase(maxPhone, minPhone)) {
			    return false;
			}
			
			return true;
		}
}
