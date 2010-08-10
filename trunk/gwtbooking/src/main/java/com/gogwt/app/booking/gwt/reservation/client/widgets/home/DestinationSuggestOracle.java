package com.gogwt.app.booking.gwt.reservation.client.widgets.home;

import java.util.ArrayList;
import java.util.Collection;

import com.gogwt.app.booking.dto.dataObjects.response.SuggestiveDestinationResponseBean;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.rpc.interfaces.reservation.ReservationProcessServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;

public class DestinationSuggestOracle extends SuggestOracle {

	@Override
	public void requestSuggestions(final Request request,
			final Callback callback) {
		String query = request.getQuery().trim();

		// Call RPC if the destination string is more than 3 characters
		if (query.length() >= 3) {
			ReservationProcessServiceAsync service = ReservationProcessServiceAsync.Util
					.getLocationKeyWordsAsync();

			service.getLocationKeyWords(
					query,
					GWTExtClientUtils.getUserContext(),
					new AsyncCallback<ArrayList<SuggestiveDestinationResponseBean>>() {

						public void onFailure(Throwable caught) {
							// doing nothing.
						}

						public void onSuccess(
								ArrayList<SuggestiveDestinationResponseBean> suggestList) {
							Collection<Suggestion> suggestions = new ArrayList<Suggestion>();
							for (final SuggestiveDestinationResponseBean bean : suggestList) {
								suggestions
										.add(new DestinationSuggestion(bean));
							}

							Response response = new Response(suggestions);
							callback.onSuggestionsReady(request, response);
						}
					});

		}

	}

}
