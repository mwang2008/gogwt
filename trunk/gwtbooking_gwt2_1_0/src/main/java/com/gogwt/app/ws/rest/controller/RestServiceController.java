package com.gogwt.app.ws.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gogwt.app.booking.businessService.domainService.LookupBusinessService;
import com.gogwt.app.booking.dto.dataObjects.common.KeywordBean;
import com.gogwt.app.booking.webservice.rest.domain.KeywordList;

@Controller
public class RestServiceController {
	protected static Logger logger = Logger.getLogger("RestServiceController");
	
	/**
	 * http://localhost/gwtbooking/en-us/getkeywords/atl
	 * @RequestParam("searchKey") String searchKey
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/getkeywords", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	//@RequestMapping(value = "/getkeywords/{key}", method = RequestMethod.GET)
	public @ResponseBody
	//KeywordList getPersons(@PathVariable("key") String key) {
	KeywordList getPersons(@RequestParam("key") String key, final HttpServletRequest request, final HttpServletResponse response) {
		logger.debug("Provider has received request to get all persons");
        System.out.println(" ==== key=" + key + ". hostname=" + request.getRemoteHost());
        
    	List<KeywordBean> keywordList = 
		    LookupBusinessService.getCommonBusinessService().getKeywordList(key, 10);
		
    	
	  
		if (keywordList == null || keywordList.isEmpty() ) {
			return null;
		}
 		
		//return keywordList;
		
		// Call service here
		KeywordList result = new KeywordList();
		List<KeywordBean> sl = new ArrayList<KeywordBean>();
		
		/*
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
		
		keywordBean = new KeywordBean();
		keywordBean.setKeyword("Alpharetta, ga");
		keywordBean.setLat(23.4444);
		keywordBean.setLng(-83.000);
		keywordBean.setSearchkey("atdl");
		keywordBean.setType("city");
		sl.add(keywordBean);
		*/
		
		//add list from db
		sl.addAll(keywordList);
		
		result.setData(sl);
		//result.setData(personService.getAll());

		
		
		return result;
	}
}
