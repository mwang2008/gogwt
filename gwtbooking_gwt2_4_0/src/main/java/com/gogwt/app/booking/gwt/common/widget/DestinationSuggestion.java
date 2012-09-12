package com.gogwt.app.booking.gwt.common.widget;

import com.gogwt.app.booking.dto.dataObjects.common.KeywordBean;
import com.google.gwt.user.client.ui.SuggestOracle;

public class DestinationSuggestion implements SuggestOracle.Suggestion {
	private KeywordBean keywordBean;

	public DestinationSuggestion(KeywordBean keywordBean) {
		super();
		this.keywordBean = keywordBean;
	}

	public String getDisplayString() {
		return keywordBean.getKeyword();
	}

	public String getReplacementString() {
		return keywordBean.getKeyword();
	}

	public KeywordBean getKeywordBean() {
		return keywordBean;
	}

	public void setKeywordBean(KeywordBean keywordBean) {
		this.keywordBean = keywordBean;
	}
	
	
}
