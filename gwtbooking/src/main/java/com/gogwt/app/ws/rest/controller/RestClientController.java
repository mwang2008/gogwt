package com.gogwt.app.ws.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.gogwt.app.booking.dto.dataObjects.common.KeywordBean;
import com.gogwt.app.booking.webservice.rest.domain.KeywordList;

/**
 * 
 * <code><B>RestClientController<code><B>
 * Webpage used to retrieve REST service by using RestTemplate
 *  
 * <p/>
 */

@Controller
public class RestClientController {
protected static Logger logger = Logger.getLogger("controller");
	
	private RestTemplate restTemplate = new RestTemplate();
	
	/**
	 * Retrieves a single record from the REST provider
	 * and displays the result in a JSP page
	 * http://localhost/gwtbooking/en-us/getsuggestion?searchKey=atl
	 */
	@RequestMapping(value = "/getsuggestion", method = RequestMethod.GET)
	public String getSuggestion(@RequestParam("searchKey") String searchKey, Model model) {
		logger.debug("Retrieve suggestion with id: " + searchKey);
		
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		//acceptableMediaTypes.add(MediaType.APPLICATION_XML);
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);  //not working 406 
		
		// Prepare header
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(acceptableMediaTypes);
		HttpEntity<KeywordBean> entity = new HttpEntity<KeywordBean>(headers);

		// Send the request as GET
		try {
			String url = "http://localhost/gwtbooking/en-us/getkeywords/" + searchKey;
			ResponseEntity<KeywordList> result = restTemplate.exchange(url, HttpMethod.GET, entity, KeywordList.class);
			// Add to model
			model.addAttribute("keywords", result.getBody().getData());
			
		} catch (Exception e) {
			logger.error(e);
		}
		
		// This will resolve to /jsp/suggestion.jsp
		return "suggestion";
	}
}
