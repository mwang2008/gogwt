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

package com.gogwt.demo.gwt.navigation.client.navigation;

import com.gogwt.demo.gwt.navigation.client.widgets.info.InfoWidget;
import com.gogwt.framework.arch.widgets.AbstractPage;

public class InfoView extends AbstractPage {

	@Override
	public void process() {
		this.pagePanel.clear();
		
		InfoWidget infoWidget = new InfoWidget();
		this.pagePanel.add(infoWidget);	
		
		infoWidget.display();
	}

}
