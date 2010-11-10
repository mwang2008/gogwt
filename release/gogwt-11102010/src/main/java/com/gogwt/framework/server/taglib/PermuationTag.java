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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.gogwt.framework.arch.utils.StringUtils;
import com.gogwt.framework.linker.PermutationConstants;
import com.gogwt.framework.server.permutation.PermutationObject;
import com.gogwt.framework.server.permutation.PermutationSelector;
import com.gogwt.framework.server.utils.google.PermutationUtil;

public class PermuationTag extends TagSupport implements PermutationConstants,
		NameValueTag {
	private static Logger logger = Logger.getLogger(PermuationTag.class);

	private String module;
	private Map<String, String> params;
	private boolean inline;

	/**
	 * <p>
	 * Create a new instance of PermuationTag.
	 * </p>
	 */
	public PermuationTag() {
		params = new HashMap<String, String>();
		inline = false;
		module = null;
	}

	public int doStartTag() throws JspException {

		if (!StringUtils.isSet(module)) {
			logger.fatal("module is not set, please provide base: ");
			return SKIP_BODY;
		}
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		final HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		final HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

		JspWriter out = null;
		try {
			out = pageContext.getOut();

			final String userAgent = PermutationUtil.getUserAgent(request);

			Map<String, String> userSettingMap = params;
			// add user.agent if does not have.
			if (!userSettingMap.containsKey(USER_AGENT)) {
				userSettingMap.put(USER_AGENT, userAgent);
			}

			final PermutationObject permutationObject = getPermutationObjectFromPermutationFile();
			final String cacheHtmlFileName = getCacheHtmlFileName(
					permutationObject, userSettingMap);

			// locate general.nocache.js
			//final String generalNocacheJs = retriveGeneralnoCache(permutationObject);
            //System.out.println(" generalNocacheJs=" + generalNocacheJs);
            //out.print(generalNocacheJs);
            
			final String contextPath = request.getContextPath();
			//final String cacheFilePathName = contextPath + "/" + base + "/"	+ cacheHtmlFileName + ".cache.html";
			
			if (isInline()) {

				logger.fatal(" not support yet for inline cache.html");

			} else {
				
				String cacheFilePathName ="/"+ module + "/permutation.jsp?mp="	+ cacheHtmlFileName;
				System.out.println("cacheFilePathName= "+cacheFilePathName);
				
				/* it is generated on top of the html file. so it is not working.*/				
				final RequestDispatcher requestDispatcher = request.getRequestDispatcher(cacheFilePathName);
		        requestDispatcher.include(pageContext.getRequest(), pageContext.getResponse());
		        
				
				//cacheFilePathName = contextPath + cacheFilePathName;
		        //out.print("<jsp:include page=\"" + cacheFilePathName + "\" />");			
			}

		} catch (Exception e) {
			logger.fatal("errors when process permutation ", e);
			return SKIP_BODY;
		} finally {

		}
		return EVAL_PAGE;
	}

	/**
	 * Get general.nocache.js file and replace moduleFunctionName and moduleName
	 * @param permutationObject
	 * @return
	 * @throws Exception
	 */
	private String retriveGeneralnoCache(
			final PermutationObject permutationObject) throws Exception {
		
		String path = "com/gogwt/framework/server/taglib/" + GENERAL_NOCACHE_TEMPLATE;
		System.out.println(" -- path==" + path);
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream inStream = classloader.getResourceAsStream(path);
		if (inStream == null) {
			throw new IOException(
					"could not find " + GENERAL_NOCACHE_TEMPLATE + "file under resources.");
		}
		
		String generalNocahe = parseISToString(inStream);
		generalNocahe = generalNocahe.replaceAll("$moduleFunctionName$", permutationObject.getModuleFunctionName());
		generalNocahe = generalNocahe.replaceAll("$moduleName$", permutationObject.getModuleName());
		
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

	private PermutationObject getPermutationObjectFromPermutationFile()
			throws Exception {
		final String filePath = WEB_INF + "/" + module + "/"
				+ PERMUTATION_FILE;
		final PermutationObject permutationObject = PermutationSelector
				.getInstance().processProperty(filePath);
		logger.debug(permutationObject.toString());

		return permutationObject;
	}

	private String getCacheHtmlFileName(
			final PermutationObject permutationObject,
			final Map<String, String> userSettingMap) throws Exception {

		Map<String, String> patternMap = permutationObject.getPatternMap();

		String key, value, userValue;
		StringBuilder permutationKey = new StringBuilder();
		int index = 0;

		for (Map.Entry<String, String> pattern : patternMap.entrySet()) {
			key = pattern.getKey();
			value = pattern.getValue();

			if (userSettingMap.containsKey(key)) {
				// if value is inside the values like
				userValue = userSettingMap.get(key);

				if (value.contains(userValue)) {
					permutationKey.append(userValue);
				} else {
					throw new Exception("The value for the key=[" + key
							+ "] with value=[" + userValue
							+ "] does not in the list of : "
							+ pattern.getValue());
				}
			}

			if (index < patternMap.size() - 1) {
				permutationKey.append("|");
			}

			index++;
		}

		String cachehtml = permutationObject.getProperties().getProperty(
				permutationKey.toString());

		logger.debug("permutationKey=" + permutationKey + ", cachehtml="
				+ cachehtml);

		return cachehtml;

	}

	/**
	 * release this tag
	 */
	public void release() {
		super.release();
		module = null;
		inline = false;
		params = new HashMap<String, String>();
	}

	 

	public void addParam(String name, String value) {
		params.put(name, value);
	}

	public Map<String, String> getParams() {
		return params;
	}

	
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public boolean isInline() {
		return inline;
	}

	public void setInline(boolean inline) {
		this.inline = inline;
	}

}
