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

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
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

	private String base;
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
		base = null;
	}

	public int doStartTag() throws JspException {

		if (!StringUtils.isSet(base)) {
			logger.fatal("base is not set, please provide base: ");
			return SKIP_BODY;
		}
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		final HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();

		JspWriter out = null;
		try {
			out = pageContext.getOut();

			final String userAgent = PermutationUtil.getUserAgent(request);

			Map<String, String> userSettingMap = params;
			// add user.agent if does not have.
			if (!userSettingMap.containsKey(USER_AGENT)) {
				userSettingMap.put(USER_AGENT, userAgent);
			}

			String cacheHtmlFileName = getCacheHtmlFileName(userSettingMap);

			String contextPath = request.getContextPath();
			String cacheFilePathName = contextPath + "/" + base + "/" + cacheHtmlFileName + ".cache.html";
			//<iframe src="javascript:''" id="com.gogwt.demo.gwt.navigation.NavigationModule" tabIndex='-1' style="position:absolute;width:0;height:0;border:none"></iframe>

			
			if (isInline()) {
				System.out.println(" inline true " + cacheFilePathName);
				//out.print("inline true." + cacheFilePathName);
				
				StringBuilder iframeBuilder = new StringBuilder();
				
				iframeBuilder.append("<iframe src=\"javascript:''\"");				 
				iframeBuilder.append(" id=\"");
				iframeBuilder.append(base);
				iframeBuilder.append("\" tabIndex=\"-1\" style=\"position:absolute;width:0;height:0;border:none\">");
				
				//out.print(iframeBuilder.toString());
				try {
				    String path = base + "/" + cacheHtmlFileName + ".cache.html";
			        final RequestDispatcher requestDispatcher = request.getRequestDispatcher(path );
			        requestDispatcher.include( request, pageContext.getResponse() );
				}
				catch (Throwable e) {
					e.printStackTrace();
				}
				//iframeBuilder.append(cacheFilePathName);
				//iframeBuilder.append("</iframe>");

				//out.print("</iframe>");

			} else {
				StringBuilder iframeBuilder = new StringBuilder();
			
				iframeBuilder.append("<iframe src=\"");
				iframeBuilder.append(cacheFilePathName);
				iframeBuilder.append("\" id=\"");
				iframeBuilder.append(base);
				iframeBuilder.append("\" tabIndex=\"-1\" style=\"position:absolute;width:0;height:0;border:none\">");
				iframeBuilder.append("</iframe>");

				out.print(iframeBuilder.toString());
				System.out.println(" inline false " + cacheFilePathName);
				out.print("inline false." + cacheFilePathName);

			}

		} catch (Exception e) {
			logger.fatal("errors when process permutation ", e);
			return SKIP_BODY;
		} finally {

		}
		return EVAL_PAGE;
	}

	private String getCacheHtmlFileName(Map<String, String> userSettingMap)
			throws Exception {

		final String filePath = WEB_INF + "/" + getBase() + "/"
				+ PERMUTATION_FILE;
		final PermutationObject permutationObject = PermutationSelector
				.getInstance().processProperty(filePath);

		if (userSettingMap == null) {
			throw new Exception(
					" Development problem: the permutaion map is not set in http request. "
							+ "The permutation map would be something like: {locale,en_us} {user.agent, geoko}, etc");
		}

		logger.debug(permutationObject.toString());

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
		base = null;
		inline = false;
		params = new HashMap<String, String>();
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public void addParam(String name, String value) {
		params.put(name, value);
	}

	public Map<String, String> getParams() {
		return params;
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
