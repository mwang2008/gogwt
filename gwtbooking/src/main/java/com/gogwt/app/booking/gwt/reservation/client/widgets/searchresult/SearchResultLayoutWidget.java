package com.gogwt.app.booking.gwt.reservation.client.widgets.searchresult;

import com.gogwt.app.booking.dto.dataObjects.common.EnvMappingElem;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.framework.arch.widgets.BaseWidget;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * SearchResultLayoutWidget contains tab panel, map panel, and result list
 * panel.
 * 
 * @author WangM
 * 
 */
public class SearchResultLayoutWidget extends BaseWidget implements TabListener {
	Panel layoutPanel = new FlowPanel();

	private final int MAP_TAB = 0;
	private final int LIST_TAB = 1;

	private TabPanel tabPanel = new TabPanel();
	
	

	private Image mapviewOn;
	private Image listviewOff;

	public SearchResultLayoutWidget() {

		EnvMappingElem envMappingElem = GWTExtClientUtils.getMappingElem();

		// 1. add tab panel

		mapviewOn = new Image(envMappingElem.getContextPath() + "/images/"
				+ envMappingElem.getLagnRegion() + "/mapviewOn.gif");
		mapviewOn.setTitle(""); // todo: add title for SEO
		listviewOff = new Image(envMappingElem.getContextPath() + "/images/"
				+ envMappingElem.getLagnRegion() + "/listviewOff.gif");

		

		initWidget(layoutPanel);
	}

	/**
	 * Entry point to display search result
	 * @param hotelSearchResponseBean
	 */
	public void prepareEntryLayout(
			final HotelSearchResponseBean hotelSearchResponseBean) {
		
		layoutPanel.clear();
		
		VerticalPanel panel = new VerticalPanel();
		FlexTable flextable = new FlexTable();
			
        panel.add(flextable);
        
        int row = 0;
        //1. title 
        flextable.setWidget(row, 0, new Label("Show Title"));
        
        //2. show map
        row++;
        final ResultMapsPanel resultMapsPanel = new ResultMapsPanel();
        resultMapsPanel.showMapWithResult(hotelSearchResponseBean);
        flextable.setWidget(row, 0, resultMapsPanel);
        
        //3. show list
        row++;
        final ResultListPanel resutlListPanel = new ResultListPanel();
        resutlListPanel.showListResult(hotelSearchResponseBean);
        flextable.setWidget(row, 0, resutlListPanel);
        
        
        
        /*
		// 1. add tab panel
		tabPanel.add(new FlowPanel(), "MAP VIEW");
		tabPanel.add(new FlowPanel(), "LIST VIEW");

		tabPanel.getTabBar().setTabHTML(MAP_TAB, mapviewOn.toString());
		tabPanel.getTabBar().setTabHTML(LIST_TAB, listviewOff.toString());

		// select tab before adding listener so map won't display until the maps
		// api is ready
		tabPanel.selectTab(MAP_TAB);
		tabPanel.addTabListener(this);

		layoutPanel.add(tabPanel);

		// 2. add map view
		if (tabPanel.getTabBar().getSelectedTab() == MAP_TAB) {			 
			resultMapsPanel.showMapWithResult(hotelSearchResponseBean);
			resultMapsPanel.setVisible(true);
		} else {
			resultMapsPanel.setVisible(false);
		}
		layoutPanel.add(resultMapsPanel);
		
		// 3. add list view
        */
		
		layoutPanel.add(panel);
		
	}

	// @Override
	public boolean onBeforeTabSelected(SourcesTabEvents arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	// @Override
	public void onTabSelected(SourcesTabEvents arg0, int arg1) {
		// TODO Auto-generated method stub

	}

}
