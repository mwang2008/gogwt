package com.gwtview.arch.navigation;

import java.util.Map;


/**
 * 
 * @author WangM
 * 
 */
public abstract class AbstractViewConfigAccessor implements ViewConfigAccessor {
	protected static String[] viewTokens;	 
	protected Map<String, ViewConfig> viewConfigInstances;
	  
	 
	 /**
	   * <p>
	   * See {@link #setViewInstances(Map<String,Composite>)}
	   * </p>
	   * @return Returns the viewInstances.
	   */
	  public Map<String, ViewConfig> getViewConfigInstances()
	  {
	    return viewConfigInstances;
	  }

	  /**
	   * <p>
	   * See {@link #setViewTokens(String[])}
	   * </p>
	   * @return Returns the viewTokens.
	   */
	  public String[] getViewTokens()
	  {
	    return viewTokens;
	  }
 
}
