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

package com.gogwt.demo.gwt.mvp.client.widgets.home.view;

import com.gogwt.framework.arch.widgets.View;
import com.google.gwt.user.client.ui.Widget;

public interface HomeView<T> extends View<T>{
	public interface Presenter<T> {
		void toDetail();
	}
	
	/**
	 * Connect to presenter
	 * @param presenter
	 */
	void setPresenter(Presenter<T> presenter);

	/**
	 * 
	 * @return
	 */
	Widget asWidget();
	

}
