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

package com.gogwt.framework.server.seo;

public class CrawlConfigData {
	private int waitForBackgroundJavaScript;
	private int timedout;
    private int waitForBackgroundJavaScriptStartingBefore;
    
	public int getWaitForBackgroundJavaScript() {
		return waitForBackgroundJavaScript;
	}

	public void setWaitForBackgroundJavaScript(int waitForBackgroundJavaScript) {
		this.waitForBackgroundJavaScript = waitForBackgroundJavaScript;
	}

	public int getTimedout() {
		return timedout;
	}

	public void setTimedout(int timedout) {
		this.timedout = timedout;
	}

	public int getWaitForBackgroundJavaScriptStartingBefore() {
		return waitForBackgroundJavaScriptStartingBefore;
	}

	public void setWaitForBackgroundJavaScriptStartingBefore(
			int waitForBackgroundJavaScriptStartingBefore) {
		this.waitForBackgroundJavaScriptStartingBefore = waitForBackgroundJavaScriptStartingBefore;
	}

	
}
