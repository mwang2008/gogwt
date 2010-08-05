package com.gwtview.arch.navigation;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtview.arch.widgets.AbstractView;
import com.gwtview.arch.widgets.ActionForm;
 

/**
 * 
 * @author WangM
 * 
 */
public abstract class AbstractEntryPoint implements EntryPoint, HistoryListener {
	 
	private boolean isDefaultViewLoaded = false;

	//private static DbgLogger sLogger = LogUtils.getLogger( AbstractEntryPoint.class );
	
	/** viewContainer - FlowPanel Container widget for the views */
	private final transient Panel viewContainer = new FlowPanel();

	/**
	 * viewAccessor - ViewAccessor Provides a way to instantiate views via code
	 * generation
	 */
	protected transient ViewConfigAccessor viewAccessor;

	/**
	 * 
	 */
	public final void onModuleLoad() {

		initializeViewAccessor();
		initializeHistoryListener();

		loadModule();

		if (!isDefaultViewLoaded) {
			History.fireCurrentHistoryState();
		}
	}

	/**
	 * <p>
	 * Initialize history listener
	 * </p>
	 */
	private void initializeHistoryListener() {
		History.addHistoryListener(this);
	}

	/**
	 * <p>
	 * The entry point of loading module, all sub class should implement it.
	 * </p>
	 */
	protected abstract void loadModule();

	/**
	 * <p>
	 * Make subclass provide the correct view accessor
	 * </p>
	 * 
	 * @return
	 */
	protected abstract AbstractViewConfigAccessor obtainViewAccessor();

	/**
	 * <p>
	 * Called by module entry point loadModule to load view manager
	 * </p>
	 * 
	 * @param view
	 *            , the view name used in HTML/JSP
	 */
	protected void addViewManagerToRootPanel(final String view) {
		RootPanel.get(view).add(viewContainer);
	}

	/**
	 * This method is called whenever the application's history changes.
	 * 
	 * @param historyToken
	 *            the histrory token
	 */
	public final void onHistoryChanged(final String token) {
		String visibilityToken = getCurrentViewName(token);
		manageViewVisibility(visibilityToken.toLowerCase());
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
	  private void manageViewVisibility( final String token )
	  {
	    // hide all views
	    for( final ViewConfig viewConfig : viewAccessor.getViewConfigInstances().values()) {
	      viewConfig.getInstance().setVisible( false );
	    }

	    AbstractView view = null;
	    if ( viewAccessor.getViewConfigInstances().containsKey( token ) ) {
	      //sLogger.info( "Show already instatiated view." );
	      // show the view if already instantiated
	      view = viewAccessor.lazyCreateOrGetViewConfig( token ).getInstance();
	      view.setVisible( true );
	    } else {
	      //sLogger.info( "Instatiate View " + token );
	      // instantiate, add and show the view
	      try {
	        view = viewAccessor.lazyCreateOrGetViewConfig( token ).getInstance();
	        viewContainer.add( view );
	      } catch (Throwable t) {
	        //sLogger.error("Error instantiating view in manageViewVisibility()"+t, t);
	      }
	    }
	    
	    // Process View
	    if ( view != null ) {
	      // indicates a default view has been loaded
	      isDefaultViewLoaded = true;
	      ActionForm actionForm = null;// viewAccessor.getActionForm();
	      
	      //save current context
	      //actionForm.setCurrentToken(token);
	      //actionForm.setViewAccessor(viewAccessor);
	      
	      //sLogger.info( "Execute View.process()");
	      try {
	    	view.preProcess();
	        
	    	view.process(actionForm);
	        
	    	view.postProcess();
	        
	      } catch (Throwable t) {
	        //sLogger.error("Error executing view.process();", t);
	      }
	    }	      	        
	  }

	/**
	 * <p>
	 * Initialize view accessor
	 * </p>
	 */
	private void initializeViewAccessor() {
		viewAccessor = obtainViewAccessor();
	}

	/**
	 * if history token is empty, then take first one as viewName
	 * @return
	 */
	private String getCurrentViewName(String token) {	    
	   if (!isSet(token)) {
		   token = viewAccessor.getViewTokens()[0];
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

