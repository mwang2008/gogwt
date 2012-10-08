package com.gogwt.app.booking.gwt.common.utils;

import com.gogwt.app.booking.dto.dataObjects.common.WeatherResponse;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;



public class WeatherResponseJsonConverter {
	
	public static WeatherResponse convertJSON(String json) {

		final WeatherResponse response = new WeatherResponse();

		final JSONObject jsonObj = (JSONObject) JSONParser.parseStrict(json);
		
		if (jsonObj.get("responseText") != null) {
			response.setResponseText(parseStringValue(jsonObj.get("responseText")));
		}
		
		if (jsonObj.get("success") != null) {
			response.setSuccess(parseBooleanValue(jsonObj.get("success")));
		}
		
		if (jsonObj.get("state") != null) {
			response.setState(parseStringValue(jsonObj.get("state")));
		}
		
		if (jsonObj.get("city") != null) {
			response.setCity(parseStringValue(jsonObj.get("city")));
		}
		
		if (jsonObj.get("zip") != null) {
			response.setZip(parseStringValue(jsonObj.get("zip")));
		}

		if (jsonObj.get("temperatureType") != null) {
			response.setTemperatureType(parseStringValue(jsonObj.get("temperatureType")));
		}

		
		if (jsonObj.get("weatherStationCity") != null) {
			response.setWeatherStationCity(parseStringValue(jsonObj.get("weatherStationCity")));
		}
		if (jsonObj.get("description") != null) {
			response.setDescription(parseStringValue(jsonObj.get("description")));
		}
		if (jsonObj.get("temperature") != null) {
			response.setTemperature(parseStringValue(jsonObj.get("temperature")));
		}
		if (jsonObj.get("relativeHumidity") != null) {
			response.setRelativeHumidity(parseStringValue(jsonObj.get("relativeHumidity")));
		}
		if (jsonObj.get("wind") != null) {
			response.setWind(parseStringValue(jsonObj.get("wind")));
		}
		if (jsonObj.get("pressure") != null) {
			response.setPressure(parseStringValue(jsonObj.get("pressure")));
		}
		if (jsonObj.get("visibility") != null) {
			response.setVisibility(parseStringValue(jsonObj.get("visibility")));
		}
		if (jsonObj.get("windChill") != null) {
			response.setWindChill(parseStringValue(jsonObj.get("windChill")));
		}
		if (jsonObj.get("remarks") != null) {
			response.setRemarks(parseStringValue(jsonObj.get("remarks")));
		}
	 
		
		
		return response;
	}
	
	 /**
	   * <p>
	   * Convert json object to String if object is String type
	   * </p>
	   * @param jsonObj
	   * @return
	   */
	  private static String parseStringValue( final JSONValue jsonObj )
	  {
		  try {
	        return jsonObj.isString().stringValue();
		  }
		  catch (Throwable e) {
			//ignore  
		  }
		  
		  return null;
	  }

	  private static boolean parseBooleanValue(final JSONValue jsonObj) {
		  try {
		    return jsonObj.isBoolean().booleanValue();
		  }
		  catch (Throwable e) {
			  //ignore
		  }
		  return false;
	  }

	  private static int parseIntegerValue(final JSONValue jsonObj) {
		  
		  try {
			  return (int)jsonObj.isNumber().doubleValue();		     
	      }
		  catch (Throwable e) {
			  //ignore
		  }
		
		  return -999;
	 }
	 
	  /**
	   * <p>
	   * Convert to JSONObject if jsonObj is JSONObject
	   * </p>
	   * @param jsonObj
	   * @return
	   */
	  private static JSONObject getJSONObject( final JSONValue jsonObj )
	  {
		try {
	      return jsonObj.isObject();
		}
		catch (Throwable e) {
			//ignore
		}
		return null;
	  }

	
}
