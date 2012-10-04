package com.gogwt.rest.service.controller;


import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
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
	 * URL: http://localhost:8080/pservice/en/restweather
	 * @param weatherForm
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/restweather", method=RequestMethod.POST, headers="Content-Type=application/json")
	public @ResponseBody WeatherResponse retrieveWeatherJSON(@RequestBody WeatherForm weatherForm, final HttpServletRequest request) {
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
