package com.gogwt.app.booking.gwt.reservation.client.navigation;

import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.ProgressBarWidget;
import com.gogwt.app.booking.gwt.reservation.client.widgets.guestinfo.GuestInfoLayoutWidget;
import com.gogwt.framework.arch.widgets.AbstractPage;

public class GuestInformationView extends AbstractPage {

	@Override
	public void process() {
		
		pagePanel.clear();
		
		 //1. add progress bar
		 ProgressBarWidget progressBar = new ProgressBarWidget();
		 progressBar.processDisplayProgressBar(ProcessStatusEnum.SEARCH_RESULT);
		 pagePanel.add(progressBar);

		 //2. add layout
		 GuestInfoLayoutWidget layoutWidget = new GuestInfoLayoutWidget();
		 layoutWidget.prepareEntryLayout();		 
		 pagePanel.add(layoutWidget);
		
	}

}
