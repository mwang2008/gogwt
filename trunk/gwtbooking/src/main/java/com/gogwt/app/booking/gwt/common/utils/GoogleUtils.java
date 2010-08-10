package com.gogwt.app.booking.gwt.common.utils;

import com.google.gwt.maps.client.Maps;


public final class GoogleUtils {
	private static boolean googleMapKeyLoaded;

	public final static String GOOGLE_MAPKEY="ABQIAAAAmdQW6A4k5xzDjjGKjkBoGxTNnYUpi0vJmWXtC66R5nqEvj2QuxSWoIfZsVFbr3by_GoWAub_tmLJLA";
	
	
	public interface KeyReady{
		public void showMap();
	}
	
	/**
	 * Load Google Map Key
	 * @param languageId
	 * @param isSecure
	 */
	public static void loadGoogleMapKey(
			final Object carriedValue, final KeyReady keyReadyCallBack) {
		 
		 Maps.loadMapsApi(GoogleUtils.GOOGLE_MAPKEY, "2", false, new Runnable() {
		      public void run() {
		    	  //showMap(hotelSearchResponseBean);
		      }
		 });
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
