package com.gogwt.app.booking.rpc.services.reservation;

import java.util.List;

import com.gogwt.app.booking.businessService.domainService.LookupBusinessService;
import com.gogwt.app.booking.businessService.domainService.ReservationBusinessService;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.KeywordBean;
import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;
import com.gogwt.app.booking.scopeManager.session.SessionBeanLookupService;


/**
 * RPC implementation for
 * 1) find keyword
 * 2) search hotel
 * @author WangM
 *
 */
public class HotelSearchRPCController extends ReservationProcessServiceAdapter {

	public List<KeywordBean> getLocationKeyWords(
			String destination, UserContextBean userContext)
			throws AppRemoteException {
		
		List<KeywordBean> keywordList = 
		    LookupBusinessService.getCommonBusinessService().getKeywordList(destination, 10);
		
	  
		if (keywordList == null || keywordList.isEmpty() ) {
			return null;
		}
 		
		return keywordList;
	}
	
	/**
	 * Search hotel
	 */
	public HotelSearchResponseBean searchHotels(
			SearchFormBean searchFormBean, UserContextBean userContext)
			throws AppRemoteException {
		
		//1. call domain service
		ReservationBusinessService reservationBusinessService = LookupBusinessService.getReservationBusinessService();
		HotelSearchResponseBean hotelSearchResponse = reservationBusinessService.findHotelsWithLocation(searchFormBean);
		
		//2. set request/response to session
		SessionBeanLookupService.getReservationSessionManager().getReservationContainerBean().setStatus(ProcessStatusEnum.SEARCH_RESULT);
		SessionBeanLookupService.getReservationSessionManager().getReservationContainerBean().setHotelSearchRequest(searchFormBean);
		SessionBeanLookupService.getReservationSessionManager().getReservationContainerBean().setHotelSearchResponse(hotelSearchResponse);
		
		
		return hotelSearchResponse;
	}
}
