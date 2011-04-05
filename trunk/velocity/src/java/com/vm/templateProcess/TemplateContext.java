package com.vm.templateProcess;

import java.text.DateFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Date;
import org.apache.log4j.Logger;
 
public class TemplateContext
{
  private static Logger sLogger = Logger.getLogger( TemplateContext.class.getName() );

  private Locale mLocale;
  private String mDefaultResourceBundlePath;

  /**
   * Constructor
   */
  public TemplateContext( Locale locale )
  {
    mLocale = locale;
  }

  /**
   * Format currency
   * 
   * @param amount
   * @param currencyCode
   * @return
   */
  public String formatCurrencyWithDefaultLocale( final double amount )
  {
    Format formatter = NumberFormat.getCurrencyInstance( mLocale );
    return formatter.format( new Double( amount ) );
  }

  /**
   * Format double
   * 
   * @param number
   * @return
   */
  public String doubleFormatter( final double number )
  {
    Format formatter = NumberFormat.getNumberInstance( mLocale );
    return formatter.format( new Double( number ) );
  }

  /**
   * Format date
   * @param date
   * @return
   */
  public String formatDate( final Date date)
  {
    if ( date == null ) {
      return "";
    }
    DateFormat dateFormat = 
      DateFormat.getDateInstance(DateFormat.LONG, mLocale);
    return dateFormat.format( date );
  }
  
  public double multiple(double num1, double num2) {
    return num1*num2;
  }
  
  /**
   * Get resource bundle
   * 
   * @param key
   * @return
   */
  public String getResourceBundleContentWithKey( final String key )
  {
    return getResourceBundleContentPathKey( mDefaultResourceBundlePath, key );
  }

  public String getResourceBundleContentPathKey( final String pathName,
    final String key )
  {
    return getResourceBundleContent( pathName, mLocale, key );
  }

  public String getResourceBundleContent( final String pathName,
    final Locale locale, final String key )
  {
    try {
      ResourceBundle resourceBundle = ResourceBundle.getBundle( pathName, locale );
      return resourceBundle.getString( key );

    } catch( Throwable e ) {
      sLogger.debug( "could not find resource bundle content for: pathName="
        + pathName + ", locale=" + locale + ", key=" + key );
      sLogger.debug( e.getMessage() );

      return key;
    }
  }

  public String getResourceBundleContentArg1( final String key, final Object arg1 )
  {

    Object args[] = {
      arg1
    };
    return getContent( mDefaultResourceBundlePath, mLocale, key, args );

  }

  /**
   * Get resource bundle content with path, key and argument.
   * </p>
   * 
   * @param pathName
   *          reource bundle path name
   * @param key
   *          reource bundle key
   * @param arg1
   *          first argument
   * @return String - Content return from resource bundle.
   */
  public String getResourceBundleContent1Arg1( final String pathName,
    final String key, final Object arg1 )
  {

    Object args[] = {
      arg1
    };
    return getContent( pathName, mLocale, key, args );
  }


  /**
   * Get resource bundle content with path, key and argument.
   * </p>
   * 
   * @param pathName
   *          reource bundle path name
   * @param locale
   *          locale
   * @param key
   *          reource bundle key
   * @param arg1
   *          first argument
   * @return String - Content return from resource bundle.
   */
  public String getResourceBundleContent2Arg1( final String pathName,
    final Locale locale, final String key, final Object arg1 )
  {
    Object args[] = {
      arg1
    };
    return getContent( pathName, locale, key, args );
  }

  /**
   * Get resource bundle content with key and argumentss.
   * </p>
   * 
   * @param key
   *          reource bundle key
   * @param arg1
   *          first argument
   * @param arg2
   *          second argument
   * @return String - Content return from resource bundle.
   */
  public String getResourceBundleContentArg2( final String key, final Object arg1,
    final Object arg2 )
  {

    Object args[] = {
      arg1, arg2
    };
    return getContent( mDefaultResourceBundlePath, mLocale, key, args );

  }

  public String getResourceBundleContent1Arg2( final String pathName,
    final String key, final Object arg1, final Object arg2 )
  {

    Object args[] = {
      arg1, arg2
    };
    return getContent( pathName, mLocale, key, args );

  }

  public String getResourceBundleContent2Arg2( final String pathName,
    final Locale locale, final String key, final Object arg1, final Object arg2 )
  {

    Object args[] = {
      arg1, arg2
    };
    return getContent( pathName, locale, key, args );

  }

  public String getResourceBundleContentArg3( final String key, final Object arg1,
    final Object arg2, final Object arg3 )
  {
    Object args[] = {
      arg1, arg2, arg3
    };
    return getContent( mDefaultResourceBundlePath, mLocale, key, args );

  }


  public String getResourceBundleContent1Arg3( final String pathName,
    final String key, final Object arg1, final Object arg2, final Object arg3 )
  {

    Object args[] = {
      arg1, arg2, arg3
    };
    return getContent( pathName, mLocale, key, args );

  }


  public String getResourceBundleContent2Arg3( final String pathName,
    final Locale locale, final String key, final Object arg1, final Object arg2,
    final Object arg3 )
  {

    Object args[] = {
      arg1, arg2, arg3
    };
    return getContent( pathName, locale, key, args );

  }

  public String getContent( final String pathName, final Locale locale,
    final String key, final Object[] args )
  {
    try {

      ResourceBundle resourceBundle = ResourceBundle.getBundle( pathName, locale );
      MessageFormat formatter = new MessageFormat( resourceBundle.getString( key ) );
      return formatter.format( args );

    } catch( Throwable e ) {
      sLogger.debug( "could not find resource bundle content for: pathName="
        + pathName + ", locale=" + locale + ", key=" + key );
      sLogger.debug( e.getMessage() );
      return key;
    }
  }


  /**
   * @return Returns the value of {@link #mDefaultResourceBundlePath}.
   */
  public String getDefaultResourceBundlePath()
  {
    return mDefaultResourceBundlePath;
  }

  /**
   * @param defaultResourceBundlePath
   *          Set the value of {@link #mDefaultResourceBundlePath}.
   */
  public void setDefaultResourceBundlePath( String defaultResourceBundlePath )
  {
    mDefaultResourceBundlePath = defaultResourceBundlePath;
  }

  /**
   * @return Returns the value of {@link #mLocale}.
   */
  public Locale getLocale()
  {
    return mLocale;
  }

  /**
   * @param locale
   *          Set the value of {@link #mLocale}.
   */
  public void setLocale( Locale locale )
  {
    mLocale = locale;
  }


}
