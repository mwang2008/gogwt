package com.gogwt.rest.service.controller;


import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gogwt.apps.admin.formbean.WeatherForm;
import com.gogwt.apps.admin.formbean.WeatherResponse;
import com.gogwt.rest.service.service.RestDomainService;
import com.gogwt.utils.ToStringUtils;

/**
 * 
 * @Resource usage:
 * 1. first add <context:component-scan base-package="com.gogwt.rest.service.service" /> in appContext.xml
 * 2. define @Component for RestDomainService
 * 3. define @Resource in any usage
 * 
 * @author michael.wang
 *
 */
@Controller
public class RestController {
	protected static Logger logger = Logger.getLogger("RestController");
	
 
	@Resource
	private RestDomainService restDomainService;
	
	 	
	/**
	 * http://localhost:8080/gservice/en/resttest
	 * @return
	 */
	@RequestMapping(value="/resttest", method=RequestMethod.GET,
	headers="Accept=application/html, application/xhtml+xml")
	public @ResponseBody String test() {
		logger.info("==== Spring REST Showcase");
		return "hi RestController";
	}
	
	/**
	 * URL: http://localhost/gservice/en/restweather
	 * Method: POST
	 * Header: Content-Type: application/json
	 * Data: {"zip":"30097", "temperatureType":"F"}
	 * Result: {"state":"GA","description":"Mostly Cloudy","visibility":"","zip":"30097","temperatureType":"F","success":true,"temperature":"72","responseText":"City Found","city":"Duluth","weatherStationCity":"Lawrenceville","weatherID":3,"relativeHumidity":"100","wind":"SE6","pressure":"29.71R","windChill":"","remarks":""}
	 * @param weatherForm
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/restweather", method=RequestMethod.POST, headers="Content-Type=application/json")
	public @ResponseBody WeatherResponse retrieveWeatherPOST(@RequestBody WeatherForm weatherForm, final HttpServletRequest request) {
		logger.info(" ==== retrieveWeatherJSON JSON input: " + ToStringUtils.toString(weatherForm));
		
		restDomainService.getWeather();
	 	
		WeatherResponse response = restDomainService.getWeather(weatherForm);
		response.setZip(weatherForm.getZip());
		response.setTemperatureType(weatherForm.getTemperatureType());
		
		if (response.isSuccess() && StringUtils.equalsIgnoreCase("C", weatherForm.getTemperatureType())) {
			double tempture = Double.parseDouble(response.getTemperature());
			tempture = (tempture-32)*5.0/9.0;			
			
			int temptureInt = (int)(tempture+0.5);
			response.setTemperature(temptureInt+"");
		}
		
	 	return response;
	}
	
	/**
	 * Chrome: Simple REST Client
	 * URL: http://localhost:8080/gservice/en/restweather/30097
	 * Method: GET
	 * Header: Content-Type: application/json
	 * Result: {"state":"GA","description":"Mostly Cloudy","visibility":"","zip":"30097","temperatureType":"F","success":true,"temperature":"72","responseText":"City Found","city":"Duluth","weatherStationCity":"Lawrenceville","weatherID":3,"relativeHumidity":"100","wind":"SE6","pressure":"29.71R","windChill":"","remarks":""}
	 * @param zip
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/restweather/{zip}", method=RequestMethod.GET, headers="Content-Type=application/json")
	public @ResponseBody WeatherResponse retrieveWeatherGET(@PathVariable String zip, final HttpServletRequest request) {
		WeatherForm weatherForm = new WeatherForm();
		weatherForm.setZip(zip);
		weatherForm.setTemperatureType("F");
		
		logger.info(" ==== retrieveWeatherJSON JSON input: " + ToStringUtils.toString(weatherForm));
		
		restDomainService.getWeather();
	 	
		WeatherResponse response = restDomainService.getWeather(weatherForm);
		response.setZip(weatherForm.getZip());
		response.setTemperatureType(weatherForm.getTemperatureType());
		
		if (response.isSuccess() && StringUtils.equalsIgnoreCase("C", weatherForm.getTemperatureType())) {
			double tempture = Double.parseDouble(response.getTemperature());
			tempture = (tempture-32)*5.0/9.0;			
			
			int temptureInt = (int)(tempture+0.5);
			response.setTemperature(temptureInt+"");
		}
		
	 	return response;
	}

	/**
	 * Chrome: Simple REST Client
	 * URL: http://localhost/gservice/en/restweather
	 * Method: PUT
	 * Header: Content-Type: application/json
	 * Data: {"zip":"30097", "temperatureType":"F"}
	 * Result: {"state":"GA","description":"Mostly Cloudy","visibility":"","zip":"30097","temperatureType":"F","success":true,"temperature":"72","responseText":"City Found","city":"Duluth","weatherStationCity":"Lawrenceville","weatherID":3,"relativeHumidity":"100","wind":"SE6","pressure":"29.71R","windChill":"","remarks":""}
	 * @param weatherForm
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/restweather", method=RequestMethod.PUT, headers="Content-Type=application/json")
	public @ResponseBody WeatherResponse retrieveWeatherPUT(@RequestBody WeatherForm weatherForm, final HttpServletRequest request) {
		logger.info(" ==== retrieveWeatherJSON JSON input: " + ToStringUtils.toString(weatherForm));
		
		restDomainService.getWeather();
	 	
		WeatherResponse response = restDomainService.getWeather(weatherForm);
		response.setZip(weatherForm.getZip());
		response.setTemperatureType(weatherForm.getTemperatureType());
		
		if (response.isSuccess() && StringUtils.equalsIgnoreCase("C", weatherForm.getTemperatureType())) {
			double tempture = Double.parseDouble(response.getTemperature());
			tempture = (tempture-32)*5.0/9.0;			
			
			int temptureInt = (int)(tempture+0.5);
			response.setTemperature(temptureInt+"");
		}
		
	 	return response;
	}
}
