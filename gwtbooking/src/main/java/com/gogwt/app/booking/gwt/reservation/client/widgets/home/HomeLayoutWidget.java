package com.gogwt.app.booking.gwt.reservation.client.widgets.home;

import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.app.booking.gwt.reservation.client.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.ErrorPanel;
import com.gogwt.framework.arch.widgets.BaseWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * HomeWidget response to display home page.  
 * 
 * @author WangM
 * 
 */
public class HomeLayoutWidget extends BaseWidget {
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
		Panel rightPanel = WidgetStyleUtils.createVerticalPanel();
		rightPanel.getElement().setId("homeViewRight");
		
		rightPanel.add(new Label("Right Panel"));
		return rightPanel;
	}
	
	private Panel buildAndInitFormPanel() {
		Panel leftPanel = WidgetStyleUtils.createVerticalPanel();
		leftPanel.getElement().setId("homeViewLeft");
		
		Panel theFormPanel = WidgetStyleUtils.createVerticalPanel("homeSearchPanel");
		
		//add error panel
		ErrorPanel.getInstance().initErrorPanel();
		theFormPanel.add(ErrorPanel.getInstance());
		
/*		theFormPanel.add(new Label(
						"Ex of Destination->address: 1600 Pennsylvania Avenue, NW Washington, DC 20500 "));
		
		theFormPanel.add(new Label("Ex of Destination->city: atlanta, ga"));
*/
		
		theFormPanel.add(WidgetStyleUtils.createLabel(tags.Label_Destination(), "text12blue"));
		theFormPanel.add(formEntry.getDestination());
	 	
	    WidgetStyleUtils.addIdStylesToWidget(formEntry.getDestination(),
	            "destSubContainer", "quickResRow");
	    
		theFormPanel.add(WidgetStyleUtils.createLabel(tags.Label_Radius(), "text12blue"));
		theFormPanel.add(formEntry.getRadius());
		formEntry.getRadius().setStyleName("quickResRow");
		
		//WidgetStyleUtils.addIdStylesToWidget(formEntry.getRadius(),
	     //       "destSubContainer", "quickResRow");

		theFormPanel.add(formEntry.getBtnSelectDestination());
		
		// call formEntry to inti the form entry
		formEntry.prepareFormEntry();
		
		leftPanel.add(theFormPanel);
		
		return leftPanel;
	}
}
