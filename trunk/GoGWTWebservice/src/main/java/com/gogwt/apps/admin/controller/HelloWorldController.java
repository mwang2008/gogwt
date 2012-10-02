package com.gogwt.apps.admin.controller;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * http://localhost:8080/gservice/en/hello
 * @author michael.wang
 *
 */
@Controller
@RequestMapping("/hello")
public class HelloWorldController {
	protected static Logger logger = Logger.getLogger("HelloWorldController");
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView helloWorld(){
		logger.info("==== Spring HelloWorldController helloWorld");
		
		ModelAndView model = new ModelAndView("hello_world");
		model.addObject("msg", "hello world");
 
		return model;
	}
}
