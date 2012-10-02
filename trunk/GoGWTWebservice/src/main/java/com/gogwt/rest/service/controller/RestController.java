package com.gogwt.rest.service.controller;


import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gogwt.apps.admin.formbean.WeatherForm;
import com.gogwt.apps.admin.formbean.WeatherResponse;
import com.gogwt.utils.ToStringUtils;

/**
 * 
 * @author michael.wang
 *
 */
@Controller
public class RestController {
	protected static Logger logger = Logger.getLogger("RestController");
	
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
	
	@RequestMapping(value="/restweather", method=RequestMethod.POST, headers="Content-Type=application/json")
	public @ResponseBody WeatherResponse retrieveWeatherJSON(@RequestBody WeatherForm weatherForm, final HttpServletRequest request) {
		logger.info(" ==== retrieveWeatherJSON JSON input: " + ToStringUtils.toString(weatherForm));
		
		WeatherResponse response = new WeatherResponse();
		response.setCity("Atlanta");
	 	return response;
	}

}
