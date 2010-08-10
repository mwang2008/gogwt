package com.gogwt.app.booking.dto.dataObjects.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ReverseEnumMap<K, V extends ReversibleEnum<K, V>>  implements Serializable
{
  /**
   * serialVersionUID - long
   */
  private static final long serialVersionUID = 1L;
  private final Map<K, V> mReverseMap = new HashMap<K, V>();

  /**
   * <p>
   * Create a new instance of ReverseEnumMap.
   * </p>
   * @param valueType
   */
  public ReverseEnumMap( final Class<V> valueType )
  {
    for( final V v : valueType.getEnumConstants() ) {
      mReverseMap.put( v.getCode(), v );
    }
  }

  /**
   * <p>
   * Perform the reverse lookup for the given enum value and return the enum
   * constant.
   * </p>
   * @param enumValue
   * @return enum constant
   */
  public V get( final K enumValue )
  {
    return mReverseMap.get( enumValue );
  }
}

