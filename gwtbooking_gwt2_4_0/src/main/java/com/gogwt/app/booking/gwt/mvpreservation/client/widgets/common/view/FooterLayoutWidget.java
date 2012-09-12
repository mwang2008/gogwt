package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.common.view;

 
import java.util.Date;

import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;

 
public class FooterLayoutWidget extends AbstractWidget {
	 
	private Panel layoutPanel = new FlowPanel();

	public FooterLayoutWidget() {
		super();
		initWidget(layoutPanel);
		
		displayHeader();
	}

	private void displayHeader() {
		
		//layoutPanel.getElement().setId("FooterContainer");
		layoutPanel.setStyleName("foot_table");
		
		Panel divider = WidgetStyleUtils.createHorizontalPanel();
		divider.getElement().setId("foot_divider");
		layoutPanel.add(divider);
		
		final Panel footPanel = WidgetStyleUtils.createHorizontalPanel();
		
		
		
		GWT.runAsync(new RunAsyncCallback() {
	       public void onFailure(Throwable caught) {
	           Window.alert("Code download failed");
	       }

	       public void onSuccess() {
	    	   Date today = new Date();
	   		   int year = today.getYear()+1900;
	    	   footPanel.add(new HTML(" @" + year + " GoGWT.com. <br>Footer is created by ReservationMVPEntryPoint, code spliting "));
	    	   layoutPanel.add(footPanel);
	       }
	    });
		
		//footPanel.add(new HTML(" @" + year + " GoGWT.com, footer is created by ReservationMVPEntryPoint"));
		//layoutPanel.add(footPanel);
	 
	}

}
