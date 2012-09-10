package org.krams.tutorial.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.krams.tutorial.domain.PersonList;
import org.krams.tutorial.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gogwt.app.booking.dto.dataObjects.common.KeywordBean;
import com.gogwt.app.booking.webservice.rest.domain.KeywordList;

@Controller
public class MyRestProviderController {
	protected static Logger logger = Logger
			.getLogger("MyRestProviderController");

	@Resource(name = "personService")
	private PersonService personService;

	@RequestMapping(value = "/mypersons", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	PersonList getPerson() {
		logger.debug("Provider has received request to get all persons");

		// Call service here
		PersonList result = new PersonList();
		result.setData(personService.getAll());

		return result;
	}

	@RequestMapping(value = "/myperson/{id}", method = RequestMethod.GET, headers = "Accept=application/html, application/xhtml+xml")
	public String getPersonHTML(@PathVariable("id") Long id, Model model) {
		logger.debug("Provider has received request to get person with id: "
				+ id);

		// Call service to here
		model.addAttribute("person", personService.get(id));

		// This will resolve to /WEB-INF/jsp/getpage.jsp
		return "getpage";
	}

	/**
	 * http://localhost/gwtbooking/en-us/keywords/atl
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/keywords/{key}", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	KeywordList getPersons(@PathVariable("key") String key) {
		logger.debug("Provider has received request to get all persons");
        System.out.println(" ==== key=" + key);
        
		// Call service here
		KeywordList result = new KeywordList();
		List<KeywordBean> sl = new ArrayList<KeywordBean>();
		KeywordBean keywordBean = new KeywordBean();
		keywordBean.setKeyword("Atlanta, ga");
		keywordBean.setLat(23.4444);
		keywordBean.setLng(-83.000);
		keywordBean.setSearchkey("atl");
		keywordBean.setType("city");
		
		sl.add(keywordBean);
		
		keywordBean = new KeywordBean();
		keywordBean.setKeyword("Rome, ga");
		keywordBean.setLat(23.4444);
		keywordBean.setLng(-83.000);
		keywordBean.setSearchkey("atl");
		keywordBean.setType("city");
		sl.add(keywordBean);
		
		result.setData(sl);
		//result.setData(personService.getAll());

		return result;
	}

	/*
	@RequestMapping(value = "/keywords", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	public String getKeywords() {
		logger.debug("Provider has received request to get keywords: "
				);
		System.out.println(" ==== keywords: ");

		// Call service here

		return "null";
	}
	*/
}
