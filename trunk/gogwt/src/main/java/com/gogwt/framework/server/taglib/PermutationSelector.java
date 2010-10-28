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

package com.gogwt.framework.server.taglib;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PermutationSelector {
	private static Logger logger = Logger.getLogger(PermutationSelector.class);
    private static final String PATTERN = "pattern";
    
	private static Map<String, Properties> permutationMap;
	private static PermutationSelector instance;

	private PermutationSelector() {
		super();
		permutationMap = new HashMap<String, Properties>();
	}

	public void processProperty(final String path) {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream(path));

			String pattern = props.getProperty(PATTERN);

			
		} catch (IOException e) {
			logger.error("could not load file: " + path, e);
		}

	}

	public PermutationSelector getInstance() {
		if (instance == null) {
			instance = new PermutationSelector();
		}

		return instance;
	}
}
