package com.gogwt.framework.arch.navigation;

import com.gogwt.framework.arch.widgets.AbstractPage;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class AbstractMVPEntryPoint implements EntryPoint,
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

	public final void onModuleLoad() {

		initializePageAccessor();
		initializeHistoryListener();

		preLoadModule();
		loadModule();
		postLoadModule();
		
		if (!isDefaultPageLoaded) {
			History.fireCurrentHistoryState();
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
	    if ( pageAccessor.getPageConfigInstances().containsKey( token ) ) {
	      //sLogger.info( "Show already instatiated view." );
	      // show the view if already instantiated
	      page = pageAccessor.lazyCreateOrGetPageConfig( token ).getInstance();
	      page.setVisible( true );
	    } else {
	      //sLogger.info( "Instatiate View " + token );
	      // instantiate, add and show the view
	      try {
	        page = pageAccessor.lazyCreateOrGetPageConfig( token ).getInstance();
	        pageContainer.add( page );
	      } catch (Throwable t) {
	    	  t.printStackTrace();
	        //sLogger.error("Error instantiating view in manageViewVisibility()"+t, t);
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
	    	  //todo: add log
	        //sLogger.error("Error executing view.process();", t);
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
