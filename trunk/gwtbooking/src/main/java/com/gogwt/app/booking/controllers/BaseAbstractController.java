package com.gogwt.app.booking.controllers;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.gogwt.app.booking.populator.Populator;

public class BaseAbstractController extends SimpleFormController {
	public static final String SUCCESS_URL = "successURL";
	
	private Map<String, Populator> populators;
    private String controllerName;
    
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

			Populator populator = null;
			while (iterator.hasNext()) {
				key = iterator.next();
				populator = populators.get(key);
				map.addAttribute(key, populator.getPopulator(request));
			}
		}
		return map;
	}


	
	public Map<String, Populator> getPopulators() {
		return populators;
	}

	public void setPopulators(Map<String, Populator> populators) {
		this.populators = populators;
	}



	public String getControllerName() {
		return controllerName;
	}



	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getSuccessURL( final HttpServletRequest request )
	  {
	    return request.getParameter( SUCCESS_URL );
	  }
	

}
