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
public class PermutationLinker extends AbstractLinker {
	public static final String PERMUTATION_FILE = "permutation.properties";

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
							keyNamePattern.append("||");						
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
					
					permutationBuilder.append("pattern="+ keyNamePattern.toString());
					permutationBuilder.append("\n");
					
					hasPatternCollected = true;
				}

				//logger.log(TreeLogger.INFO, "  " + keyBuilder.toString() + "="	+ permutation);

				permutationBuilder.append(keyBuilder.toString());
				permutationBuilder.append("=");
				permutationBuilder.append(permutation);
				permutationBuilder.append("\n");				
			}
			outIndex++;
 		}

		
		SyntheticArtifact a = emitString(logger, permutationBuilder.toString(), PERMUTATION_FILE);
 		artifacts.add(a);
		 
 
		logger.log(TreeLogger.INFO,
				"end PermutationLinker : used "
						+ (System.currentTimeMillis() - startTime) + "ms save to " + PERMUTATION_FILE );

		return artifacts;

	}

}
