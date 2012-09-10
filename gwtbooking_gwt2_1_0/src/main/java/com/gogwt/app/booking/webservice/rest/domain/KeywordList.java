package com.gogwt.app.booking.webservice.rest.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.gogwt.app.booking.dto.dataObjects.common.KeywordBean;

@XmlRootElement(name="keywords")
public class KeywordList {
	private List<KeywordBean> data;

	public List<KeywordBean> getData() {
		return data;
	}

	public void setData(List<KeywordBean> data) {
		this.data = data;
	}
}
