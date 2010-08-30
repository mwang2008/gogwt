package com.gogwt.framework.arch.navigation;

import java.util.Map;

public interface PageConfigAccessor {
	 public String toString();
	   
	   /**
	    * <p>
	    * Instantiates the view widgets involved in a particular navigation mapping
	    * If the view has already been instantiated, returns this instance or create a new one
	    * </p>
	    * @param token
	    * @return
	    */
	   PageConfig lazyCreateOrGetPageConfig( String token );

	   /**
	    * <p>
	    * Provides list of view tokens
	    * </p>
	    * @return
	    */
	   String[] getPageTokens();
	   
	   /**
	    * <p>
	    * Keeps track of instances of the view widgets associated in a
	    * particular navigation mapping
	    * </p>
	    * @return
	    */
	   Map<String, PageConfig> getPageConfigInstances();
}
