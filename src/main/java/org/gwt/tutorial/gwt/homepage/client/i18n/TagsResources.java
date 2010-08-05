package org.gwt.tutorial.gwt.homepage.client.i18n;

import org.hibernate.tutorial.resources.i18n.LabelResources;
import org.hibernate.tutorial.resources.i18n.ViewResources;

import com.google.gwt.core.client.GWT;
 
public interface TagsResources extends LabelResources, ViewResources {
	
	 final static TagsResources instance = GWT.create(TagsResources.class);
	  
	  public class Util {
	    /**
	     * <p>
	     * Convenience method to instantiate this resource bundle
	     * </p>
	     * @return
	     */
	    public static TagsResources getInstance() {
	      return instance;
	    }
	  }
}
