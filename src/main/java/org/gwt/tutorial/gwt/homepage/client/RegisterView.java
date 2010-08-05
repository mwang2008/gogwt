package org.gwt.tutorial.gwt.homepage.client;

import org.gwt.tutorial.gwt.homepage.client.i18n.TagsResources;

import com.google.gwt.user.client.ui.Label;
import com.gwtview.arch.widgets.AbstractView;
import com.gwtview.arch.widgets.ActionForm;

public class RegisterView extends AbstractView  {
	private final static TagsResources tags = TagsResources.Util.getInstance();
	
	
	/**
	 * 
	 */
	public void process(ActionForm actionForm) {
		final Label l = new Label(tags.label_welcome());
		viewPanel.add(l);
		
		RegisterLayoutWidget registerLayoutEntry = new RegisterLayoutWidget();
		 
		registerLayoutEntry.layout( );
		
		viewPanel.add(registerLayoutEntry);
	}
}
