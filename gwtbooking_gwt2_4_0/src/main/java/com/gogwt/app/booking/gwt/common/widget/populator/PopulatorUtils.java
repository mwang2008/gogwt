package com.gogwt.app.booking.gwt.common.widget.populator;


public class PopulatorUtils {
	/**
	   * <p>
	   * Create an auto-populating list box
	   * </p>
	   * @param name
	   * @param label
	   * @return
	   */
	  public static ExtendedListBox createPopulatorListBox( String name, String label )
	  {
	    return new ListBoxPopulator( name, label ).getWidget();
	  }
	  
	  public static ExtendedListBox createPopulatorListBox( String name, String label, String initValue, String initLable )
	  {
	    return new ListBoxPopulator( name, label, initValue, initLable ).getWidget();
	  }
}
