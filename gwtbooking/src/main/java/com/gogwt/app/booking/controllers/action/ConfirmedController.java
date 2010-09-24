package com.gogwt.app.booking.controllers.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.scopeManager.session.SessionBeanLookupService;

public class ConfirmedController extends AbstractController  {

	@Override
	protected ModelAndView handleRequestInternal(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	    
		logger.debug("handleRequestInternal");
		
        final ReservationContainerBean reservationContainerBean = SessionBeanLookupService.getReservationSessionManager().getReservationContainerBean();
		
        final ReserveResponseBean reserveResponse = reservationContainerBean.getReserveResponse();
		  
        final ModelMap modelMap = new ModelMap();
        modelMap.addAttribute( "reservation", reserveResponse );
        
	    return new ModelAndView("/reservation/confirmed_reservation").addAllObjects(modelMap);
	}
}
