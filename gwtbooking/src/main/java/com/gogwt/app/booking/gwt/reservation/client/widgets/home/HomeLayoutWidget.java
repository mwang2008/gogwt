package com.gogwt.app.booking.gwt.reservation.client.widgets.home;

import com.gogwt.app.booking.gwt.reservation.client.widgets.common.ErrorPanel;
import com.gogwt.framework.arch.widgets.BaseWidget;
import com.google.gwt.user.client.ui.FlowPanel;
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
	HomeFormEntry formEntry;
	Panel layoutPanel = new FlowPanel();

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
		layoutPanel.add(new Label(" Home Widget"));
		
		Panel theFormPanel = new VerticalPanel();

		//add error panel
		ErrorPanel.getInstance().initErrorPanel();
		theFormPanel.add(ErrorPanel.getInstance());
		
		theFormPanel.add(new Label(
						"Ex of Destination->address: 1600 Pennsylvania Avenue, NW Washington, DC 20500 "));
		
		theFormPanel.add(new Label("Ex of Destination->city: atlanta, ga"));

		theFormPanel.add(new Label("Destination:"));
		theFormPanel.add(formEntry.getDestination());

		theFormPanel.add(new Label("Radius"));
		theFormPanel.add(formEntry.getRadius());

		theFormPanel.add(formEntry.getBtnSelectDestination());

		layoutPanel.add(theFormPanel);
		
		// call formEntry to inti the form entry
		formEntry.prepareFormEntry();
	}
}
