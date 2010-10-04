package com.gogwt.app.booking.gwt.common.widget.populator;

import java.util.List;

import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;

public class ListBoxPopulator extends AbstractPopulator<ExtendedListBox> {
	private String initValue, initLable;
	
	/**
	 * <p>
	 * Create a new instance of ListBoxPopulator.
	 * </p>
	 * 
	 * @param name
	 * @param label
	 */
	public ListBoxPopulator(String name, String label) {
		super(name, label);
	}
	public ListBoxPopulator(String name, String label, String initValue, String initLable) {
		this(name, label);
		this.initValue = initValue;
		this.initLable = initLable;
		
	}
	
	@Override
	public ExtendedListBox obtainWidgetInstance(String label) {
		return new ExtendedListBox(label);
	}

	public void populate(List<PopulatorItem> data) {
		widget.clear();
		this.data = data;
		  
		if (initValue != null) {
		  widget.addItem( initLable, initValue );
		}
		  
		if (data != null) {
		  //reset list value
		   for (PopulatorItem populatorItem : data) {      
		      widget.addItem( populatorItem.getDisplay(), populatorItem.getCode() );
		   }
		}		
	}
}
