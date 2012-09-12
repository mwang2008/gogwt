package com.gogwt.app.booking.gwt.common.widget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.gogwt.app.booking.dto.dataObjects.common.KeywordBean;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.rpc.interfaces.reservation.ReservationProcessServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;

public class DestinationSuggestOracle extends SuggestOracle { 
	@Override
	public boolean isDisplayStringHTML() {
	    return true;
	}
	
	@Override
	public void requestSuggestions(final Request request,
			final Callback callback) {
		String query = request.getQuery().trim();

		/*-----------------------------------------------------------------+
		 | Call RPC: 
		 | if the first character if digital.
		 | if the destination string is more than 3 characters and 
		 +-----------------------------------------------------------------*/
		if (Character.isDigit(query.charAt(0))) {
			return;
		}
		
		// Call RPC if the destination string is more than 3 characters
		if (query.length() >= 3) {
			ReservationProcessServiceAsync service = ReservationProcessServiceAsync.Util
					.getLocationKeyWordsAsync();

			service.getLocationKeyWords(
					query,
					GWTExtClientUtils.getUserContext(),
					new AsyncCallback<List<KeywordBean>>() {

						public void onFailure(Throwable caught) {
							// doing nothing.
						}

						public void onSuccess(List<KeywordBean> suggestList) {
							
							Collection<Suggestion> suggestions = new ArrayList<Suggestion>();
							
							for (final KeywordBean bean : suggestList) {
								suggestions.add(new DestinationSuggestion(bean));
							}

							Response response = new Response(suggestions);
							callback.onSuggestionsReady(request, response);
						}
					});

		}

	}


}
