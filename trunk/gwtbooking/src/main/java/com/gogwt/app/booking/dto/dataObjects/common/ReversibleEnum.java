package com.gogwt.app.booking.dto.dataObjects.common;

/**
 * <code><B>ReversibleEnum<code><B>
 * <p/>
 * This interface defines the method that the {@link Enum} implementations
 * should implement if they want to have the reversible lookup functionality.
 * i.e. allow the lookup using the code for the {@link Enum} constants.
 * <p/>
 * @see com.ihg.dec.framework.commons.utils.enums.ReversibleEnum
 */

public interface ReversibleEnum<E, V>
{
  /**
   * <p>
   * Return the value/code of the enum constant.
   * </p>
   * @return value
   */
  E getCode();

  /**
   * <p>
   * Get the {@link Enum} constant by looking up the code in the reverse enum
   * map.
   * </p>
   * @param  E - code
   * @return V - The enum constant
   */
  V reverse( E code );
}

