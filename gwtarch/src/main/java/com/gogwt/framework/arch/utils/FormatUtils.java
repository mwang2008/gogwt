package com.gogwt.framework.arch.utils;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;

public abstract class FormatUtils
{
	
	private static final String DATE_STANDARD_FORMAT = "yyyyMMdd";
	
   /**
    * <p>
    * Format date 
    * </p>
    * @param date
    * @param pattern
    * @return
    */
   public static String formatDate(Date date, String pattern) {
     DateTimeFormat formatter = DateTimeFormat.getFormat(pattern);
     String formatDate = formatter.format(date); 
     return formatDate;
   }
    
   
   /**
    * 
    * <p>
    * Parse String to date
    * </p>
    * @param date
    * @param pattern
    * @return
    * @throws IllegalArgumentException
    */
   public static Date parseDate(String date, String pattern) throws IllegalArgumentException {
     DateTimeFormat formatter = DateTimeFormat.getFormat(pattern);
     if (!GWTStringUtils.isSet(pattern)) {
       formatter = DateTimeFormat.getMediumDateFormat();
     }
     return formatter.parseStrict( date );      
   }
   

 
   
   /**
    * Get today's date with 00:00:00 time
    * current date contains time including hours,min,sec. 
    * To eliminate this and to only have the 
    * date based year,month,date format date to string 
    * and then back to date object.
    * @return
    */
   public static Date todayDate() {
	   final Date currentDate = new Date();
		String strFormattedDate = FormatUtils.formatDate(
				currentDate, DATE_STANDARD_FORMAT);
		Date formattedCurrentDate = FormatUtils.parseDate(
				strFormattedDate, DATE_STANDARD_FORMAT);

		return formattedCurrentDate;
   }
   /**
    * <p>
    * calculate number of days between two java.util.Date
    * </p>
    * @param sDate Date contains Start Date
    * @param eDate Date contains End Date
    * @return
    */
   public static int dateDiff(final Date sDate, final Date eDate) {
	     
	   float oneDay = 1000 * 60 * 60 * 24;
	   float datediff = (float)((eDate.getTime() - sDate.getTime())/oneDay);
	   // this is done to take care of daylight savings issue
	   int datediffInt = Math.round(datediff);
	   return datediffInt; 
   }
   
   /**
    * <p>
    * Format time
    * </p>
    * @param time
    * @param pattern
    * @return
    */
   public static String formatTime(Date time, String pattern) {
     DateTimeFormat formatter = DateTimeFormat.getFormat(pattern);
     return formatter.format(time);
   }
   
   
   
   /**
    * 
    * <p>
    * formatCurrency with symbol and amount
    * </p>
    * @param symbol
    * @param amount
    * @return
    */
   public static String formatCurrency(final String symbol, final double amount) {
     NumberFormat currencyFormatter = NumberFormat.getFormat("#,##0.00");
     currencyFormatter.format( amount);
     
     //return symbol + " " + currencyFormatter.format( amount);
     return " " + symbol + currencyFormatter.format( amount);
     
   }
   
   public static String formatCurrency(final String symbol, final double amount, final String code)
   {
     final NumberFormat currencyFormatter = NumberFormat.getFormat("#,##0.00");
     currencyFormatter.format( amount);
     
     return new StringBuilder(symbol)
     .append(" ").append(currencyFormatter.format( amount))
     .append(" ").append(code)
     .toString();
   }
   public static String formatDistance(float distance)
   {
	   final NumberFormat formatter;
	   if (distance == 0){
		   formatter = NumberFormat.getFormat("#,##0");
	   }else{
		   formatter = NumberFormat.getFormat("#,##0.00");
	   }
	   return formatter.format(distance);
   }
   
   public static String formatDistance(double distance)
   {
	   final NumberFormat formatter;
	   if (distance == 0){
		   formatter = NumberFormat.getFormat("#,##0");
	   }else{
		   formatter = NumberFormat.getFormat("#,##0.00");
	   }
	   return formatter.format(distance);
   }
   
   public static String formatNumber(final int nbr) {
     final NumberFormat formatter = NumberFormat.getFormat("#,##0");
     return formatter.format(nbr);
   }
   
   public static String formatNumber(final double nbr) {
     final NumberFormat formatter = NumberFormat.getFormat("#,##0");
     return formatter.format(nbr);
   }
   
   public static String formatNumber(final long nbr) {
	     final NumberFormat formatter = NumberFormat.getFormat("#,##0");
	     return formatter.format(nbr);
	   }
   
   public static Long differenceDates(final Date sDate, final Date eDate) {
	      
	     return (eDate.getTime() - sDate.getTime())/(1000 * 60 * 60 * 24);
	   }  
}
