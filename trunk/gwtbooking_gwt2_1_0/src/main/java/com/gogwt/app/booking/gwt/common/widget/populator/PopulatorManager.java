package com.gogwt.app.booking.gwt.common.widget.populator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.allen_sauer.gwt.log.client.Log;
import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;
import com.gogwt.app.booking.gwt.common.utils.GWTJSUtils;
import com.gogwt.app.booking.rpc.interfaces.common.PopulatorRPCService;
import com.gogwt.framework.arch.utils.StringUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;


public class PopulatorManager {
	  
	 /**
	   * populatorRequest - Map<String,List<Populator>> - Populator Requests
	   */
	  private final static Map<String, List<Populator>> populatorRequest = new TreeMap<String, List<Populator>>();
	  
 	 /**
	   * <p>
	   * Add a populator request
	   * </p>
	   * @param populatorName
	   * @param widget
	   * @param callback
	   *          @ old way
	   */
	  public static void addRequest( String populatorKey, Populator widget )
	  {	      
	      if ( populatorRequest.containsKey( populatorKey ) ) {
	          // if request being made, then add to populator list
	    	  populatorRequest.get( populatorKey ).add( widget );
	      } else {
	         // if not, create new populator list
	         List<Populator> populators = new ArrayList<Populator>();
	         populators.add( widget );
	         populatorRequest.put( populatorKey, populators );
	      }
	    
	  }
	  
    /**
     * Handle popoulator: called from Entry class.
     *  
     * Example: 
     * Map<String, String> maps the value inside ReservationProcessConfig.xml
     * <property name="populators">
		  <map>
			<entry key="statesProvincesPopulator">com.gogwt.app.booking.populator.StatePopulator</entry>
			<entry key="titlePopulator">com.gogwt.app.booking.populator.TitlePopulator</entry>
		  </map>
	   </property>
     * @param populatorMap
     */
	public static void handlePopulators(final Map<String, String> populatorMap) {
	    if (populatorMap != null) {	     
	        Log.debug( "PopulatorManager.handlePopulators(Map<String, String> populatorMap): " + populatorMap.size() );
	        handlePopulators(populatorMap.keySet());
	    }	
		
	}

	private static void handlePopulators( final Set<String> populatorKeys ) {

		Log.info( "handlePopulators(Set<String> populatorIds)" );
	    
	    String populatorKey;
	    ArrayList<PopulatorItem> populatorItems;
	    
	    //if the page has populator	    
	    if ( populatorKeys != null && populatorKeys.size()>0 ) {	    
	      final HashMap<String, ArrayList<PopulatorItem>> serverPopulatorContents = getPopulatorDataFromServer();
	      
	      if (serverPopulatorContents == null) {
	    	  return;
	      }
	      
	      for( Entry<String, List<Populator>> entry : populatorRequest.entrySet() ) {
	    	  populatorKey = entry.getKey();
	    	  if (serverPopulatorContents.containsKey(populatorKey)) {
	    		  populatorItems = serverPopulatorContents.get(populatorKey);
	    		  
	    		  for( Populator populator : entry.getValue() ) {
	  	            populator.populate( populatorItems );
	  	          }
	    	  }
	      }
 	    }
	 }	
	

     /**
      * Get populator info from Javascript variable serializedPopulator generated page of JSP.
      * @return
      */
	  private static HashMap<String, ArrayList<PopulatorItem>> getPopulatorDataFromServer( ) {
	    
	    // check if serialized version available
	    String serializedPopulatorInfo = GWTJSUtils.getPopulatorsFromJS();
	    
	    if (StringUtils.isSet( serializedPopulatorInfo)) {
	    	Log.info( "getPopulatorDataFromServer() serializedPopulatorInfo present " );
	      try {
	        SerializationStreamFactory factory = (SerializationStreamFactory)GWT.create( PopulatorRPCService.class );
	        SerializationStreamReader reader;

	        reader = factory.createStreamReader( serializedPopulatorInfo );
	        HashMap<String, ArrayList<PopulatorItem>> populatorInfo = (HashMap<String, ArrayList<PopulatorItem>>)reader.readObject();
	        
	        return populatorInfo;
	        
	      } catch( Exception e ) {
	        // ignore
	    	  Log.fatal( "Error de-serializing populator information: ", e);
	      }
	    }
	    
	    return null;
	 }
	 
	 

}
