package com.gogwt.framework.arch.widgets;

import java.util.ArrayList;

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public abstract class AbstractRequestWidget extends Composite {
	public @UiField HTML errorMessage;
	
	public void dispErrorMsg(ArrayList<String> msgs) {
		StringBuilder sb = new StringBuilder();		
		if (msgs != null && !msgs.isEmpty()) {
			for (String msg : msgs) {
				sb.append("<li>" + msg + "</li>");
 			}
		}			 	
		errorMessage.setHTML(sb.toString());
	}
}
