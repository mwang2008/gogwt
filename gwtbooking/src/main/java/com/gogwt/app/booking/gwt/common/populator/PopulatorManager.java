package com.gogwt.app.booking.gwt.common.populator;

import java.util.Map;

public class PopulatorManager {
 
    /**
     * Map<String, String> maps the value inside ProcessConfig.xml
     * <property name="populators">
		  <map>
			 <entry key="statesProvincesPopulator">com.gogwt.app.booking.populator.StatePopulator</entry>
			 <entry key="countryPopulator">com.gogwt.app.booking.populator.TitlePopulator</entry>
		  </map>
	   </property>
     * @param populatorMap
     */
	public static void handlePopulators(final Map<String, String> populatorMap) {
		//not populator for the view, just return
		if (populatorMap == null) {
			return;
		}

        //todo: for simplicity of gogwt, use list directly. 		
		
	}

	
}
