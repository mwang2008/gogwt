package com.gogwt.app.booking.rpc.services.reservation;

import java.util.ArrayList;
import java.util.List;

import com.gogwt.app.booking.businessService.domainService.LookupBusinessService;
import com.gogwt.app.booking.businessService.domainService.ReservationBusinessService;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.KeywordBean;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.dto.dataObjects.response.SuggestiveDestinationResponseBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;


public class HotelSearchRPCController extends ReservationProcessServiceAdapter {

	public ArrayList<SuggestiveDestinationResponseBean> getLocationKeyWords(
			String destination, UserContextBean userContext)
			throws AppRemoteException {
		
		List<KeywordBean> keywordList = 
		    LookupBusinessService.getCommonBusinessService().getKeywordList(destination, 10);
		
		
		ArrayList<SuggestiveDestinationResponseBean> retList = new ArrayList<SuggestiveDestinationResponseBean>();

		if (keywordList == null || keywordList.isEmpty() ) {
			return null;
		}
		
		SuggestiveDestinationResponseBean suggestiveDestination = null;
		for (KeywordBean keywordBean : keywordList) {
			suggestiveDestination = new SuggestiveDestinationResponseBean();
			suggestiveDestination.setSuggestedDestination(keywordBean.getKeyword());
			retList.add(suggestiveDestination);
		}
		
		/*
		SuggestiveDestinationResponseBean suggestiveDestination = new SuggestiveDestinationResponseBean();		
		suggestiveDestination.setSuggestedDestination("Atlanta");
		retList.add(suggestiveDestination);

		suggestiveDestination = new SuggestiveDestinationResponseBean();
		suggestiveDestination.setSuggestedDestination("Atlantic");
		retList.add(suggestiveDestination);

		suggestiveDestination = new SuggestiveDestinationResponseBean();
		suggestiveDestination.setSuggestedDestination("Atlantis");
		retList.add(suggestiveDestination);

		suggestiveDestination = new SuggestiveDestinationResponseBean();
		suggestiveDestination.setSuggestedDestination("Atlda");
		retList.add(suggestiveDestination);

		suggestiveDestination = new SuggestiveDestinationResponseBean();
		suggestiveDestination.setSuggestedDestination("Atlzs");
		retList.add(suggestiveDestination);

		suggestiveDestination = new SuggestiveDestinationResponseBean();
		suggestiveDestination.setSuggestedDestination("Atlanta downtown");
		retList.add(suggestiveDestination);
        */
		
		return retList;
	}
	
	public HotelSearchResponseBean searchHotels(
			SearchFormBean searchFormBean, UserContextBean userContext)
			throws AppRemoteException {
		
		ReservationBusinessService reservationBusinessService = LookupBusinessService.getReservationBusinessService();
		HotelSearchResponseBean hotelSearchResponse = reservationBusinessService.findHotelsWithLocation(searchFormBean);
		
		return hotelSearchResponse;
	}
}
