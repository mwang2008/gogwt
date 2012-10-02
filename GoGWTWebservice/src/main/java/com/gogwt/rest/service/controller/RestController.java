package com.gogwt.rest.service.controller;


import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
