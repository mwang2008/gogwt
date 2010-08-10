package com.gogwt.app.booking.controllers.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.gogwt.app.booking.businessService.domainService.CommonBusinessService;
import com.gogwt.app.booking.businessService.domainService.LookupBusinessService;
import com.gogwt.app.booking.controllers.ControllerHelper;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.utils.StringUtils;

public class HotelDetailController extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String propertyId = request.getParameter("propertyId");

		if (StringUtils.isSet(propertyId)) {
			int id = Integer.parseInt(propertyId);
			
			UserContextBean userContext = ControllerHelper.getUserContext(request);

			CommonBusinessService commonBusinessService = 
				LookupBusinessService.getCommonBusinessService();

			final HotelBean hotel = commonBusinessService.getCommonDAO()
					.getHotelDetail(id, userContext);

			final ModelMap modelMap = new ModelMap();

			modelMap.addAttribute("detail", hotel);

			return new ModelAndView("/hotel/detail").addAllObjects(modelMap);
		}
		
		return new ModelAndView("/hotel/detail");
		
		
	}

}
