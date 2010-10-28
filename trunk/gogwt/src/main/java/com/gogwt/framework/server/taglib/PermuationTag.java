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
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.gogwt.framework.arch.utils.StringUtils;

public class PermuationTag extends TagSupport {
	private static Logger logger = Logger.getLogger(PermuationTag.class);
	
	public static final String PERMUTATION_FILE = "permutation.properties";

	private String gwtBase;

	public int doEndTag() throws JspException {

		final HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		
		try {
			String theString = pageContext.getServletContext().getRealPath(
					getGwtBase() + "/" + PERMUTATION_FILE);
			System.out.println("  === theString="+ theString);
			
			String path = getPermuationFilePath();
			
			Properties props = new Properties();
			props.load(new FileInputStream(theString));

		 	String permutation = props.getProperty("en_us|OFF|gecko");
		 	String pattern = props.getProperty("pattern");
		 	System.out.println("pattern="+pattern);
		 	System.out.println("permutation="+permutation);
		 	
		} catch (Exception e) {
			logger.error("could not load file: " + PERMUTATION_FILE, e);
		}
		return EVAL_PAGE;
	}

	private String getPermuationFilePath() throws Exception {
		String base = getGwtBase();
		if (!StringUtils.isSet(base)) {
			throw new Exception("require gwtBase");
		}
		
		if (base.charAt(0) == '/') {
			base = base.substring(1);
		}
		
		if (base.charAt(base.length() -1) == '/') {
			base = base.substring(0, base.length()-2);
		}
		
		String path = pageContext.getServletContext().getRealPath(
				"/" + base + "/" + PERMUTATION_FILE);
		
		return path;
	}
	/**
	 * release this tag
	 */
	public void release() {
		super.release();

	}

	public String getGwtBase() {
		return gwtBase;
	}

	public void setGwtBase(String gwtBase) {
		this.gwtBase = gwtBase;
	}

}
