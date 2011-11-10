package com.gogwt.apps.tracking.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
	private final static SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
	
	public static String toString(Date date) throws Exception {
 		return df.format(date);
	}
	
	public static Date toDate(String dateStr) throws Exception {		 
		return df.parse(dateStr);
	}
}
