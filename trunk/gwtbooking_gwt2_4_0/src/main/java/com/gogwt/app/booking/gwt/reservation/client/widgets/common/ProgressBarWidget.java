package com.gogwt.app.booking.gwt.reservation.client.widgets.common;

import com.gogwt.app.booking.dto.dataObjects.common.ProcessStatusEnum;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.framework.arch.utils.StringUtils;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
 

public class ProgressBarWidget extends AbstractWidget {
	
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
	 	if (StringUtils.isSet(query)) {
	 		if (StringUtils.equals(query, "?")) {
	 		   homeLink.append("?");
	 		}
	 		homeLink.append(query);
	 	}
  		HTML home = WidgetStyleUtils.createHtmlLink(tags.Page_name_home(), homeLink.toString());
 		mainPanel.add(home);
 	    
 		mainPanel.add(new Label(" -> "));
	 
	 	//search result link
	 	if (status.compareTo(ProcessStatusEnum.SEARCH_RESULT) == 0) {
	 		mainPanel.add(new Label(tags.Page_name_searchresult()));
	 	}
	 	else if (status.compareTo(ProcessStatusEnum.SEARCH_RESULT) > 0) {
 	 		
	 		String url = getFullURL("searchresult");
 	 		HTML searchResult = WidgetStyleUtils.createHtmlLink(tags.Page_name_searchresult(), url);
	 		
	 		mainPanel.add(searchResult);
	 	}
	 	
	 	//guest info
	 	if (status.compareTo(ProcessStatusEnum.GUEST_INFO) ==0) {
	 		mainPanel.add(new Label(" -> "));
	 		mainPanel.add(new Label(tags.Page_name_guestinfo()));
	 	}
	 	if (status.compareTo(ProcessStatusEnum.GUEST_INFO)>0) {
	 		mainPanel.add(new Label(" -> "));
	 		String url = getFullURL("guestinfo");	 		 
	  	 	
 	 		HTML searchResult = WidgetStyleUtils.createHtmlLink(tags.Page_name_guestinfo(), url);
	 		
	 		mainPanel.add(searchResult);
	 	}
	 	
	 	
	}
	
	private String getFullURL(String token) {
		String query = GWTExtClientUtils.getMappingElem().getQueryParamter();
		final StringBuilder linkUrl = new StringBuilder();
		linkUrl.append(GWTExtClientUtils.getMappingElem().getPrefix() + "/gwtreservation");
 		if (StringUtils.isSet(query)) {
	 		if (StringUtils.equals(query, "?")) {
	 			linkUrl.append("?");
		 	} 			
 			linkUrl.append(query);
 		}
 		if (StringUtils.isSet(token)) {
 		  linkUrl.append("#");
 		  linkUrl.append(token);
 		}
 		
 		return linkUrl.toString();
	}
}
