package com.gogwt.app.booking.gwt.common.widget.populator;

import java.util.List;

import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;

public interface Populator {
	 /**
	   * <p>
	   * This method must be overridden to populate widget with the data
	   * </p>
	   * @param data
	   */
	  void populate(List<PopulatorItem> data);
}
