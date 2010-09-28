package com.gogwt.app.booking.gwt.reservation.client.widgets.guestinfo;

import com.gogwt.app.booking.dto.dataObjects.common.CommandBean;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.common.utils.WidgetStyleUtils;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.ErrorPanel;
import com.gogwt.app.booking.gwt.reservation.client.widgets.common.HasFormEntry;
import com.gogwt.framework.arch.utils.StringUtils;
import com.gogwt.framework.arch.widgets.AbstractWidget;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GuestInfoLayoutWidget extends AbstractWidget implements HasFormEntry {
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();
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
 	 * <p>
	 * Fill the layout
	 * </p>
	 */
	public void prepareEntryLayout() {
 	 	guestInfoFormEntry.prepareFormEntry(this);
		
		final FlexTable mainTable = WidgetStyleUtils.createFlexTable();
		mainTable.setCellPadding( 0 );
		mainTable.setCellSpacing( 0 );
		mainTable.setStyleName( "guestInformationTable" );
		
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
		
		//add extra whitespace 
		theFormPanel.add(new HTML("<br><br>"));
		
		//add error panel
		ErrorPanel.getInstance().initErrorPanel();
		theFormPanel.add(ErrorPanel.getInstance());
		
		FlexTable flexTable = WidgetStyleUtils.createFlexTable();
		flexTable.addStyleName( "guestInfoFormTable" );
		
		fillNewRow(flexTable, tags.label_Title(), REQUIRED_STYLE, formEntry.getTitle(), WIDGET_STYLE, VISIBLE );
		fillNewRow(flexTable, tags.label_First_Name(), REQUIRED_STYLE, formEntry.getFirstName(), WIDGET_STYLE, VISIBLE );
		fillNewRow(flexTable, tags.label_Last_Name(), REQUIRED_STYLE, formEntry.getLastName(), WIDGET_STYLE, VISIBLE );
		fillNewRow(flexTable, tags.Label_Address(), REQUIRED_STYLE, formEntry.getAddress(), WIDGET_STYLE, VISIBLE );
		fillNewRow(flexTable, tags.Label_city(), REQUIRED_STYLE, formEntry.getCity(), WIDGET_STYLE, VISIBLE );
		fillNewRow(flexTable, tags.Label_state(), REQUIRED_STYLE, formEntry.getStateId(), WIDGET_STYLE, VISIBLE );
		fillNewRow(flexTable, tags.Label_zip(), REQUIRED_STYLE, formEntry.getZipCode(), WIDGET_STYLE, VISIBLE );
		fillNewRow(flexTable, tags.label_email(), REQUIRED_STYLE, formEntry.getEmail(), WIDGET_STYLE, VISIBLE );
		fillNewRow(flexTable, "", REQUIRED_STYLE, formEntry.btnReserve, WIDGET_STYLE, VISIBLE );
		
		theFormPanel.add(formEntry.getSelectedIndex());
		theFormPanel.add(formEntry.getHotelId());
		theFormPanel.add(flexTable);
		 
		
		return theFormPanel;
	}
	
	private Panel fillRightPanel() {
		
		Panel rightPanel = WidgetStyleUtils.createFlowPanel();
		//rightPanel.add(new Label("right"));
		
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
	    table.setText( row, 0, fieldName + ": " );
	    table.getFlexCellFormatter().setAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
	    
	    if (StringUtils.isSet( fieldNameStyle )) {
	      table.getFlexCellFormatter().addStyleName( row, 0, fieldNameStyle );
	    }
	    table.setWidget( row, 1, widget );
	    if (StringUtils.isSet( widgetStyle )) {
	      table.getFlexCellFormatter().addStyleName( row, 1, widgetStyle );
	    }
	    table.getRowFormatter().setVisible( row, isRowVisible );
	    return row;
	  }  

}
