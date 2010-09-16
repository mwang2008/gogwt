package com.gogwt.app.booking.dto.dataObjects.response;

import java.util.List;

import com.gogwt.app.booking.dto.dataObjects.BaseBean;
import com.gogwt.app.booking.dto.dataObjects.common.KeywordBean;

public class SuggestiveDestinationResponseBean extends BaseBean {
	private List<KeywordBean> keywordList;
	private String suggestedDestination;

	
	public List<KeywordBean> getKeywordList() {
		return keywordList;
	}

	public void setKeywordList(List<KeywordBean> keywordList) {
		this.keywordList = keywordList;
	}

	public String getSuggestedDestination() {
		return suggestedDestination;
	}

	public void setSuggestedDestination(String suggestedDestination) {
		this.suggestedDestination = suggestedDestination;
	}
	
	
}
