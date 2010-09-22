package com.gogwt.app.booking.gwt.reservation.client.widgets.home;

import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.ErrorPanel;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * HomeWidget response to display home page.  
 * 
 * @author WangM
 * 
 */
public class HomeLayoutWidget extends AbstractWidget {
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();
	
	HomeFormEntry formEntry;
	 
	private Panel layoutPanel = WidgetStyleUtils.createHorizontalPanel();
	
	public HomeLayoutWidget() {
		super();
		formEntry = new HomeFormEntry();
		initWidget(layoutPanel);
	}

	/**
	 * <p>
	 * Fill the layout
	 * </p>
	 */
	public void prepareEntryLayout() {
		layoutPanel.clear();
		layoutPanel.getElement().setId( "homeViewId" );
		
		Panel theFormPanel = buildAndInitFormPanel();
		Panel rightPanel = buildRightPanel();
		
		layoutPanel.add(theFormPanel);
		layoutPanel.add(rightPanel);
 
	}
	
	private Panel buildRightPanel() {
		Panel rightPanel = WidgetStyleUtils.createVerticalPanel("homeViewRight");
		rightPanel.getElement().setId("homeViewRight");
		
		rightPanel.add(new Label("Validate input of destination:"));
		
	 	String html = "<li>  Full Address </li>"
			   + "<li>  City, State; example: Atlanta, GA </li>"
			   + "<li>  Latitude,Longitude. ex: 33.754487,-84.389663 </li>"
			   + "<li>  Airport code</li>";
		HTMLPanel htmlPanel = new HTMLPanel(html);
		htmlPanel.addStyleName("destSubContainer");
		rightPanel.add(htmlPanel);
		
 		return rightPanel;
	}
	 
	 
	private Panel buildAndInitFormPanel() {
		Panel leftPanel = WidgetStyleUtils.createVerticalPanel();
		leftPanel.getElement().setId("homeViewLeft");
		
		Panel theFormPanel = WidgetStyleUtils.createVerticalPanel("homeSearchPanel");
		
		//add error panel
		ErrorPanel.getInstance().initErrorPanel();
		theFormPanel.add(ErrorPanel.getInstance());
				
		theFormPanel.add(WidgetStyleUtils.createLabel(tags.Label_Destination(), "text12blue"));
		theFormPanel.add(formEntry.getDestination());
		 
	    WidgetStyleUtils.addIdStylesToWidget(formEntry.getDestination(),
	            "destSubContainer", "quickResRow");
	    
		theFormPanel.add(WidgetStyleUtils.createLabel(tags.Label_Radius(), "text12blue"));
		theFormPanel.add(formEntry.getRadius());
		formEntry.getRadius().setStyleName("quickResRow");
 
		theFormPanel.add(formEntry.getBtnSelectDestination());
		
		// call formEntry to inti the form entry
		formEntry.prepareFormEntry();
		
		leftPanel.add(theFormPanel);
		
		return leftPanel;
	}
}
