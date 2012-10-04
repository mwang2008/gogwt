package com.gogwt.apps.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gogwt.apps.admin.formbean.WeatherResponse;

@Controller
@RequestMapping("/weatherresult")
public class WeatherResultController {
	protected static Logger logger = Logger.getLogger("WeatherResultController");
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView  initForm(ModelMap model, final HttpServletRequest request){
		
		WeatherResponse weatherResponse = (WeatherResponse)request.getSession().getAttribute("resultObj");
		ModelAndView mv = new ModelAndView("weather_result");
		
		mv.addObject("weatherResult", weatherResponse);
		
		//return form view, jsp name
		//return "weather_result";
		
		return mv;
	}
}
