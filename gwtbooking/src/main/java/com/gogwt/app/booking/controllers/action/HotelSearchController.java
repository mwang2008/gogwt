package com.gogwt.app.booking.controllers.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.gogwt.app.booking.businessService.domainService.LookupBusinessService;
import com.gogwt.app.booking.businessService.domainService.ReservationBusinessService;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.scopeManager.session.SessionBeanLookupService;
 

public class HotelSearchController extends SimpleFormController {
	private static Logger logger = Logger.getLogger(HotelSearchController.class);

	@Override
	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {

		SearchFormBean searchFormBean = new SearchFormBean();

		return searchFormBean;
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

		return handleSearchHotel(request, response, (SearchFormBean) command,
				errors);
	}

	/**
	 * Handle user request
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, Object,
	 *      org.springframework.validation.BindException)
	 */
	private ModelAndView handleSearchHotel(final HttpServletRequest request,
			final HttpServletResponse response,
			final SearchFormBean searchFormBean, final BindException errors) throws Exception {
		
		//1. call domain service to search for hotel
		ReservationBusinessService reservationBusinessService = LookupBusinessService.getReservationBusinessService();
		HotelSearchResponseBean hotelSearchResponse = reservationBusinessService.findHotelsWithLocation(searchFormBean);
		
		//2. has result, redirect to result page
		if (hotelSearchResponse != null && hotelSearchResponse.hasSearchResult()) {
			SessionBeanLookupService.getReservationSessionManager().setSearchFormBean(searchFormBean);
			SessionBeanLookupService.getReservationSessionManager().setHotelSearchResponse(hotelSearchResponse);
			String targetURL = getSuccessView();
			return new ModelAndView(new RedirectView(targetURL));
		}
		
		return super.showForm(request, response, errors, null);
		 
	}

}
