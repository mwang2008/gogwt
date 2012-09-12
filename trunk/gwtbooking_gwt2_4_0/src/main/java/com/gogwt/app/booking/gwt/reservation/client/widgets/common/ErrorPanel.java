package com.gogwt.app.booking.gwt.reservation.client.widgets.common;

 
import java.util.ArrayList;

import com.gogwt.framework.arch.utils.StringUtils;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ErrorPanel  extends Composite {
	private static ErrorPanel instance = new ErrorPanel();
	
	 
	private Panel errorPanel = new VerticalPanel();

	public ErrorPanel() {
		super();
		initWidget(errorPanel);
		
	}
	
	public void displayError(ArrayList<String> errorList) {
		errorPanel.clear();
		if (StringUtils.isSet(errorList)) {
			for (String error : errorList) {
				errorPanel.add(new HTML("<div class='text12red'><li>" +  error + "</li></div>"));
			}
		}
 	}
	
	public void displayError(String error) {
		errorPanel.clear();
		if (StringUtils.isSet(error)) {
			errorPanel.add(new HTML("<div class='text12red'><li>" +  error + "</li></div>"));			 
		}
 	}
	
	public void initErrorPanel() {
		errorPanel.clear();
	}
 
	
    public static ErrorPanel getInstance() {
    	return instance;
    }
}
