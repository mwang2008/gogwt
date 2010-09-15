package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.common.view;

import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.HYPHON;
import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.SLASH;
import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.UNDER_SCORE;

import com.gogwt.app.booking.gwt.common.i18n.TagsReservationLookupResources;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.framework.arch.utils.GWTStringUtils;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
		Label rightLabel = new Label(" GWT, Spring MVC, Hibernate, AOP, SEO, Performance Tuneup ");
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
			if (!GWTStringUtils.equalsIgnoreCase(currentLangRegion, lage_Region)) {
				String langName = tagLookup.getString("language_selector_" + lage_Region);
				String langLink = contructLanguageLink(lage_Region);
			 
				langLinks = WidgetStyleUtils.createHtmlLink(langName, langLink);
				languagePanel.add(langLinks);
				
				//for zh_CN, display not support message
 				if (GWTStringUtils.equalsIgnoreCase(lage_Region, "zh_CN")) {
					langLinks.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent arg0) {
							Window.alert(" Not Support Yet, just demo how to switch languages ");							 
						}					 
					});
				}
				
				if (++i != langRegions.length-1) {
					languagePanel.add(WidgetStyleUtils.createStyledLabel( "|", "veritcalDivider" ));
				}
			}
		}
 
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
		sbuilder.append("gwtreservation");
		
		//add request if any
		final String queryString = Window.Location.getQueryString();
		if (GWTStringUtils.isSet(queryString)) {
			//sbuilder.append("?");
			sbuilder.append(queryString);
		}
		return sbuilder.toString();
	}
}
