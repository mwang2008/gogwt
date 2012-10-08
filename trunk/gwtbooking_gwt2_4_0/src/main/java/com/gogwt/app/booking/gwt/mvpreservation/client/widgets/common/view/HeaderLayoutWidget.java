package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.common.view;

import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.HYPHON;
import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.SLASH;
import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.UNDER_SCORE;

import com.gogwt.app.booking.gwt.common.i18n.TagsReservationLookupResources;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.framework.arch.utils.StringUtils;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

 
public class HeaderLayoutWidget extends AbstractWidget {
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
		//String logoURL = GWTExtClientUtils.getMappingElem().getPrefix() + "/mvpreservation";
		String logoURL = GWTExtClientUtils.getMappingElem().getContextPath();
		if (Window.Location.getQueryString() != null) {
			logoURL += Window.Location.getQueryString();
		}
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
		Label rightLabel = new Label("GWT, Spring MVC, Hibernate, AOP, SEO, REST, JSON, CFX WS, XML, Performance Tuneup, MySQL ");
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
		
		int i=0;
		for (String lage_Region : langRegions) {
			if (!StringUtils.equalsIgnoreCase(currentLangRegion, lage_Region)) {
				String langName = tagLookup.getString("language_selector_" + lage_Region);
				String langLink = contructLanguageLink(lage_Region);
			 
				langLinks = WidgetStyleUtils.createHtmlLink(langName, langLink);
				languagePanel.add(langLinks);
				
				 
				languagePanel.add(WidgetStyleUtils.createStyledLabel( " | ", "veritcalDivider" ));
				 
			}
		}
 
		//add current language
		languagePanel.add(new Label(tagLookup.getString("language_selector_" + currentLangRegion)));

		layoutPanel.add(languagePanel);
	 
	}
	
 
	private String contructLanguageLink(final String lage_Region) {
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(GWTExtClientUtils.getMappingElem().getContextPath());
		sbuilder.append(SLASH);
		String lageRegion = lage_Region.replace(UNDER_SCORE, HYPHON);
		sbuilder.append(lageRegion);
		sbuilder.append(SLASH);
		//sbuilder.append(GWTExtClientUtils.getMappingElem().getControllerName());
		sbuilder.append("mvpreservation");
		
		//add request if any
		final String queryString = Window.Location.getQueryString();
		if (StringUtils.isSet(queryString)) {
			if (!StringUtils.containsString(queryString, "?")) {
			  sbuilder.append("?");
			}
			sbuilder.append(queryString);
		}
		return sbuilder.toString();
	}
}
