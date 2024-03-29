package com.gogwt.app.booking.gwt.hoteldetail.client.widgets.common.view;

import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

public class MenuLayoutWidget extends AbstractWidget {
private TagsReservationResources tags = TagsReservationResources.Util.getInstance();
	
	private Panel layoutPanel = WidgetStyleUtils.createHorizontalPanel();

	public MenuLayoutWidget() {
		super();
		initWidget(layoutPanel);
		
		displayMenu();
	}
	
	private void displayMenu() {
		
		layoutPanel.setStyleName("menu_table");
		
		Panel menuPanel = WidgetStyleUtils.createHorizontalPanel();
		menuPanel.setStyleName("menu_inner");
		
		//address
		//Label address = WidgetStyleUtils.createLabel(tags.Label_Address(), "bw_top_menu");		 
		//Label alongRoute = WidgetStyleUtils.createLabel(tags.Label_AlongRoute(), "bw_top_menu");
		 
		Label menu1 = WidgetStyleUtils.createLabel(tags.menu_hoteldetail(), "bw_top_menu");
		menuPanel.add(menu1);
/*		
		menuPanel.add(address);
		menuPanel.add(WidgetStyleUtils.createLabel("|", "bw_top_menu"));
		menuPanel.add(alongRoute);
		menuPanel.add(WidgetStyleUtils.createLabel("|", "bw_top_menu"));
		
*/		 
		layoutPanel.add(menuPanel);
	}

}

