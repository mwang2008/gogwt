package com.gogwt.app.booking.dto.dataObjects;


public class UserContextBean extends BaseBean {
	private String languageId; // en
	private String countryId; // US

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

}
