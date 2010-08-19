package com.skeleton.example.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class MyFirstGWTEntryPoint implements EntryPoint {
	private static TagsResources tags = TagsResources.Util.getInstance();
	
	@Override
	public void onModuleLoad() {
		String welcome = tags.label_welcome();
		Label label = new Label(" HI this is my first GWT");
		RootPanel.get().add(label);
		RootPanel.get().add(new HTML("<b>" + welcome + "</b>"));
		
	}

}
