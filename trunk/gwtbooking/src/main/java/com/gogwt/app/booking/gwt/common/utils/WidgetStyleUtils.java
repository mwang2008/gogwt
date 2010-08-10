package com.gogwt.app.booking.gwt.common.utils;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * <code><B>WidgetStyleUtils<code><B>
 * <p>
 * Common code used to create/organize widgets
 * </p>
 */

public class WidgetStyleUtils {

	/**
	 * Create flow panel with style ID
	 * @param id
	 * @return
	 */
	public static Panel createFlowPanelWithId(String id) {
		FlowPanel fp = new FlowPanel();
		fp.getElement().setId(id);
		return fp;
	}
	
	 /**
	   * Add only one style to the widget.
	   * @param widget
	   * @param style
	   */
	  public static void setStyleToWidget( Widget widget, String style )
	  {
	    widget.setStyleName( style );
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
	    a.setHref( "javascript:" );
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
	         //do nothing
	      }
	    }
	  }
	  
	  /**
	   * <p>
	   * </p>
	   * @param linkName
	   * @param linkUrl
	   * @return @
	   */
	  public static HTML createHtmlLink( final String linkName, final String linkUrl )
	  {
	    final HTML html = new HTML();
	    String formattedlinkName = linkName.replaceAll( "[@]", "&#64;" );
	    // String formattedlinkName = URL.encode(linkName);
	    html.setHTML( "<a href=\"" + linkUrl + "\" title=\"" + formattedlinkName + "\">" + formattedlinkName + "</a>" );
	    return html;
	  }
	  
	  /**
	   * <p>
	   * create FlexTable
	   * </p>
	   * @return
	   */
	  public static FlexTable createFlexTable()
	  {
	    return createFlexTable( "" );
	  }
	  
	  public static FlexTable createFlexTable( final String style )
	  {
	    final FlexTable tbl = new FlexTable();
	    tbl.setStyleName( style );
	    return tbl;
	  }
	  
	  public static FlowPanel createFlowPanel()
	  {
	    final FlowPanel fp = new FlowPanel();
	    return fp;
	  }
	  
	  
	  public static Panel createFlowPanelWithStyles( String ... styles )
	  {
	    FlowPanel fp = new FlowPanel();
	    addStylesToWidget( fp, styles );
	    return fp;
	  }
	  
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
	  
	  public static Label createLabel( final String text, String ... styles )
	  {
	    Label label = new Label( text );
	    addStylesToWidget( label, styles );
	    return label;
	  }

	  public static Label createLabel( final String text )
	  {
	    return createStyledLabel( text, "" );
	  }
	  
	  public static Label createStyledLabel( String text, String ... styles )
	  {
	    return createStyledLabel( text, true, styles );
	  }

	  public static Label createStyledLabel( String text, boolean wordWrap, String ... styles )
	  {
	    Label label = new Label( text );
	    label.setWordWrap(wordWrap);
	    addStylesToWidget( label, styles );
	    return label;
	  }
	  
	  public static HorizontalPanel createHorizontalPanel( String ... styles )
	  {
	    final HorizontalPanel hp = new HorizontalPanel();
	    addStylesToWidget( hp, styles );
	    return hp;
	  }

	  public static HorizontalPanel createHorizontalPanel( Widget... widgets )
	  {
	    final HorizontalPanel hp = new HorizontalPanel();
	    for (Widget widget : widgets) {
	    	hp.add(widget);
	    }
	    return hp;
	  }
	  
	  public static HorizontalPanel createHorizontalPanel()
	  {
	    final HorizontalPanel hp = new HorizontalPanel();
	    return hp;
	  }
	  
	  
	  /**
	   * <p>
	   * Provide a panel with all widget laid out horizontally
	   * </p>
	   * @param variableWidget
	   * @return
	   */
	  public static Panel getHorizontalPanelWithWidgets( String style,
	    Widget ... widgets )
	  {
	    HorizontalPanel hp = new HorizontalPanel();
	    addWidgetsToPanel( hp, widgets );
	    addStylesToWidget( hp, style );
	    return hp;
	  }
	  
	  /**
	   * <p>
	   * Provide a <code>FlowPanel</code>
	   * </p>
	   * @param id
	   * @param styles
	   * @return
	   */
	  public static Panel createFlowPanelWithIdStyles( String id, String ... styles )
	  {
	    FlowPanel fp = new FlowPanel();
	    fp.getElement().setId( id );
	    addStylesToWidget( fp, styles );
	    return fp;
	  }

	 
	  
	  /****************************************************************
	   * PRIVATE 
	   */
	  /**
	   *  
	   */
	  private static void addWidgetsToPanel( Panel p, Widget ... widgets )
	  {
	    for( int i = 0; i < widgets.length; i++ ) {
	      p.add( widgets[i] );
	    }
	  }
}
