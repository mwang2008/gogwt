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

/**
 * <code><B>ControllerConfigAccessor<code><B>
 * 
 * <p/>
 */

public interface ControllerConfigAccessor {
	 public String toString();
	   
	   /**
	    * <p>
	    * Instantiates the view widgets involved in a particular navigation mapping
	    * If the view has already been instantiated, returns this instance or create a new one
	    * </p>
	    * @param token
	    * @return
	    */
	   ControllerConfig lazyCreateOrGetPageConfig( String token );

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
	   Map<String, ControllerConfig> getPageConfigInstances();
}
