package com.gogwt.framework.arch.navigation;

import java.util.Map;


/**
 * 
 * @author WangM
 *
 */
public interface ViewConfigAccessor {
   public String toString();
   
   /**
    * <p>
    * Instantiates the view widgets involved in a particular navigation mapping
    * If the view has already been instantiated, returns this instance or create a new one
    * </p>
    * @param token
    * @return
    */
   ViewConfig lazyCreateOrGetViewConfig( String token );

   /**
    * <p>
    * Provides list of view tokens
    * </p>
    * @return
    */
   String[] getViewTokens();
   
   /**
    * <p>
    * Keeps track of instances of the view widgets associated in a
    * particular navigation mapping
    * </p>
    * @return
    */
   Map<String, ViewConfig> getViewConfigInstances();
   
   /**
    * <p>
    * Application wide configuration information
    * </p>
    * @return
    */
  // Map<String, Map<String, String>> getProperties();
   
   //ActionForm getActionForm();
}


