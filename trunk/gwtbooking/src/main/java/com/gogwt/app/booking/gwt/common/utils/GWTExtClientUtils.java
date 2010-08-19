package com.gogwt.app.booking.gwt.common.utils;

import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.EnvMappingElem;
import com.gogwt.framework.arch.utils.GWTStringUtils;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;

public final class GWTExtClientUtils {
	private static EnvMappingElem bean;

	public static UserContextBean getUserContext() {
		final UserContextBean userContext = new UserContextBean();
		EnvMappingElem env = buildMappingElem();
		 
		userContext.setLanguageId(env!=null && env.getLanguageId()!= null ? env.getLanguageId() :  "en");
		userContext.setCountryId(env!=null && env.getCountryId()!= null ? env.getCountryId() : "us");
		 
		
		return userContext;
	}

	/**
	 * Get env 
	 * @return
	 */
	public static EnvMappingElem getMappingElem() {

		if (bean == null) {
			bean = buildMappingElem();
		}
		return bean;
	}

	/////////////////////////////////////////////////////////////////////////////////
	//  PRIVATE 	
	//
	private static EnvMappingElem buildMappingElem() {
		final String envJson = GWTJSUtils.getEnvJson();

		// envJson is not defined in JSP, return null.
		if (!GWTStringUtils.isSet(envJson)) {
			return null;
		}

		final JSONObject jsonObj = (JSONObject) JSONParser.parse(envJson);
	 
		if (jsonObj == null) {
			return null;
		}

		final EnvMappingElem envMappingElem = new EnvMappingElem();

		if (jsonObj.get("languageId") != null) {
			envMappingElem.setLanguageId(parseStringValue(jsonObj.get("languageId")));
		}
		if (jsonObj.get("countryId") != null) {
			envMappingElem.setCountryId(parseStringValue(jsonObj.get("countryId")));
		}

		if (jsonObj.get("countryId") != null) {
			envMappingElem.setCountryId(parseStringValue(jsonObj.get("countryId")));
		}		
		if (jsonObj.get("contextPath") != null) {
			envMappingElem.setContextPath(parseStringValue(jsonObj.get("contextPath")));
		}
		if (jsonObj.get("controllerName") != null) {
			envMappingElem.setControllerName(parseStringValue(jsonObj.get("controllerName")));
		}
		if (jsonObj.get("prefix") != null) {
			envMappingElem.setPrefix(parseStringValue(jsonObj.get("prefix")));
		}
		if (jsonObj.get("token") != null) {
			envMappingElem.setToken(parseStringValue(jsonObj.get("token")));
		}
		if (jsonObj.get("uri") != null) {
			envMappingElem.setUri(parseStringValue(jsonObj.get("uri")));
		}

		if (jsonObj.get("domainName") != null) {
			envMappingElem.setDomainName(parseStringValue(jsonObj.get("domainName")));
		}
		
		if (jsonObj.get("isSecure") != null) {
			envMappingElem.setSecure(parseBooleanValue(jsonObj.get("isSecure")));
		}
		
		if (GWTStringUtils.isSet(Location.getQueryString())) {
		    envMappingElem.setQueryParamter(Location.getQueryString());
		}
		
		return envMappingElem;
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

	// ///////////////////////////////////////////////////////////////////////////
	// mutator & accessor
	//
	/**
	 * display token page, no refreshed.
	 * 
	 * @param token
	 */
	public static void forward(final String token) {
		History.newItem(token);
	}

	/**
	 * url is full url, the page is refreshed.
	 * 
	 * @param url
	 */
	public static void redirect(final String url) {
		Window.Location.assign(url);
	}

}
