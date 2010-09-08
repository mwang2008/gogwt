package com.gogwt.app.booking.gwt.reservation.client.widgets.guestinfo;

import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.ErrorPanel;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.HasFormEntry;
import com.gogwt.framework.arch.utils.GWTStringUtils;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GuestInfoLayoutWidget extends AbstractWidget implements HasFormEntry {
	private static final String WIDGET_STYLE = "guestInfoWidget";
	private static final String REQUIRED_STYLE = "guestInfoRequiredField";
	
	private boolean VISIBLE = true;
	
	private Panel layoutPanel = new FlowPanel();
	private GuestInfoFormEntry guestInfoFormEntry;

	public GuestInfoLayoutWidget() {
		super();
		guestInfoFormEntry = new GuestInfoFormEntry();
		initWidget(layoutPanel);
		
		
	}
	
	/**
	 *     A----------------------------B
	 *     |                            |
	 *     C-------------D--------------E
	 *     |             |              |
	 *     |             |              |
	 *     F-------------G--------------H
	 *     
	 *     
	 * <p>
	 * Fill the layout
	 * </p>
	 */
	public void prepareEntryLayout() {
		layoutPanel.add(new Label("Guest info"));
		
	 	guestInfoFormEntry.prepareFormEntry(this);
		
		final FlexTable mainTable = WidgetStyleUtils.createFlexTable();
		mainTable.setCellPadding( 0 );
		mainTable.setCellSpacing( 0 );
		//mainTable.setStyleName( "guestInformationTable" );
		
		final Panel leftFormTable =  fillFormPanel(guestInfoFormEntry);
		final Panel rightTable =  fillRightPanel();
		
		final int row = mainTable.getRowCount();
		mainTable.setWidget( row, 0, leftFormTable );
		mainTable.setWidget( row, 1, rightTable );
				
		layoutPanel.add(mainTable);
	}

	/**
	 * Call back from GuestInfoFormEntry
	 */
	public void fromFormEntry(CommandBean command) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Error panel and form panel
	 * @param formEntry
	 * @return
	 */
	private Panel fillFormPanel(GuestInfoFormEntry formEntry) {
		Panel theFormPanel = new VerticalPanel();
		
		//add error panel
		ErrorPanel.getInstance().initErrorPanel();
		theFormPanel.add(ErrorPanel.getInstance());
		
		FlexTable flexTable = WidgetStyleUtils.createFlexTable();
		flexTable.addStyleName( "guestInfoFormTable" );
		
		fillNewRow(flexTable, formEntry.firstNameLabel, REQUIRED_STYLE, formEntry.firstName, WIDGET_STYLE, VISIBLE );
		fillNewRow(flexTable, formEntry.lastNameLabel, REQUIRED_STYLE, formEntry.lastName, WIDGET_STYLE, VISIBLE );
		fillNewRow(flexTable, formEntry.addressLabel, REQUIRED_STYLE, formEntry.address, WIDGET_STYLE, VISIBLE );
		fillNewRow(flexTable, formEntry.cityLabel, REQUIRED_STYLE, formEntry.city, WIDGET_STYLE, VISIBLE );
		fillNewRow(flexTable, formEntry.stateIdLabel, REQUIRED_STYLE, formEntry.stateId, WIDGET_STYLE, VISIBLE );
		fillNewRow(flexTable, formEntry.zipCodeLabel, REQUIRED_STYLE, formEntry.zipCode, WIDGET_STYLE, VISIBLE );
		fillNewRow(flexTable, formEntry.emailLabel, REQUIRED_STYLE, formEntry.email, WIDGET_STYLE, VISIBLE );
		fillNewRow(flexTable, "", REQUIRED_STYLE, formEntry.btnReserve, WIDGET_STYLE, VISIBLE );
		
		theFormPanel.add(flexTable);
		
		return theFormPanel;
	}
	
	private Panel fillRightPanel() {
		
		Panel rightPanel = WidgetStyleUtils.createFlowPanel();
		rightPanel.add(new Label("right"));
		
		return rightPanel;			
	}
	
	 /**
	   * <p>
	   * create a new table row with 2 columns: 
	   *  <li>column 1: table.setText
	   *  <li>column 2: table.setWidget
	   * </p>
	   * @param table FlexTable
	   * @param fieldName String
	   * @param fieldNameStyle String
	   * @param widget Widget
	   * @param widgetStyle String
	   * @param isRowVisiable boolean
	   */
	  public int fillNewRow( final FlexTable table,
	    final String fieldName, final String fieldNameStyle,
	    final Widget widget, final String widgetStyle,
	    final boolean isRowVisible)
	  {
	    final int row = table.getRowCount();
	    table.setText( row, 0, fieldName );
	    if (GWTStringUtils.isSet( fieldNameStyle )) {
	      table.getFlexCellFormatter().addStyleName( row, 0, fieldNameStyle );
	    }
	    table.setWidget( row, 1, widget );
	    if (GWTStringUtils.isSet( widgetStyle )) {
	      table.getFlexCellFormatter().addStyleName( row, 1, widgetStyle );
	    }
	    table.getRowFormatter().setVisible( row, isRowVisible );
	    return row;
	  }  

}
