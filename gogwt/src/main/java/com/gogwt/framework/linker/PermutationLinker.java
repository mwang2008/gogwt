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
package com.gogwt.framework.linker;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
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

/**
 * <code><B>PermutationLinker<code><B>
 * 
 * @LinkerOrder(LinkerOrder.Order.POST, PRE, PRIMARY <p/>
 */

@LinkerOrder(LinkerOrder.Order.POST)
public class PermutationLinker extends AbstractLinker implements PermutationConstants {
	

	@Override
	public String getDescription() {
		return "permutation";
	}

	@Override
	public ArtifactSet link(TreeLogger logger, LinkerContext context,
			ArtifactSet artifacts) throws UnableToCompleteException {

		logger.log(TreeLogger.INFO, "start PermutationLinker");

		long startTime = System.currentTimeMillis();

		artifacts = new ArtifactSet(artifacts);

		
		StringBuilder permutationBuilder = new StringBuilder();

		 
		String permutation = null;
        String moduleName = null;
        String moduleFunctionName = null;
		StringBuilder keyBuilder = null;
		StringBuilder keyNamePattern = null;
		boolean hasPatternCollected = false;
		
		int outIndex = 0;
		for (CompilationResult result : artifacts.find(CompilationResult.class)) {
			permutation = result.getStrongName();
			
			if (!hasPatternCollected) {
				keyNamePattern = new StringBuilder();
			}

			SortedSet<SortedMap<SelectionProperty, String>> propertiesMap = result.getPropertyMap();

			//keyBuilder = new StringBuilder();
 			int index = 0;
			for (SortedMap<SelectionProperty, String> sm : propertiesMap) {
				index = 0;
				keyBuilder = new StringBuilder();
				for (Map.Entry<SelectionProperty, String> e : sm.entrySet()) {

					if (!hasPatternCollected) {
						keyNamePattern.append(e.getKey());	
						if (index < sm.entrySet().size() - 1) {
						   keyNamePattern.append(" " + PERMUTATION_DELIMITER + " ");
						}
					}

					keyBuilder.append(e.getValue());

					//logger.log(TreeLogger.INFO, "keyBuilder["+ outIndex +","+index+"]="+index + "," + e.getValue());
					
					if (index < sm.entrySet().size() - 1) {
						keyBuilder.append("|");						
					}
					index++;
				}

				if (!hasPatternCollected) {
					logger.log(TreeLogger.INFO, " #pattern -->  "+ keyNamePattern.toString());
					
					moduleName = context.getModuleName();
					moduleFunctionName = context.getModuleFunctionName();

					permutationBuilder.append("pattern="+ keyNamePattern.toString().toLowerCase());
					permutationBuilder.append("\n");
					permutationBuilder.append("moduleFunctionName="+ moduleFunctionName);
					permutationBuilder.append("\n");
					permutationBuilder.append("moduleName="+ moduleName);
					permutationBuilder.append("\n");
					
					String noCachefile = retriveGeneralNoCache(logger, moduleFunctionName, moduleName);
					 
					SyntheticArtifact jspFile = emitString(logger, noCachefile, PERMUTATION_JSP_FILE);
					  
			 		artifacts.add(jspFile);
			 		
					hasPatternCollected = true;
				}

				//logger.log(TreeLogger.INFO, "  " + keyBuilder.toString() + "="	+ permutation);

				permutationBuilder.append(keyBuilder.toString().toLowerCase());
				permutationBuilder.append("=");
				permutationBuilder.append(permutation);
				permutationBuilder.append("\n");				
			}
			outIndex++;
 		}

		 
		String filePath = "../" + WEB_INF + "/" + moduleName + "/" + PERMUTATION_FILE;
 		 
		SyntheticArtifact syntheticArtifact = emitString(logger, permutationBuilder.toString(), filePath);
		  
 		artifacts.add(syntheticArtifact);
		 
 

 		
 		logger.log(TreeLogger.INFO,
				" used "
						+ (System.currentTimeMillis() - startTime) + "ms; save to " + filePath );
		logger.log(TreeLogger.INFO,
				"end PermutationLinker");

		return artifacts;

	}
	/**
	 * Get general.nocache.js file and replace moduleFunctionName and moduleName
	 * @param permutationObject
	 * @return
	 * @throws Exception
	 */
	private String retriveGeneralNoCache(TreeLogger logger, final String moduleFunctionName, final String moduleName) throws UnableToCompleteException {
		
		String packageName = this.getClass().getPackage().getName();
 		
		packageName = packageName.replaceAll("\\.","/");
 		
		String path = packageName + "/" + GENERAL_NOCACHE_TEMPLATE;		
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream inStream = classloader.getResourceAsStream(path);
		if (inStream == null) {
			logger.log(TreeLogger.ERROR, "could not find " + path + " file.");
			throw new UnableToCompleteException();
					 
		}
		
		String generalNocahe = parseISToString(inStream);
		generalNocahe = generalNocahe.replaceAll("%%moduleFunctionName%%", moduleFunctionName);
		generalNocahe = generalNocahe.replaceAll("%%moduleName%%", moduleName);
		
		return generalNocahe;
	}

	 public String parseISToString(java.io.InputStream is){
	      
	        java.io.BufferedReader din = new java.io.BufferedReader(new InputStreamReader(is));
	        StringBuffer sb = new StringBuffer();
	        try{
	            String line = null;
	            while((line=din.readLine()) != null){
	                sb.append(line+"\n");
	            }
	        }catch(Exception ex){
	            ex.getMessage();
	        }finally{
	            try{
	                is.close();
	            }catch(Exception ex){}
	        }
	        return sb.toString();
	    }
}
