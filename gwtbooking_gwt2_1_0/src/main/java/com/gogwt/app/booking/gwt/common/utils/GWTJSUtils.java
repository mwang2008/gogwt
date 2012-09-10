package com.gogwt.app.booking.gwt.common.utils;


/**
 * <code><B>GWTJSUtils<code><B>
 * <p/>
 * Common utils for GWT JS functions
 * <p/>
 * 
 */

public final class GWTJSUtils {

	/**
	 * <p> Get envJson value. Example; <script type="text/javascript"> var
	 * envJson ='{"secure":"false","regionId":"","subSection":"","countryId":"us","brandId":"","controllerData":[],"controllerName":"","validUrl":true,"brand":"holidayinn","hotelCode":"","section":"hotelsearch","uri":"/holidayinn/en/us/hotelsearch","imagePrefix":"","url":"/holidayinn/en/us/hotelsearch","languageId":"en","contextPath":"/holidayinn"}
	 * ' ; s</script> </p>
	 * 
	 * @return
	 */
	public static native String getEnvJson() /*-{ 
	    return $wnd.envJson; 
	  }-*/;
	
	/**
	 * <p> Get serializedPopulator value set in JSP from server </p>
	 * 
	 * @return
	 */
	public static native String getPopulatorsFromJS() /*-{ 
	    return $wnd.serializedPopulator; 
	  }-*/;
}
