/*
 * Copyright 2010 GoGWT.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gogwt.app.booking.controllers.action.jquery;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.gogwt.app.booking.businessService.LookupBusinessService;
import com.gogwt.app.booking.businessService.domainService.ReservationBusinessService;
import com.gogwt.app.booking.controllers.BaseAbstractController;
import com.gogwt.app.booking.controllers.action.HotelSearchController;
import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;
import com.gogwt.app.booking.exceptions.clientserver.InvalidateGeocodeException;
import com.gogwt.app.booking.scopeManager.session.SessionBeanLookupService;

/**
 * <code><B>JHotelSearchController<code><B>
 * 
 * Created for practice of JQuery
 * <p/>
 */

public class JHotelSearchController extends BaseAbstractController {
	private static Logger logger = Logger.getLogger(JHotelSearchController.class);

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
		ReservationBusinessService reservationBusinessService = null;
		HotelSearchResponseBean hotelSearchResponse = null;
		try {
		   reservationBusinessService = LookupBusinessService.getReservationBusinessService();
		   hotelSearchResponse = reservationBusinessService.findHotelsWithLocation(searchFormBean);
		}
		catch (AppRemoteException e) {
			if (e instanceof InvalidateGeocodeException) {
				errors.reject("error.invalid.geocode");
				return super.showForm(request, response, errors, null);
			}
		}
		
		
		//2. has result, redirect to result page
		if (hotelSearchResponse != null && hotelSearchResponse.hasSearchResult()) {
			SessionBeanLookupService.getReservationSessionManager().getReservationContainerBean().setHotelSearchRequest(searchFormBean);
			SessionBeanLookupService.getReservationSessionManager().getReservationContainerBean().setHotelSearchResponse(hotelSearchResponse);
			SessionBeanLookupService.getReservationSessionManager().getReservationContainerBean().setStatus(ProcessStatusEnum.SEARCH_RESULT);
			
			String targetURL = getSuccessView();
			return new ModelAndView(new RedirectView(targetURL));
		}
		
		//display error message
		errors.reject("error.no.search.result");
		return super.showForm(request, response, errors, null);
		 
	}


}
