package com.gogwt.app.booking.gwt.reservation.linker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.SortedSet;

import com.google.gwt.core.ext.LinkerContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.AbstractLinker;
import com.google.gwt.core.ext.linker.ArtifactSet;
import com.google.gwt.core.ext.linker.CompilationResult;
import com.google.gwt.core.ext.linker.LinkerOrder;
import com.google.gwt.core.ext.linker.SelectionProperty;
import com.google.gwt.core.ext.linker.SyntheticArtifact;

//@LinkerOrder(LinkerOrder.Order.POST, PRE, PRIMARY
@LinkerOrder(LinkerOrder.Order.POST)
public class PermutationLinker extends AbstractLinker {
	public static final String PERMUTATION_FILE = "permutation.properties"; 
	
	@Override
	public String getDescription() {
 		return "permutation";
	}

	@Override
	public ArtifactSet link(TreeLogger logger, LinkerContext context,
			ArtifactSet artifacts) throws UnableToCompleteException {
		
		logger.log(TreeLogger.INFO,"start PermutationLinker");
		
		artifacts = new ArtifactSet(artifacts); 
		ByteArrayOutputStream out = new ByteArrayOutputStream(); 
 
		String permutation = null;	   
	         
	    Properties props = new Properties(); 
	    StringBuilder keyBuilder = null;
	    StringBuilder keyNamePattern = null;  
	    boolean hasPattern = false;
	    
		for (CompilationResult result : artifacts.find(CompilationResult.class)) {
			permutation = result.getStrongName(); 
			
			if (!hasPattern) {
			   keyNamePattern = new StringBuilder();
			}
			
			SortedSet<SortedMap<SelectionProperty, String>>	propertiesMap = result.getPropertyMap();
			
			keyBuilder = new StringBuilder();
		 	
			int index = 0;
			for (SortedMap<SelectionProperty, String> sm : propertiesMap) {
				index = 0;
                for (Map.Entry<SelectionProperty, String> e : sm.entrySet()) {
                 	                    
        			if (!hasPattern) {	       				 
                        keyNamePattern.append(e.getKey());
                        keyNamePattern.append("   ");
        			}    
        			
        			keyBuilder.append(e.getValue());
        			
        			if (index < sm.entrySet().size()-1) {
                       keyBuilder.append("|");  
        			}
        			
        			index ++;
                }
                
                if (!hasPattern) {
                   logger.log(TreeLogger.INFO, " #pattern -->  "+keyNamePattern.toString());                                     
                }
                
                logger.log(TreeLogger.INFO, "  " + keyBuilder.toString()+"="+permutation);
                props.setProperty(keyBuilder.toString(), permutation);
            } 
			
			hasPattern = true;
		}
		
		    try {
	            props.store(out, "permutation properties file");
	        } catch (IOException e) {  
	            logger.log(TreeLogger.ERROR, "Unable to store data", e);
	            throw new UnableToCompleteException();
	        }
	         
	        
	        SyntheticArtifact a = emitBytes(logger, out.toByteArray(),PERMUTATION_FILE);
	        artifacts.add(a); 

	    	if (out != null) {
        		try {
        		  out.close();
        		}
        		catch(IOException ie) {
        			ie.printStackTrace();
        			throw new UnableToCompleteException();
        		}
        	}
	    	
	        logger.log(TreeLogger.INFO,"end PermutationLinker");
	        
	        
        return artifacts;

	}

}
