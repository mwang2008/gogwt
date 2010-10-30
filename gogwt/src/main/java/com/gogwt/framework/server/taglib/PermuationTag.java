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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.gogwt.framework.arch.utils.StringUtils;
import com.gogwt.framework.linker.PermutationConstants;
import com.gogwt.framework.server.permutation.PermutationObject;
import com.gogwt.framework.server.permutation.PermutationSelector;
import com.gogwt.framework.server.utils.google.PermutationUtil;
import com.ihg.dec.apps.webframework.taglib.NameValueTag;

public class PermuationTag extends TagSupport implements PermutationConstants, NameValueTag {
	private static Logger logger = Logger.getLogger(PermuationTag.class);

    private String base;
    private Map<String, String> params;
    
    /**
     * <p>
     * Create a new instance of PermuationTag.
     * </p>
     */
    public PermuationTag()
    {
    	params = new HashMap<String, String>();
    }
    
	public int doEndTag() throws JspException {

		final HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();

		try {
		 
			String filePath = WEB_INF + "/" + getBase() + "/" + PERMUTATION_FILE;
			final PermutationObject permutationObject = PermutationSelector
					.getInstance().processProperty(filePath);

			Map<String, String> userSettingMap = (Map<String, String>) request
					.getAttribute(REQ_PERMUTATION);
			if (userSettingMap == null) {
				throw new Exception(
						" Development problem: the permutaion map is not set in http request. "
								+ "The permutation map would be something like: {locale,en_us} {user.agent, geoko}, etc");
			}

			Map<String, String> permutationMap = permutationObject.getPatternMap();
			logger.debug(permutationObject.toString());

			String userAgent = PermutationUtil.getUserAgent(request);
			
			String permutationKey = getPermutationKey(userSettingMap, permutationObject, userAgent);
			String permutation = permutationObject.getProperty().getProperty(permutationKey);

			logger.debug("permutationKey="+permutationKey + ", permutation="+permutation);
 
		} catch (Exception e) {
			logger.fatal("errors when process permutation ", e);
		}
		return EVAL_PAGE;
	}

	private String getPermutationKey(final Map<String, String> userSettingMap, final PermutationObject permutationObject, String userAgent) throws Exception {
		
		
		Map<String, String> patternMap = permutationObject.getPatternMap();
		
		String key, value, userValue;
		StringBuilder permutationKey = new StringBuilder();
		int index = 0;
		for (Map.Entry<String, String> pattern : patternMap.entrySet()) {
			key = pattern.getKey();
			value = pattern.getValue();
			
			if (userSettingMap.containsKey(key)) {
				//if value is inside the values like
				userValue = userSettingMap.get(key);
				
				if (value.contains(userValue)) {
				   permutationKey.append(userValue);
				}
				else {
				    throw new Exception("The value for the key=["+ key + "] with value=[" + value + "] does not in the list of : " + pattern.getValue());
				}
			}
			else {
				//does not find key in userSetting
				if (StringUtils.equalsIgnoreCase(key, "user.agent")) {
					permutationKey.append(userAgent);
				}
				else {
					throw new Exception("Could not find specific value for the key= " + key);
				}
			}
			
			if (index < patternMap.size()-1) {
				permutationKey.append("|");
			}
		    
			index++;
		}
		
		return permutationKey.toString();
		
	}

	 

	/**
	 * release this tag
	 */
	public void release() {
		super.release();
		base = null;
		params = new HashMap<String, String>();
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public void addParam(String name, String value) {
		params.put( name, value );		
	}
 
	

}
