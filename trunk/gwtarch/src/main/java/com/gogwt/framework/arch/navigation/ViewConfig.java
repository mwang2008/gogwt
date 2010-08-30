package com.gogwt.framework.arch.navigation;

import java.util.Map;

import com.gogwt.framework.arch.widgets.AbstractView;


/**
 * Bean object used to hold view contains
 * 
 * @author WangM
 * @deprecated
 */
public class ViewConfig {
	private String name;
	private boolean seoCrwalable;
	private AbstractView instance;
	private Map<String, Map<String, String>> properties;

	public ViewConfig() {
	}

	
	public ViewConfig(String name, AbstractView instance) {
		super();
		this.instance = instance;
		this.name = name;
	}

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
	 * Set the value of <code>name</code>.
	 * </p>
	 * 
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>
	 * See {@link #setinstance(AbstractView)}
	 * </p>
	 * 
	 * @return Returns the instance.
	 */
	public AbstractView getInstance() {
		return instance;
	}

	/**
	 * <p>
	 * Set the value of <code>instance</code>.
	 * </p>
	 * 
	 * @param instance
	 *            The instance to set.
	 */
	public void setInstance(AbstractView instance) {
		this.instance = instance;
	}

	/**
	 * <p>
	 * See {@link #setproperties(Map<String,Map<String,String>>)}
	 * </p>
	 * 
	 * @return Returns the properties.
	 */
	public Map<String, Map<String, String>> getProperties() {
		return properties;
	}

	/**
	 * <p>
	 * Set the value of <code>properties</code>.
	 * </p>
	 * 
	 * @param properties
	 *            The properties to set.
	 */
	public void setProperties(Map<String, Map<String, String>> properties) {
		this.properties = properties;
	}

	public boolean isSeoCrwalable() {
		return seoCrwalable;
	}

	public void setSeoCrwalable(boolean seoCrwalable) {
		this.seoCrwalable = seoCrwalable;
	}

	
}

