package com.gogwt.app.booking.gwt.common.utils;

import com.google.gwt.maps.client.Maps;
import com.google.gwt.user.client.Window;


public final class GoogleUtils {
	private static boolean googleMapKeyLoaded;

	public final static String GOOGLE_MAPKEY_GOGWT ="ABQIAAAAmdQW6A4k5xzDjjGKjkBoGxTNnYUpi0vJmWXtC66R5nqEvj2QuxSWoIfZsVFbr3by_GoWAub_tmLJLA";
	public final static String GOOGLE_MAPKYE_ALLHOTELMOTEL = "ABQIAAAAmdQW6A4k5xzDjjGKjkBoGxRuL81oiDTDYRFw2ophuZYKEuHgiBS2WD2rY2gMxFpirkx_QeTPC8zx0A";
	
	public interface KeyReady{
		public void showMap();
	}
	
	public static String getGoogleMapKey() {
		String hostName = Window.Location.getHostName();
		
		if (hostName == null) {
			return GOOGLE_MAPKEY_GOGWT;
		}
		
		int pos = hostName.indexOf("gogwt.com");
		if (pos != -1) {
			return GOOGLE_MAPKEY_GOGWT;
		}
		
		pos = hostName.indexOf("allhotelmotel.com");
		if (pos != -1) {
			return GOOGLE_MAPKYE_ALLHOTELMOTEL;
		}
		
		return GOOGLE_MAPKEY_GOGWT;
	}
	/**
	 * Load Google Map Key
	 * @param languageId
	 * @param isSecure
	 */
	public static void loadGoogleMapKey(
			final Object carriedValue, final KeyReady keyReadyCallBack) {
		 
		 Maps.loadMapsApi(GoogleUtils.getGoogleMapKey(), "2", false, new Runnable() {
		      public void run() {
		    	  //showMap(hotelSearchResponseBean);
		      }
		 });
	}

	public static void loadGoogleMapKey() {
	   if (!googleMapKeyLoaded) {
		   Maps.loadMapsApi(GoogleUtils.getGoogleMapKey(), "2", false, new Runnable() {
		      public void run() {
		    	  googleMapKeyLoaded = true;
		      }
		   });
	   }
	}
	
	/**
	 * @return the googleMapKeyLoaded
	 */
	public static boolean isGoogleMapKeyLoaded() {
		return googleMapKeyLoaded;
	}

	/**
	 * @param googleMapKeyLoaded
	 *            the googleMapKeyLoaded to set
	 */
	public static void setGoogleMapKeyLoaded(boolean googleMapKeyLoaded) {
		GoogleUtils.googleMapKeyLoaded = googleMapKeyLoaded;
	}

}
