package com.gogwt.app.booking.controllers;

import static com.gogwt.app.booking.BookingConstants.ENV;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.gogwt.app.booking.config.urlmapping.UrlMappingElem;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.populator.BasePopulator;

public class BaseAbstractFormController extends SimpleFormController {
	private Map<String, BasePopulator> populators;

	/**
	 * <p>
	 * Overrites referenceData of SimpleFormController to process form populator
	 * </p>
	 * 
	 * @param request
	 *            - the request used to obtain the url mapping element.
	 * @param command
	 *            - the comand of form object.
	 * @param errors
	 *            - the error object.
	 */
	@Override
	protected Map referenceData(final HttpServletRequest request,
			final Object command, final Errors errors) throws Exception {
		ModelMap map = null;
		String key = null;

		if (populators != null) {
			map = new ModelMap();
			final Set<String> keySet = populators.keySet();
			final Iterator<String> iterator = keySet.iterator();

			BasePopulator populator = null;
			while (iterator.hasNext()) {
				key = iterator.next();
				populator = populators.get(key);
				map.addAttribute(key, populator.getPopulator(request));
			}
		}
		return map;
	}


	
	public Map<String, BasePopulator> getPopulators() {
		return populators;
	}

	public void setPopulators(Map<String, BasePopulator> populators) {
		this.populators = populators;
	}

	

}
