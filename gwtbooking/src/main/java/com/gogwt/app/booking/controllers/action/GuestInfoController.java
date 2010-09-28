package com.gogwt.app.booking.controllers.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.gogwt.app.booking.businessService.domainService.LookupBusinessService;
import com.gogwt.app.booking.businessService.domainService.ReservationBusinessService;
import com.gogwt.app.booking.controllers.BaseAbstractController;
import com.gogwt.app.booking.controllers.ControllerHelper;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.dto.dataObjects.common.ReservationContainerBean;
import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;
import com.gogwt.app.booking.scopeManager.session.SessionBeanLookupService;
 

/**
 * GuestInfo
 * @author WangM
 *
 */
public class GuestInfoController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(GuestInfoController.class);
	
	@Override
	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {

		GuestInfoFormBean guestInfoFormBean = new GuestInfoFormBean();

		return guestInfoFormBean;
	}

	@Override
	protected ModelAndView showForm(final HttpServletRequest request,
			final HttpServletResponse response, final BindException errors,
			final Map controlModel) throws Exception {
		logger.debug(" - In showForm()");

		return super.showForm(request, response, errors, controlModel);
	}

	/**
	 * Call before onSubmit with/without error.
	 * 
	 * @see com.ihg.dec.apps.dotcom.actions.AbstractDotcomController#processFormSubmission(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, Object,
	 *      org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView processFormSubmission(
			final HttpServletRequest request,
			final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception {

		logger.debug("processFormSubmission");

		// add logic to validate input or business rule.

		// super process
		return super.processFormSubmission(request, response, command, errors);
	}

	/**
	 * Handle form request
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, Object,
	 *      org.springframework.validation.BindException)
	 */
	@Override
	public ModelAndView onSubmit(final HttpServletRequest request,
			final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception {
		logger.debug("ModelAndView onSubmit(request, response, command)");

		return confirmReservation(request, response, (GuestInfoFormBean) command,
				errors);
	}

	/**
	 * Handle user request
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, Object,
	 *      org.springframework.validation.BindException)
	 */
	private ModelAndView confirmReservation(final HttpServletRequest request,
			final HttpServletResponse response,
			final GuestInfoFormBean guestFormBean, final BindException errors) throws Exception {
		
	
		//1. call domain service to search for hotel
		ReservationBusinessService reservationBusinessService = LookupBusinessService.getReservationBusinessService();
		 
		int index = Integer.parseInt(request.getParameter("index")); //guestFormBean.getId();
		HotelBean selectedHotel = SessionBeanLookupService.getReservationSessionManager().getReservationContainerBean().getHotelSearchResponse().getHotelList().get(index);
		
		ReserveResponseBean reserveResponseBean = reservationBusinessService.confirmReservation(guestFormBean, selectedHotel, ControllerHelper.getUserContext(request));
		
		ReservationContainerBean reservationContainerBean = SessionBeanLookupService.getReservationSessionManager().getReservationContainerBean();
		
		//3. set request/response to session
		reservationContainerBean.setStatus(ProcessStatusEnum.CONFIRMATION);
		reservationContainerBean.setGuestInfoBean(guestFormBean);
		reservationContainerBean.setReserveResponse(reserveResponseBean);
		reservationContainerBean.setSelectHotelItem(index);

		String targetURL = getSuccessView();
		return new ModelAndView(new RedirectView(targetURL));
 		 
	}


}
