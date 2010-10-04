package com.gogwt.app.booking.gwt.common.widget.populator;

import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;

public class ExtendedListBox extends ListBox implements HasValue<String> {
	private String label;

	/**
	 * <p>
	 * Create a new instance of ExtendedListBox. Adds a corresponding hidden
	 * <label> element for accessibility
	 * </p>
	 * 
	 * @param label
	 */
	public ExtendedListBox(String label) {
		this(label, null);
	}

	public ExtendedListBox(String label, Map<String, String> labelValueMap) {
		this.label = label;

		// Pre-populate the list
		if (labelValueMap != null && !labelValueMap.isEmpty()) {
			for (Entry<String, String> entry : labelValueMap.entrySet()) {
				addItem(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * <p>
	 * Get the selected value
	 * </p>
	 * 
	 * @return
	 */
	public final String getSelectedValue() {
		if (this.getItemCount() > 0) {
			return getValue(getSelectedIndex());
		}
		return null;
	}

	/**
	 * <p>
	 * Select list entry by value
	 * </p>
	 * 
	 * @param value
	 */
	public final void setSelectedValue(String value) {
		internalSetSelectedValue(value);

	}

	private final void internalSetSelectedValue(String value) {
		int index = 0;
		for (int i = 0; i < getItemCount(); i++) {
			if (getValue(i).equals(value)) {
				index = i;
				break;
			}
		}
		setItemSelected(index, true);
	}

	public String getValue() {
		return this.getSelectedValue();
	}

	public void setValue(String value) {
		this.setSelectedValue(value);

	}

	public void setValue(String arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
