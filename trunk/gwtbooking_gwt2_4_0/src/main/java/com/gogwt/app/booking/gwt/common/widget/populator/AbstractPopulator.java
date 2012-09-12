package com.gogwt.app.booking.gwt.common.widget.populator;

import java.util.List;

import com.gogwt.app.booking.dto.dataObjects.common.PopulatorItem;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractPopulator<WIDGET extends Widget> implements Populator {
	private final String name;
	private final String label;
	protected List<PopulatorItem> data;
	protected final WIDGET widget;

	/**
	 * <p>
	 * Create a new instance of AbstractPopulator.
	 * </p>
	 * 
	 * @param name
	 */
	protected AbstractPopulator(String name, String label) {
		this.name = name;
		this.label = label;
		this.widget = obtainWidgetInstance(label);
		PopulatorManager.addRequest(name, this);
	}
 

	
	/**
	 * <p>
	 * Provides an instance of the widget associated with the populator
	 * </p>
	 * 
	 * @return
	 */
	public abstract WIDGET obtainWidgetInstance(String label);

	/**
	 * <p>
	 * See {@link #setname(String)}
	 * </p>
	 * 
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>
	 * See {@link #setwidget(WIDGET)}
	 * </p>
	 * 
	 * @return Returns the widget.
	 */
	public WIDGET getWidget() {
		return widget;
	}

	/**
	 * <p>
	 * See {@link #setlabel(String)}
	 * </p>
	 * 
	 * @return Returns the label.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * <p>
	 * See {@link #setdata(List<PopulatorItem>)}
	 * </p>
	 * 
	 * @return Returns the data.
	 */
	public List<PopulatorItem> getData() {
		return data;
	}

	/**
	 * <p>
	 * Set the value of <code>data</code>.
	 * </p>
	 * 
	 * @param data
	 *            The data to set.
	 */
	public void setData(List<PopulatorItem> data) {
		this.data = data;
	}

}
