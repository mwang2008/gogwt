package com.gogwt.app.booking.dto.dataObjects.common;

import com.gogwt.framework.arch.dto.BaseBean;



public class StateBean extends BaseBean {
	private int id;
	private String stateId;
	private String stateName;
    private String languageId;
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}
 
}
