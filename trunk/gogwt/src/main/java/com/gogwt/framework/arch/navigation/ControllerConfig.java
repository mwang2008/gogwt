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

import com.gogwt.framework.arch.widgets.AbstractController;

public class ControllerConfig {
	private String name;	 
	private AbstractController instance;
	private Map<String, Map<String, String>> properties;
	private Map<String, String> forward;
	private Map<String, String> globalForward;
	
	public ControllerConfig() {
	}

	
	public ControllerConfig(String name, AbstractController instance) {
		super();
		this.instance = instance;
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public AbstractController getInstance() {
		return instance;
	}


	public void setInstance(AbstractController instance) {
		this.instance = instance;
	}


	public Map<String, Map<String, String>> getProperties() {
		return properties;
	}


	public void setProperties(Map<String, Map<String, String>> properties) {
		this.properties = properties;
	}


	public Map<String, String> getForward() {
		return forward;
	}


	public void setForward(Map<String, String> forward) {
		this.forward = forward;
	}


	public Map<String, String> getGlobalForward() {
		return globalForward;
	}


	public void setGlobalForward(Map<String, String> globalForward) {
		this.globalForward = globalForward;
	}
 
}
