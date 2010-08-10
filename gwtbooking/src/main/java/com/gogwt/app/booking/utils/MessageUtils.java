package com.gogwt.app.booking.utils;

import java.util.Locale;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;



public abstract class MessageUtils
{
  /**
   * <p>
   * Try to resolve the message. If could not find, just return key back.
   * </p>
   * @param code
   *          the message key
   * @param args
   *          the message arguments
   * @param locale
   *          the resource bunlde locale
   * @return message from resouce bundle
   */
  public static String getMessage( final String code, final Object[] args,
    final Locale locale )
  {

    String message = null;
    try {
      message = BeanLookupService.getAppContext().getMessage( code, args, locale );
    } catch( NoSuchMessageException e ) {
      message = code;
    }

    return message;
  }

  /**
   * <p>
   * Try to resolve the message. If could not find, just return key back.
   * </p>
   * @param code
   *          the message key
   * @param args
   *          the message arguments
   * @return message from resouce bundle
   */
  public static String getMessage( final String code, final Object[] args )
  {
    return getMessage( code, args, LocaleContextHolder.getLocale() );
  }

  /**
   * <p>
   * Try to resolve the message. If could not find, just return key back.
   * </p>
   * @param code
   *          the message key
   * @return message from resouce bundle
   */
  public static String getMessage( final String code )
  {

    return getMessage( code, null );
  }

}

