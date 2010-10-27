package com.gogwt.framework.arch.utils;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.UIObject;

public class WidgetStyleUtils {
	 /**
	   * <p>
	   * Use this method when an Anchor widget is needed that makes use of the href
	   * attribute to redirect the user to some external url
	   * </p>
	   * @param name
	   * @param href
	   * @param styles
	   * @return
	   */
	  public static Anchor createExternalLink( String name, String href,
	    String ... styles )
	  {
	    Anchor anch = new Anchor( name, href );
	    anch.setTitle( name );
	    addStylesToWidget(anch, styles);
	    return anch;
	  }
	  
	  /**
	   * <p>
	   * Use this method when an Anchor widget is needed that executes javascript
	   * </p>
	   * @param name
	   * @param styles
	   * @return
	   */
	  public static Anchor createAnchor( String name, String ... styles )
	  {
	    Anchor a = new Anchor( name );
	    a.setTitle( name );
	    String url = Window.Location.getHref();
	     
	    //a.setHref( "javascript:" );
	    a.setHref( url);
	    addStylesToWidget( a, styles );	    
	    return a;
	  }
	  
	  /**
	   * <p>
	   * Add style to widget destination.addStyleName( "quickResRow" );
	   * </p>
	   * @param widget
	   * @param styles
	   */
	  public static void addStylesToWidget( UIObject widget, String ... styles )
	  {
		  if (styles == null || styles.length == 0) {
			  return;
		  }

	    for(String style : styles) {
	      try {
	    	  if (style != null && style.trim().length() > 0) {
	    	        widget.addStyleName( style );
	    	  }
	      } catch( Throwable e ) {
	        Log.info( "Error adding style to widget: ('" + style + "')" );
	      }
	    }
	  }
}
