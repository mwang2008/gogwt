package com.gogwt.app.booking.gwt.reservation.client;

import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.gogwt.app.booking.gwt.common.populator.PopulatorManager;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.FooterLayoutWidget;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.HeaderLayoutWidget;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.MenuLayoutWidget;
import com.gogwt.framework.arch.navigation.AbstractEntryPoint;
import com.gogwt.framework.arch.navigation.AbstractPageConfigAccessor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point 
 * @author WangM
 *
 */
public class ReservationEntryPoint extends AbstractEntryPoint {

	//Panel wrapperPanel;
	//protected Panel containerPanel;
	 
	
	@Override
	protected void loadModule() {
		Log.info("loadModule"); 
		  Log.trace("==This is a 'TRACE' test message");
		    Log.debug("==This is a 'DEBUG' test message");
		    //Log.info("This is a 'INFO' test message");
		    //Log.warn("This is a 'WARN' test message");
		    Log.error("This is a 'ERROR' test message");
		    Log.fatal("This is a 'FATAL' test message");
    
		// header
		RootPanel.get("header").add(new HeaderLayoutWidget());
		
		// menu 
		RootPanel.get("menu").add(new MenuLayoutWidget());

		// show middle part of GWT body, wrapperContent is defined in the
		// loading JSP.
		addPageManagerToRootPanel("wrapperContent");
		
		
		// footer
		RootPanel.get("footer").add(new FooterLayoutWidget());
		 
	}

	
	
	@Override
	protected AbstractPageConfigAccessor obtainPageAccessor() {
		return GWT.create(ReservationProcessConfig.class);		 
	}



	@Override
	protected void processPopulator(Map<String, String> populatorsMap) {
		PopulatorManager.handlePopulators( populatorsMap );
		
		
	}

}
