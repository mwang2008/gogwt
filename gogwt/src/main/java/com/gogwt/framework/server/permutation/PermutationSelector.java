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

package com.gogwt.framework.server.permutation;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.gogwt.framework.arch.utils.StringUtils;
import com.gogwt.framework.linker.PermutationConstants;

public class PermutationSelector implements PermutationConstants  {
	private static Logger logger = Logger.getLogger(PermutationSelector.class);
  
    
	private static Map<String, PermutationObject> permutationMap;

	private static PermutationSelector instance;

	private PermutationSelector() {
		super();
		permutationMap = new HashMap<String, PermutationObject>();
	}

	public PermutationObject processProperty(final String path)
			throws IOException {
		PermutationObject permutationObject = null;

		// get value from map
		permutationObject = permutationMap.get(path);

		if (permutationObject != null) {
			return permutationObject;
		}

		// not found, retrieve from file
		ClassLoader classloader = Thread.currentThread()
				.getContextClassLoader();
		InputStream inStream = classloader.getResourceAsStream(path);
		if (inStream == null) {
			throw new IOException(
					"could not find permutation.properties file under WEB-INF, the path="+ path + " make sure it is generated during gwt compile: mvn clean install");
		}

		permutationObject = new PermutationObject();

		Properties props = new Properties();
		props.load(inStream);
		permutationObject.setProperties(props);

		String pattern = props.getProperty(PATTERN);
		fillPattern(pattern, permutationObject);

		String moduleName = props.getProperty(MODULE_NAME);
		String moduleFunctionName = props.getProperty(MODULE_FUNC_NAME);
		permutationObject.setModuleName(moduleName);
		permutationObject.setModuleFunctionName(moduleFunctionName);
		
		permutationMap.put(path, permutationObject);

		return permutationObject;

	}

	/**
	 * 
	 * pattern=locale : [ default en_us ] $$ log_level : [ FATAL OFF ] $$
	 * user.agent : [ gecko gecko1_8 ie6 ie8 opera safari ]
	 * 
	 * @return
	 */
	private void fillPattern(String pattern, PermutationObject permuObj) {
		pattern = pattern.toLowerCase();
		
		String[] properties = pattern.split("\\$\\$");
		
		if (properties != null && properties.length > 0) {
			for (String prop : properties) {
				
				String[] nameValue = prop.split(":");
				
				if (value(nameValue[0]) != null) {
					permuObj.addPatternMap(value(nameValue[0]),
							value(nameValue[1]));
				}
			}
		}
	}

	private String value(final String value) {
		if (!StringUtils.isSet(value)) {
			return null;
		}

		return value.trim();
	}

	public static PermutationSelector getInstance() {
		if (instance == null) {
			instance = new PermutationSelector();
		}

		return instance;
	}
}
