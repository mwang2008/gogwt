package com.gogwt.app.booking.webservice.rest.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gogwt.app.booking.webservice.rest.domain.KeywordList;

@Controller
public class BookingRestProviderController {
	private static Logger logger = Logger
			.getLogger(BookingRestProviderController.class);

	@RequestMapping(value = "/keywords{keyword}", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	KeywordList getKeywords(@PathVariable("keyword") String keyword) {
		logger.debug("Provider has received request to get keywords: "
				+ keyword);
		System.out.println(" ==== keywords: " + keyword);

		// Call service here

		return null;
	}
}
