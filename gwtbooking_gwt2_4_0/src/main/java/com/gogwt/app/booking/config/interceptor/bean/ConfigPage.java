package com.gogwt.app.booking.config.interceptor.bean;

import java.util.ArrayList;
import java.util.List;

import com.gogwt.app.booking.utils.ToStringUtils;

public class ConfigPage {

	private String pageName;
	private String pageClass;

	private List<PopulatorEntry> populatorList;

	public void addPopulator(final String key, final String entry) {
		if (populatorList == null) {
			populatorList = new ArrayList<PopulatorEntry>();
		}

		populatorList.add(new PopulatorEntry(key, entry));
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPageClass() {
		return pageClass;
	}

	public void setPageClass(String pageClass) {
		this.pageClass = pageClass;
	}

	/**
	 * <p>
	 * See {@link #setpopulatorList(List<populator>)}
	 * </p>
	 * 
	 * @return Returns the populatorList.
	 */
	public List<PopulatorEntry> getPopulatorList() {
		return populatorList;
	}

	/**
	 * <p>
	 * Set the value of <code>populatorList</code>.
	 * </p>
	 * 
	 * @param populatorList
	 *            The populatorList to set.
	 */
	public void setPopulatorList(List<PopulatorEntry> populatorList) {
		this.populatorList = populatorList;
	}

	public String toString() {
		return ToStringUtils.toString(this);
	}

}
