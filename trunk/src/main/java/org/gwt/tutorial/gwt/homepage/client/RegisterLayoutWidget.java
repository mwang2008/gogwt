package org.gwt.tutorial.gwt.homepage.client;

import org.gwt.tutorial.gwt.homepage.client.i18n.TagsResources;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RegisterLayoutWidget extends Composite {
	private final static TagsResources tags = TagsResources.Util.getInstance();
	private final Panel mainPanel = new VerticalPanel();
	RegisterFormEntry formEntry; 
	
	public RegisterLayoutWidget() {
		super();
		formEntry = new RegisterFormEntry();
		initWidget(mainPanel);
	}


	/**
	 * layout
	 * @param baseFormEntry
	 */
	public void layout() {
		mainPanel.clear();
		
		Panel horizontalPanel = new HorizontalPanel();
		horizontalPanel.add(new Label(tags.label_First_Name_colon()));
		horizontalPanel.add(formEntry.getFirstname());
		mainPanel.add(horizontalPanel);
		
		horizontalPanel = new HorizontalPanel();
		horizontalPanel.add(new Label(tags.label_Last_Name_colon()));
		horizontalPanel.add(formEntry.getLastname());
		mainPanel.add(horizontalPanel);
		
		horizontalPanel = new HorizontalPanel();
		horizontalPanel.add(formEntry.getSubmitBtn());
		horizontalPanel.add(formEntry.getMyBtn());
		
		mainPanel.add(horizontalPanel);
	}
}
