package com.gogwt.app.booking.gwt.reservation.client.widgets.searchresult;

import com.gogwt.app.booking.dto.dataObjects.common.EnvMappingElem;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * SearchResultLayoutWidget contains tab panel, map panel, and result list
 * panel.
 * 
 * @author WangM
 * 
 */
public class SearchResultLayoutWidget extends AbstractWidget implements
		 SelectionHandler<Integer> {
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();

	private HotelSearchResponseBean hotelSearchResponseBean;
	private TabPanel tabPanel = new TabPanel();  
    private VerticalPanel resultPanel = new VerticalPanel();
    
	private Panel layoutPanel = new FlowPanel();

	private final int MAP_TAB = 0;
	private final int LIST_TAB = 1;

	private Image mapviewOn, mapviewOff, listviewOn, listviewOff;

	public SearchResultLayoutWidget() {

		EnvMappingElem envMappingElem = GWTExtClientUtils.getMappingElem();

		mapviewOn = new Image(envMappingElem.getContextPath() + "/images/"
				+ envMappingElem.getLagnRegion() + "/mapviewOn.gif");
		mapviewOn.setTitle("Map View");

		mapviewOff = new Image(envMappingElem.getContextPath() + "/images/"
				+ envMappingElem.getLagnRegion() + "/mapviewOff.gif");
		mapviewOff.setTitle("Map View");

		listviewOn = new Image(envMappingElem.getContextPath() + "/images/"
				+ envMappingElem.getLagnRegion() + "/listviewOn.gif");
		listviewOn.setTitle("List View");

		listviewOff = new Image(envMappingElem.getContextPath() + "/images/"
				+ envMappingElem.getLagnRegion() + "/listviewOff.gif");
		listviewOn.setTitle("List View");

		initWidget(layoutPanel);
	}

	/**
	 * Entry point to display search result
	 * 
	 * @param hotelSearchResponseBean
	 */
	public void prepareEntryLayout(
			final HotelSearchResponseBean hotelSearchResponseBean) {

		this.hotelSearchResponseBean = hotelSearchResponseBean;

		layoutPanel.clear();

		// 1. map/list view
		tabPanel.add(new FlowPanel(), tags.searchresult_tabpanel_map_view());
		tabPanel.add(new FlowPanel(), tags.searchresult_tabpanel_list_view());
		tabPanel.getTabBar().setTabHTML(MAP_TAB, mapviewOn.toString());
		tabPanel.getTabBar().setTabHTML(LIST_TAB, listviewOff.toString());
		//tabPanel.addStyleName(style);
		
		tabPanel.selectTab(MAP_TAB);
	 	 
		tabPanel.addSelectionHandler(this);
		
		layoutPanel.add(tabPanel);

		displayResult(MAP_TAB);
		
		// flextable.setWidget(row, 0, tabPanel);

	}

 
	
	public void onSelection(SelectionEvent<Integer> event) {
		if (event.getSelectedItem() == MAP_TAB) {
		    tabPanel.getTabBar().setTabHTML( LIST_TAB, listviewOff.toString() );
		    tabPanel.getTabBar().setTabHTML( MAP_TAB, mapviewOn.toString() );
		} else {
		    tabPanel.getTabBar().setTabHTML( MAP_TAB, mapviewOff.toString() );
		    tabPanel.getTabBar().setTabHTML( LIST_TAB, listviewOn.toString() );			
		}
		
		displayResult(event.getSelectedItem());
 	}

 	private void displayResult(final int tabIndex) {
  		
		int row = 0;
		
		resultPanel.clear();
		
		FlexTable flextable = new FlexTable();
		resultPanel.add(flextable);

		// 2. show map
		if (tabIndex == MAP_TAB) {			
			row++;
			final ResultMapsPanel resultMapsPanel = new ResultMapsPanel();
			resultMapsPanel.showMapWithResult(hotelSearchResponseBean);
			flextable.setWidget(row, 0, resultMapsPanel);
		}
		 
		// 3. show list
		row++;
		final ResultListPanel resutlListPanel = new ResultListPanel();
		resutlListPanel.showListResult(hotelSearchResponseBean);
		flextable.setWidget(row, 0, resutlListPanel);

		layoutPanel.add(resultPanel);
	}
}
