package com.gogwt.app.booking.config.interceptor.bean;

import com.gogwt.app.booking.utils.ToStringUtils;

/**
 * <code><B>PopulatorEntry<code><B>
 * <p/>
 * The bean object for gwt config populator properties
 * <p/>
 */
public class PopulatorEntry {
	private String key;
	private String entry;

	/**
	 * <p>
	 * Create a new instance of PopulatorEntry.
	 * </p>
	 * 
	 * @param entry
	 * @param key
	 */
	public PopulatorEntry(String key, String entry) {
		super();
		this.entry = entry;
		this.key = key;
	}

	/**
	 * <p>
	 * See {@link #setkey(String)}
	 * </p>
	 * 
	 * @return Returns the key.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * <p>
	 * Set the value of <code>key</code>.
	 * </p>
	 * 
	 * @param key
	 *            The key to set.
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * <p>
	 * See {@link #setentry(String)}
	 * </p>
	 * 
	 * @return Returns the entry.
	 */
	public String getEntry() {
		return entry;
	}

	/**
	 * <p>
	 * Set the value of <code>entry</code>.
	 * </p>
	 * 
	 * @param entry
	 *            The entry to set.
	 */
	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String toString() {
		return ToStringUtils.toString(this);
	}

}
