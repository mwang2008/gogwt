package com.gogwt.app.booking.controllers.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.gogwt.app.booking.businessService.domainService.CommonBusinessService;
import com.gogwt.app.booking.businessService.domainService.LookupBusinessService;
import com.gogwt.app.booking.controllers.BaseAbstractController;
import com.gogwt.app.booking.controllers.ControllerHelper;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.framework.arch.utils.StringUtils;

public class HotelDetailController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(HotelDetailController.class);
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String propertyId = request.getParameter("propertyId");

		if (StringUtils.isSet(propertyId)) {
			int id = Integer.parseInt(propertyId);
			
			UserContextBean userContext = ControllerHelper.getUserContext(request);

			CommonBusinessService commonBusinessService = 
				LookupBusinessService.getCommonBusinessService();

			//final HotelBean hotel = commonBusinessService.getCommonDAO()
			//		.getHotelDetail(id, userContext);

			final HotelBean hotel = commonBusinessService.getHotelDetail(id, userContext);
			
			final ModelMap modelMap = new ModelMap();

			modelMap.addAttribute("detail", hotel);

			return new ModelAndView("/hotel/detail").addAllObjects(modelMap);
		}
		
		return new ModelAndView("/hotel/detail");
		
		
	}

}
