package com.gogwt.app.booking.utils;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

public class ToStringUtils {
	/**
     * Provides standard toString method for domain objects.
     *
     * @param object - Object to evaluate.
     *
     * @return String representation of object.
     */
   public static String toString( final Object object ) {
        StandardToStringStyle toStringStyle = new StandardToStringStyle();
        toStringStyle.setUseShortClassName( true );

        return ReflectionToStringBuilder.toString( object, toStringStyle );
    }
}
