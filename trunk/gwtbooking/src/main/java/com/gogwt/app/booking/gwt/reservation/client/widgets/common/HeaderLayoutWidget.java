package com.gogwt.app.booking.gwt.reservation.client.widgets.common;

import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.app.booking.gwt.reservation.client.i18n.TagsReservationLookupResources;
import com.gogwt.app.booking.gwt.reservation.client.i18n.TagsReservationResources;
import com.gogwt.framework.arch.utils.GWTStringUtils;
import com.gogwt.framework.arch.widgets.BaseWidget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

public class HeaderLayoutWidget extends BaseWidget {
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();
	
	private Panel layoutPanel = WidgetStyleUtils.createHorizontalPanel();

	public HeaderLayoutWidget() {
		super();
		initWidget(layoutPanel);
		
		displayHeader();
	}

	private void displayHeader() {
		
		WidgetStyleUtils.setStyleToWidget(layoutPanel, "header");
		
		//1. logo
		Panel logoPanel = WidgetStyleUtils.createFlowPanelWithStyles( "logo" );		
		String logoURL = GWTExtClientUtils.getMappingElem().getPrefix() + "/gwtreservation";
		Anchor logoAnchor = WidgetStyleUtils.createExternalLink("gogwt logo", logoURL);		
		logoAnchor.setText( "" );
		
		Image logoImage = new Image();
		final String contextPath = GWTExtClientUtils.getMappingElem().getContextPath();
		logoImage.setUrl(contextPath + "/images/logo.JPG" );
		
		//add logo to logoAnchor
		logoAnchor.getElement().appendChild( logoImage.getElement() );		
		logoPanel.add(logoAnchor);
				
		layoutPanel.add(logoPanel);
		
		// text panel
		Panel headerTextPanel = WidgetStyleUtils.createHorizontalPanel();
		headerTextPanel.addStyleName("headerText");
		Label rightLabel = new Label(" GWT, Spring MVC, RPC, AOP, SEO, Performance Tuneup ");
		rightLabel.addStyleName("rightLabel");
		headerTextPanel.add(rightLabel);
		
		layoutPanel.add(headerTextPanel);
		
		TagsReservationLookupResources tagLookup = TagsReservationLookupResources.Util.getInstance();
		// language text
		String langSupportList = tagLookup.language_support_list();
		
		String [] langRegions = langSupportList.split(",");
		
		String currentLangRegion = GWTExtClientUtils.getMappingElem().getLanguageId() + "_" + GWTExtClientUtils.getMappingElem().getCountryId().toUpperCase();
		
		Panel languagePanel = WidgetStyleUtils.createHorizontalPanel();
		languagePanel.addStyleName("headerLanguage");
		
		HTML langLinks = new HTML();
		
		for (String lageRegion : langRegions) {
			if (!GWTStringUtils.equalsIgnoreCase(currentLangRegion, lageRegion)) {
				String langName = tagLookup.getString("language_selector_" + lageRegion);
				String langLink = GWTExtClientUtils.getMappingElem().getPrefix();
			 
				WidgetStyleUtils.createHtmlLink(langName, langLink);
			}
		}
		Label langLabel = new Label(" Language: ");
		langLabel.addStyleName("rightLabel");
		languagePanel.add(langLabel);
		
		
		
		layoutPanel.add(languagePanel);
		
		/*
		//2. Welcome
		Label welcomeLabel = WidgetStyleUtils.createLabel("Welcome", "welcomeName");
	    layoutPanel.add(welcomeLabel);
		
	    //3.  
		Panel panel = WidgetStyleUtils.getHorizontalPanelWithWidgets( 
				"lang",				
				WidgetStyleUtils.createStyledLabel( "|", "veritcalDivider" ), 
				new Label(" Customer Care")
				 
		);
		
		//4. dropdown
		Panel dropDownPanel = WidgetStyleUtils.createFlowPanel();
		dropDownPanel.add(new Label("ddddd"));
		layoutPanel.add(dropDownPanel);
				
		layoutPanel.add(panel);
		*/
	}
	
 
}
