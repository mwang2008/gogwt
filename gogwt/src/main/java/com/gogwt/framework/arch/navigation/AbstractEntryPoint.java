/*
 * Copyright 2010 GoGWT.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gogwt.framework.arch.navigation;

import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.gogwt.framework.arch.utils.ActionForward;
import com.gogwt.framework.arch.widgets.AbstractController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
 

public abstract class AbstractEntryPoint implements EntryPoint,
		ValueChangeHandler<String> {
	
	private boolean isDefaultPageLoaded = false;

	// private static DbgLogger sLogger = LogUtils.getLogger(
	 
	/** pageContainer - FlowPanel Container widget for the views */
	private final transient Panel pageContainer = new FlowPanel();

	/**
	 * pageAccessor - Provides a way to instantiate views via code
	 * generation
	 */
	protected transient ControllerConfigAccessor pageAccessor;
	private long startTimeMillis;

	public final void onModuleLoad() {

/*		
        // Install an UncaughtExceptionHandler which will
	    // produce <code>FATAL</code> log messages
	    Log.setUncaughtExceptionHandler();
	    
	    // Use a deferred command so that the UncaughtExceptionHandler
	    // catches any exceptions in onModuleLoad2()
	    DeferredCommand.addCommand(new Command() {
	      public void execute() {
	    	  onModuleLoad2();
	      }
	    });
*/
	    onModuleLoad2();

	}

	private void onModuleLoad2() {
		preLoadModule();
	    
		if (Log.isDebugEnabled()) {
		      startTimeMillis = System.currentTimeMillis();
		}

		initializePageAccessor();
		initializeHistoryListener();
		
		loadModule();		
		
		if (!isDefaultPageLoaded) {
			History.fireCurrentHistoryState();
		}	
		
	    if (Log.isDebugEnabled()) {
	        long endTimeMillis = System.currentTimeMillis();
	        float durationSeconds = (endTimeMillis - startTimeMillis) / 1000F;
	        Log.debug("Duration: " + durationSeconds + " seconds");
	     }

	    postLoadModule();
	    
	}

	protected void addPagePanel(Panel panel) {
		panel.add(pageContainer);
	}
	
	/**
	 * <p>
	 * Initialize history listener
	 * gwt 2.0.3
	 * </p>
	 */	
	private void initializeHistoryListener() {		 
		History.addValueChangeHandler( this );
	    History.fireCurrentHistoryState();
	}
	
	/**
	 * <p>
	 * The entry point of loading module, all sub class should implement it.
	 * </p>
	 */	
	protected abstract void loadModule();
	protected void preLoadModule() {}
	protected void postLoadModule() {}
	
	/**
	 * <p>
	 * Make subclass provide the correct view accessor
	 * </p>
	 * 
	 * @return
	 */
	protected abstract AbstractControllerConfigAccessor obtainPageAccessor();

	
	protected void processPopulator(Map<String, String> populatorsMap) {
		
	};
	
	/**
	 * <p>
	 * Called by module entry point loadModule to load page manager
	 * </p>
	 * 
	 * @param view
	 *            , the view name used in HTML/JSP
	 */
	protected void addPageManagerToRootPanel(final String view) {
		RootPanel.get(view).add(pageContainer);
	}
	protected void addPageManagerToRootPanel() {
		RootPanel.get().add(pageContainer);
	}
	
    /*
     * gwt 2.0.3
     */	
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		String visibilityToken = getCurrentPageName(token);
		managePageVisibility(visibilityToken.toLowerCase());		
	}
	
	/****************************************************************
	 * Private methods
	 *****************************************************************/

	 /**
	   * <p> Manage the visibility for the various views that are part of this
	   * module </p>
	   * @param token
	   *          - name of the view to be made visible
	   */
	  private void managePageVisibility( final String token ) {
	    // hide all views
	    for( final ControllerConfig pageConfig : pageAccessor.getPageConfigInstances().values()) {
	      pageConfig.getInstance().setVisible( false );
	    }

	    AbstractController page = null;
	    ControllerConfig config = null;
	    if ( pageAccessor.getPageConfigInstances().containsKey( token ) ) {
	        Log.info( "Show already instatiated view." );
	       // show the view if already instantiated
	    	config = pageAccessor.lazyCreateOrGetPageConfig( token );
	        page = config.getInstance();
	        page.setVisible( true );
	    } else {
	    	Log.info( "Instatiate View " + token );
	        // instantiate, add and show the view
	        try {
	           config = pageAccessor.lazyCreateOrGetPageConfig( token );
	           page = config.getInstance();
	           pageContainer.add( page );
	        } catch (Throwable t) {
	    	  t.printStackTrace();
	    	  Log.fatal("Error instantiating view in manageViewVisibility()"+t, t);
	        }
	    }
	    
	    //save forward context
	    ActionForward.setCurrentPageForward(config.getForward(), config.getGlobalForward());
	    
	    // Process Page
	    if ( page != null ) {
	      // indicates a default view has been loaded
	      isDefaultPageLoaded = true;
	    
	      try {
	    	  page.preProcess();
	        
	    	  page.process();
	        
	    	  page.postProcess();
	        
	      } catch (Throwable t) {	    	  
	         Log.fatal("Error executing view.process();", t);
	      }
	      
	      //start to process populator
	      try {
	         Map<String, String> populatorsMap = config.getProperties().get( "populators" );
	         processPopulator(populatorsMap);
	      }
          catch( Throwable t ) {
        	Log.fatal( "Error executing PopulatorManager.handlePopulators();", t );
          }
          
  	      //finish moduleLoad
          final AbstractController finalPage = page;
  	      DeferredCommand.addCommand(new Command() {
  	         public void execute() {
  	        	onCompletePageLoad(finalPage);
  	         }
  	      });
 	    }	      	        
	  }
     
	  private void onCompletePageLoad(AbstractController page) {
		 //set page title, meta description, meta keyword etc
		  page.setPageMetaInfo();
	  }
	  
	  
		/**
		 * <p>
		 * Initialize view accessor
		 * </p>
		 */
		private void initializePageAccessor() {
			pageAccessor = obtainPageAccessor();
		}

		/**
		 * if history token is empty, then take first one as viewName
		 * @return
		 */
		protected String getCurrentPageName(String token) {	    
		   if (!isSet(token)) {
			   token = pageAccessor.getPageTokens()[0];
		   }
		   
		   //to compromise Google AJAX crawl with #!
		   if (token.startsWith( "!" )) {
			   token = token.substring( 1 );
		   }
		   
		   return token;
		}
		
		private boolean isSet(final String pValue) {
			if (pValue == null || pValue.trim().equalsIgnoreCase("")) {
				return false;
			}

			return true;
		}
		
		
}


