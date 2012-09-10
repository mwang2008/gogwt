package com.gogwt.app.booking.gwt.reservation.client.navigation;

import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.ProgressBarWidget;
import com.gogwt.app.booking.gwt.reservation.client.widgets.guestinfo.GuestInfoLayoutWidget;
import com.gogwt.framework.arch.widgets.AbstractController;
import com.gogwt.framework.arch.widgets.PageMetaInfo;

public class GuestInformationView extends AbstractController {

	@Override
	public void process() {

		controlPanel.clear();

		// 1. add progress bar
		ProgressBarWidget progressBar = new ProgressBarWidget();
		progressBar.processDisplayProgressBar(ProcessStatusEnum.GUEST_INFO);
		controlPanel.add(progressBar);

		// 2. add layout
		GuestInfoLayoutWidget layoutWidget = new GuestInfoLayoutWidget();
		layoutWidget.prepareEntryLayout();
		controlPanel.add(layoutWidget);

	}

	@Override
	protected void fillMetaInfo(PageMetaInfo pageInfo) {
		// TODO Auto-generated method stub

	}

}
