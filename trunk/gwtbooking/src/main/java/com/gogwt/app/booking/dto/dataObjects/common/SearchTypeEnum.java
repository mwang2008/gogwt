package com.gogwt.app.booking.dto.dataObjects.common;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum SearchTypeEnum implements ReversibleEnum<String, SearchTypeEnum>, IsSerializable {

	  CITY_SEARCH("city search"),
	  GEOCODE_SEARCH("mnemonic search"),
	  NAME_SEARCH("name search"),
	  MAP_SEARCH("map search");

	  private static final long serialVersionUID = 1L;
	  
	  private String mCode;
	  private static final ReverseEnumMap<String, SearchTypeEnum> M_REVERSE_MAP = 
		   new ReverseEnumMap<String, SearchTypeEnum>(
			  SearchTypeEnum.class );

	  /**
	   * <p>
	   * Create a new instance of SearchTypeEnum.
	   * </p>
	   * @param code
	   */
	  SearchTypeEnum( final String code )
	  {
	    mCode = code;
	  }

	  /**
	   * <p>
	   * Get the code associated with the enum.
	   * </p>
	   * @return Returns the code.
	   */
	  public final String getCode()
	  {
	    return mCode;
	  }


	  /**
	   * @param code
	   *          The integer code
	   * @return The enum constant
	   * @see com.ihg.dec.framework.commons.utils.enums.ReversibleEnum#reverse(java.lang.Object)
	   */
	  public SearchTypeEnum reverse( final String code )
	  {
	    return M_REVERSE_MAP.get( code );
	  }

	  /**
	   * <p>
	   * Get an instance of the this enumeration. <br/> Note: This method just
	   * returns the first enum constant. This should only be used when you need to
	   * reverse lookup an enum constant by a code.
	   * </p>
	   * @return SearchTypeEnum enum
	   */
	  public static SearchTypeEnum getInstance()
	  {
	    return values()[0];
	  }

}
