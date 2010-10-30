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

package com.gogwt.framework.server.utils.google;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.gogwt.framework.server.utils.ClientInfo;

/**
 * 
 * <code><B>PermutationUtil<code><B>
 * This class is used to get info such as locale, useragent for lookup permutation
 * It needs to check when GWT is updated.
 * <p/>
 */
public abstract class PermutationUtil {
	private static final String USER_AGENT = "USER-AGENT";

	/**
	 * get user agent Copied from GWT generated nocach.js
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserAgent(final HttpServletRequest request) {
		String ua = request.getHeader(USER_AGENT);
		ua = ua.toLowerCase();

		ClientInfo clientInfo = new ClientInfo(request);
	 
		if (clientInfo.isOP()) {
			return "opera";
		}

		if (clientInfo.isSafari()) {
			return "safari";
		}

		if (clientInfo.isIE()) {
			if (clientInfo.getBrowserMajorVersion() >= 8) {
				return "ie8";
			}

			if (clientInfo.getBrowserMajorVersion() == 6) {
				return "ie6";
			}

			// for ie7, use ie6
			return "ie6";
		}

		if (ua.indexOf("gecko") != -1) {
			Pattern p = Pattern
					.compile("rv:([0-9]+)\\.([0-9]+)\\.([0-9]+)\\.([0-9]+)");
			Matcher m = p.matcher(ua);

			int rv = 0;
			if (m.find()) {
				try {
				   rv = Integer.parseInt(m.group(1)) * 1000
						+ Integer.parseInt(m.group(2)) * 100
						+ Integer.parseInt(m.group(3)) * 10
						+ Integer.parseInt(m.group(4));
				}
				catch (NumberFormatException e) {
				  //ignore, rv still equals 0	
				}
			}
			
			//default
			if (rv == 0 || rv>=1008) {
				return "gecko1_8";
			}
						
			return "gecko";			 
		}

		return "unknown";

	}

}
