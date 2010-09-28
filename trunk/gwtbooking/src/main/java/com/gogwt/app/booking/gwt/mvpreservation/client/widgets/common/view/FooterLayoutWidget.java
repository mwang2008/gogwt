package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.common.view;

import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
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
		
		Panel footPanel = WidgetStyleUtils.createHorizontalPanel();
		
		footPanel.add(new Label("Footer"));
		layoutPanel.add(footPanel);
	 
	}

}
