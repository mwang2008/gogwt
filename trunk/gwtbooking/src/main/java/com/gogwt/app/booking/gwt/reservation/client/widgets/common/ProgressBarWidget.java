package com.gogwt.app.booking.gwt.reservation.client.widgets.common;

import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.framework.arch.widgets.BaseWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
 

public class ProgressBarWidget extends BaseWidget {
	private Panel mainPanel = WidgetStyleUtils.createFlowPanelWithId("progressBar");
	
	/**
	 * <p> Create a new instance of ProgressBarWidget. </p>
	 */
	public ProgressBarWidget() {
		initWidget(mainPanel);
	}

	public void processDisplayProgressBar(ProcessStatusEnum status) {
	 	mainPanel.clear();

	 	mainPanel.add(new Label(" prograss bar"));
	}
}
