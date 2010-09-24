package com.gogwt.framework.arch.navigation;



import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.gogwt.framework.arch.widgets.AbstractPage;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
 


/**
 * 
 * @author WangM
 * 
 */
public abstract class AbstractEntryPoint implements EntryPoint,
		ValueChangeHandler<String> {
	
	private boolean isDefaultPageLoaded = false;

	// private static DbgLogger sLogger = LogUtils.getLogger(
	 
	/** pageContainer - FlowPanel Container widget for the views */
	private final transient Panel pageContainer = new FlowPanel();

	/**
	 * viewAccessor - ViewAccessor Provides a way to instantiate views via code
	 * generation
	 */
	protected transient PageConfigAccessor pageAccessor;
	private long startTimeMillis;

	public final void onModuleLoad() {

		/* Install an UncaughtExceptionHandler which will
	     * produce <code>FATAL</code> log messages
	     */
	    Log.setUncaughtExceptionHandler();
	    
	    /* Use a deferred command so that the UncaughtExceptionHandler
	     * catches any exceptions in onModuleLoad2()
	     */
	    DeferredCommand.addCommand(new Command() {
	      public void execute() {
	        onModuleLoad2();
	      }
	    });

	    

	}

	private void onModuleLoad2() {
		  if (Log.isDebugEnabled()) {
		      startTimeMillis = System.currentTimeMillis();
		    }

		  Log.trace("This is a 'TRACE' test message");
		    Log.debug("This is a 'DEBUG' test message");
		    Log.info("This is a 'INFO' test message");
		    Log.warn("This is a 'WARN' test message");
		    Log.error("This is a 'ERROR' test message");
		    Log.fatal("This is a 'FATAL' test message");

		    
		initializePageAccessor();
		initializeHistoryListener();

		preLoadModule();
		loadModule();
		postLoadModule();
		
		if (!isDefaultPageLoaded) {
			History.fireCurrentHistoryState();
		}	
		
	    if (Log.isDebugEnabled()) {
	        long endTimeMillis = System.currentTimeMillis();
	        float durationSeconds = (endTimeMillis - startTimeMillis) / 1000F;
	        Log.debug("Duration: " + durationSeconds + " seconds");
	     }

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
	protected abstract AbstractPageConfigAccessor obtainPageAccessor();

	
	protected abstract void processPopulator(Map<String, String> populatorsMap);
	
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
	  private void managePageVisibility( final String token )
	  {
	    // hide all views
	    for( final PageConfig pageConfig : pageAccessor.getPageConfigInstances().values()) {
	      pageConfig.getInstance().setVisible( false );
	    }

	    AbstractPage page = null;
	    PageConfig config = null;
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
 	    }	      	        
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
		private String getCurrentPageName(String token) {	    
		   if (!isSet(token)) {
			   token = pageAccessor.getPageTokens()[0];
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


