package com.gogwt.app.booking.gwt.reservation.client;

import com.gogwt.app.booking.gwt.reservation.client.widgets.common.FooterLayoutWidget;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.HeaderLayoutWidget;
import com.gogwt.framework.arch.navigation.AbstractEntryPoint;
import com.gogwt.framework.arch.navigation.AbstractViewConfigAccessor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point 
 * @author WangM
 *
 */
public class ReservationEntryPoint extends AbstractEntryPoint {

	Panel wrapperPanel;
	protected Panel containerPanel;
	private Panel headerPanel;
	
	
	@Override
	protected void loadModule() {
		/*
		wrapperPanel = WidgetStyleUtils.createFlowPanelWithIdStyles( "wrapper" );
		containerPanel = WidgetStyleUtils.createFlowPanelWithId( "container" );
	    headerPanel = WidgetStyleUtils.createFlowPanelWithStyles( "MastheadContainer" );
	      
	    Widget headerWidget = new HeaderLayoutWidget();
	    headerPanel.add( headerWidget );
	    containerPanel.add( headerPanel );
	    
	    // view panel
	    addViewPanel( containerPanel );
	    	
	    // add everything to root panel
	    wrapperPanel.add( containerPanel );
	    RootPanel.get().add( wrapperPanel );
	    */
	     	
		// header
		RootPanel.get("header").add(new HeaderLayoutWidget());

		// show middle part of GWT body, wrapperContent is defined in the
		// loading JSP.
		addViewManagerToRootPanel("wrapperContent");
		
		
		// footer
		RootPanel.get("footer").add(new FooterLayoutWidget());
		 
	}

	
	
	@Override
	protected AbstractViewConfigAccessor obtainViewAccessor() {
		return GWT.create(ReservationProcessConfig.class);		 
	}

}
