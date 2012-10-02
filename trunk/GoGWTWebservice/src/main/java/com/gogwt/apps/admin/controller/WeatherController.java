package com.gogwt.apps.admin.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;

import com.gogwt.apps.admin.formbean.WeatherForm;
import com.gogwt.apps.admin.formbean.WeatherResponse;
import com.gogwt.apps.admin.service.RestDomainService;
import com.gogwt.utils.ToStringUtils;

/**
 * 
 * http://hq-dt048:8080/gservice/en/weather
 * @author michael.wang
 *
 */
@Controller
@RequestMapping("/weather")
public class WeatherController {
	protected static Logger logger = Logger.getLogger("WeatherController");
	
	//@Resource 
	//private RestDomainService service;
	
	/**
	 * Similar: protected Object formBackingObject(HttpServletRequest request)
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model){
		logger.info("=== initForm ===");
		
		WeatherForm form = new WeatherForm();
		
		
		//command object
		model.addAttribute("weather", form);
 
		//return form view, jsp name
		return "weather_form";
	}
	
	/**
	 * Similar: protected Map referenceData(HttpServletRequest request)
	 * @return
	 */
	@ModelAttribute("temperatureTypes")
	public Map<String,String> populateWebFrameworkList() {
 
		
		Map<String,String> temperatureTypes = new LinkedHashMap<String,String>();
		temperatureTypes.put("F", "Fahrenhet");
		temperatureTypes.put("C", "Celsius");
		
		return temperatureTypes;
	}
	
	/**
	 * Similar: protected ModelAndView onSubmit(HttpServletRequest request,
	 * @param customer
	 * @param result
	 * @param status
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(
		//@ModelAttribute("weather") WeatherForm form, BindingResult result, SessionStatus status) {
		@Valid@ModelAttribute("weather") WeatherForm form, BindingResult result, SessionStatus status, final HttpServletRequest request) {
		
		logger.info("=== processSubmit ===");
		//service.getWeather();
		
		System.out.println(" zip=" + form.getZip() + ", temperatureType=" + form.getTemperatureType());
		
		if (result.hasErrors()) {
			return "weather_form";
		}	
		
		int port = request.getServerPort();
		String url = "";
		if (port == 80) {
		   url = "http://localhost"+request.getContextPath()+"/en/restweather";
		}
		else {
		   url = "http://localhost:" + port +  request.getContextPath()+"/en/restweather";
		}
		
		logger.info(" rest url=" + url);
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<WeatherForm> requestEntity = new HttpEntity<WeatherForm>(form, requestHeaders);
		
		
		ResponseEntity<WeatherResponse> response = restTemplate.exchange(
				url, HttpMethod.POST, requestEntity,
				WeatherResponse.class);
		
		
		WeatherResponse weatherResponse = response.getBody();
		System.out.println("-- result:  " + ToStringUtils.toString(weatherResponse));
		
		 
		
		//clear the command object from the session
		status.setComplete();
		
		
		//return form success view
		return "weather_success";
 
	}
	
	
}
