package com.skeleton.example.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.skeleton.example.gwt.client.TagsResources;

public class MyFirstGWTEntryPoint implements EntryPoint {
	private static TagsResources tags = TagsResources.Util.getInstance();
	
	//private static final Binder binder = GWT.create(Binder.class);
	//interface Binder extends UiBinder<DockLayoutPanel, MyFirstGWTEntryPoint> { }
	
	 
	public void onModuleLoad() {
	/*	
	    String welcome = tags.label_welcome();
		Label label = new Label(" HI this is my first GWT");
		RootPanel.get().add(label);
		RootPanel.get().add(new HTML("<b>" + welcome + "</b>"));
		
	*/
		
		MyBinderWidget w = new MyBinderWidget();
        RootPanel.get().add(w);

		
		// Create the UI defined in MyFirstGWTEntryPoint.ui.xml.
	   // DockLayoutPanel outer = binder.createAndBindUi(this);
	    
	    //RootLayoutPanel root = RootLayoutPanel.get();
	    //root.add(outer);
	}

}
