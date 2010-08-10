package com.gogwt.app.booking.gwt.reservation.client.widgets.home;

import com.gogwt.app.booking.dto.dataObjects.response.SuggestiveDestinationResponseBean;
import com.google.gwt.user.client.ui.SuggestOracle;

/**
 * 
 * @author WangM
 *
 */
public class DestinationSuggestion implements SuggestOracle.Suggestion {
	private SuggestiveDestinationResponseBean suggestiveDestinationResponse;
	
	
	public DestinationSuggestion(
			SuggestiveDestinationResponseBean suggestiveDestinationResponse) {
		super();
		this.suggestiveDestinationResponse = suggestiveDestinationResponse;
	}

	 
	public String getDisplayString() {
		return suggestiveDestinationResponse.getSuggestedDestination();
	}

 
	public String getReplacementString() {
		return suggestiveDestinationResponse.getSuggestedDestination();
	}

	
}
