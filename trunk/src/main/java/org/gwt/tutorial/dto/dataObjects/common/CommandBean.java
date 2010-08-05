package org.gwt.tutorial.dto.dataObjects.common;

import java.util.HashMap;
import java.util.Map;

public class CommandBean {
	private Map mArgs;

	/**
	 * 
	 * <p>
	 * Create a new instance of Command.
	 * </p>
	 */
	public CommandBean() {
		mArgs = new HashMap();
	}

	public CommandBean(final String key, final Object value) {
		mArgs = new HashMap();
		addArg(key, value);
	}

	/**
	 * <p>
	 * See {@link #setmArgs(Map)}
	 * </p>
	 * 
	 * @return Returns the args.
	 */
	public Object getArg(final String key) {
		return mArgs.get(key);
	}

	/**
	 * <p>
	 * Set the value of <code>args</code>.
	 * </p>
	 * 
	 * @param args
	 *            The args to set.
	 */
	public Object addArg(final String key, final Object value) {
		return mArgs.put(key, value);
	}

}
