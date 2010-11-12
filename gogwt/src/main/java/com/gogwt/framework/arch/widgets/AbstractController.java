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

package com.gogwt.framework.arch.widgets;

import java.util.HashMap;
import java.util.Map;

import com.gogwt.framework.arch.utils.StringUtils;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.MetaElement;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;


public abstract class AbstractController extends Composite {
	protected final Panel controlPanel;
	
	public AbstractController() {
		controlPanel = new FlowPanel();
		initWidget(controlPanel);
	}

	/**
	 * sub class should implemente it
	 */
	public abstract void process();

	public void preProcess() {
	}

	public void postProcess() {
	}

	/**
	 * subclass needs to provide PageMetaInfo, such as title, description, keyword. 
	 * @param pageInfo 
	 */
	protected abstract void fillMetaInfo(final PageMetaInfo pageInfo);

	protected String getCurrentToken() {
		return History.getToken();
	}
	
	/**
	 * Called from AbstractEntry to set page title, keywords, description
	 */
	public final void setPageMetaInfo() {
		// 1. get page info
		PageMetaInfo pageInfo = new PageMetaInfo();
		fillMetaInfo(pageInfo);

		// 2. set page title section
		if (StringUtils.isSet(pageInfo.getTitle())) {
			Window.setTitle(pageInfo.getTitle());
		}

		// 3. rewrite meta: description, keywords
		if (StringUtils.isSet(pageInfo.getDescription())) {
		   rewriteMetaContent("description", pageInfo.getDescription());
		}
		
		if (StringUtils.isSet(pageInfo.getKeywords())) {
		   rewriteMetaContent("keywords", pageInfo.getKeywords());
		}
		
		// 4. set other meta data
		addMetaData(pageInfo.getMetaMap());
	}
	
	 /**
	   * Rewrite meta content
	   * @param key
	   * @param value
	   * @param nodeList
	   */
	  private void rewriteMetaContent( final String key, final String value ) {
		final NodeList<Element> nodeList = Document.get().getElementsByTagName("meta");
	    if ( nodeList == null ) {
	      return;
	    }

	    for( int i = 0; i < nodeList.getLength(); i++ ) {
	      Element element = nodeList.getItem( i );
	      if ( key.equalsIgnoreCase( element.getAttribute( "name" ) ) ) {
	        element.setAttribute( "content", value );
	        return;
	      }
	    }
	    
	    //not found, add new one
	    addNewMetaData(key, value);
 	  }
	  
	  private void addNewMetaData(final String key, final String value) {
		  final NodeList<Element> headerList = Document.get().getElementsByTagName("head");
		  Element head = headerList.getItem(0);
		  
		  MetaElement metaElement = Document.get().createMetaElement();
		  metaElement.setName(key);
		  metaElement.setContent(value);
		  
		  head.appendChild(metaElement);
 	  }
	  
	  private void addMetaData(final HashMap<String, String>  metaMap) {
		  if (metaMap == null || metaMap.isEmpty()) {
			  return;
		  }
		  
		  String key, value;
		  for (Map.Entry<String, String> entry : metaMap.entrySet()) {
			  key = entry.getKey();
			  value = entry.getValue();
			  rewriteMetaContent(key, value);			 
		  }		  
	  }
}
