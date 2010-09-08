package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.presenter;

import com.gogwt.app.booking.dto.dataObjects.response.SuggestiveDestinationResponseBean;
import com.google.gwt.user.client.ui.SuggestOracle;

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
