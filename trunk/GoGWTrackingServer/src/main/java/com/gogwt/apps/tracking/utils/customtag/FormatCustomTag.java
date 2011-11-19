package com.gogwt.apps.tracking.utils.customtag;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatCustomTag {
	private static final String DATE_FORMAT = "MM/dd/yy hh:mm a ";
	public static String formatTimestamp(long theTimeStamp) {
		Date date = new Date(theTimeStamp);
		
		SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);
		return sf.format(date);
	}
}
