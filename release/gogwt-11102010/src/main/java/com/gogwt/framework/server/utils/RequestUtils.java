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

package com.gogwt.framework.server.utils;

import javax.servlet.http.HttpServletRequest;

public abstract class RequestUtils {
	private static final String X_FORWARDED_FOR = "X-FORWARDED-FOR";
	private static final String USER_AGENT = "User-Agent";
	public static final String HOST_HEADER = "HOST";
	public static final String PORT_HEADER = "PORT";
	public static final String X_FORWARDED_HOST_HEADER = "X-FORWARDED-HOST";
	public static final String X_FORWARDED_PORT_HEADER = "X-FORWARDED-PORT";
    public static final int HTTP_PORT = 80;
    public static final int HTTPS_PORT = 443;
    
	private RequestUtils() {
	}

	/**
	 * <p>
	 * Get host name from header or X-FORWARDED-HOST which redirect from apache
	 * server
	 * </p>
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	public static String getRequestHostName(
			final HttpServletRequest httpServletRequest) {
		String hostName = null;
		if ((hostName = httpServletRequest.getHeader(X_FORWARDED_HOST_HEADER)) == null) {
			hostName = httpServletRequest.getHeader(HOST_HEADER);
		}

		return hostName;
	}

	/**
	 * Get user's IP, Search for Apache IP, if could not find get from request
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserIPAddress(final HttpServletRequest request) {
		String remoteHost = request.getHeader(X_FORWARDED_FOR);

		if (remoteHost == null) {
			remoteHost = request.getRemoteHost();
		}

		return remoteHost;

	}

	/**
	 * Get user agent
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserAgent(final HttpServletRequest request) {
		return request.getHeader(USER_AGENT);
	}
}
