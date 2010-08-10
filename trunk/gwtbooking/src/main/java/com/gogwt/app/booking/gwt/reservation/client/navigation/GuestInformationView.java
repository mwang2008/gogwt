package com.gogwt.app.booking.gwt.reservation.client.navigation;

import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.ProgressBarWidget;
import com.gogwt.app.booking.gwt.reservation.client.widgets.guestinfo.GuestInfoLayoutWidget;
import com.gogwt.framework.arch.widgets.AbstractView;

public class GuestInformationView extends AbstractView {

	@Override
	public void process() {
		
		 //1. add progress bar
		 ProgressBarWidget progressBar = new ProgressBarWidget();
		 progressBar.processDisplayProgressBar(ProcessStatusEnum.SEARCH_RESULT);
		 viewPanel.add(progressBar);

		 //2. add layout
		 GuestInfoLayoutWidget layoutWidget = new GuestInfoLayoutWidget();
		 layoutWidget.prepareEntryLayout();		 
		 viewPanel.add(layoutWidget);
		
	}

}
