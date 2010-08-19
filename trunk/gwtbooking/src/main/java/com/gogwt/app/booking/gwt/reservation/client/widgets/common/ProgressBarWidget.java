package com.gogwt.app.booking.gwt.reservation.client.widgets.common;

import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.app.booking.gwt.reservation.client.i18n.TagsReservationResources;
import com.gogwt.framework.arch.utils.GWTStringUtils;
import com.gogwt.framework.arch.widgets.BaseWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
 

public class ProgressBarWidget extends BaseWidget {
	
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();
	
	private Panel mainPanel = WidgetStyleUtils.createHorizontalPanel("progressBar");
	
	/**
	 * <p> Create a new instance of ProgressBarWidget. </p>
	 */
	public ProgressBarWidget() {
		initWidget(mainPanel);
	}

	public void processDisplayProgressBar(ProcessStatusEnum status) {
	 	mainPanel.clear();
        
	 	String query = GWTExtClientUtils.getMappingElem().getQueryParamter();
	 	
	 	//home link
	 	final StringBuilder homeLink = new StringBuilder(GWTExtClientUtils.getMappingElem().getPrefix() + "/gwtreservation");
	 	if (GWTStringUtils.isSet(query)) {
	 		homeLink.append("?");
	 		homeLink.append(query);
	 	}
	 	 
	 	//search result link
	 	if (status == ProcessStatusEnum.SEARCH_RESULT) {
	 		
	 		final StringBuilder searchResultLink = new StringBuilder();
	 		searchResultLink.append(GWTExtClientUtils.getMappingElem().getPrefix() + "/gwtreservation");
	 		if (GWTStringUtils.isSet(query)) {
	 			searchResultLink.append("?");
	 			searchResultLink.append(query);
	 		}
	 		searchResultLink.append("#searchresult");
	 		 
	  		HTML home = WidgetStyleUtils.createHtmlLink(tags.Page_name_home(), homeLink.toString());
	 		mainPanel.add(home);
	 	    
	 		mainPanel.add(new Label(" -> "));
	 		
 	 		HTML searchResult = WidgetStyleUtils.createHtmlLink(tags.Page_name_searchresult(), searchResultLink.toString());
	 		
	 		mainPanel.add(searchResult);
	 	}
	 	
	}
}
