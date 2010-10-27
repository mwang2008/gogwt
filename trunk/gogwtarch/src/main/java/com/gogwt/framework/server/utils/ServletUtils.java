/*
 * Copyright 2010 GoGWT.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gogwt.framework.server.utils;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.gogwt.framework.arch.utils.StringUtils;

public abstract class ServletUtils
{
  private static Logger sLogger = Logger.getLogger(ServletUtils.class);
  
  private static final String QUOTE = "'";
  private static final String SPACE_EQUAL_SPACE = " = ";
  private static final char SEMI_COLON = ';';
  private static final char QUESTION_MARK = '?';
  private static final char AMPERSAND = '&';
  private static final char EQUALS = '=';
  private static final String HTTPS = "https";
  private static final String HTTP = "http";
  private static final String DEFAULT_ENCODE = "UTF-8";


  /**
   * Avoid to create instance
   */
  private ServletUtils()
  {
  }

  /**
   * @param pURL
   * @param pName
   * @param pValue
   * @return URL with given parameter (name & value) inserted as first parameter
   *         in query string.
   */
  static public String insertURLParam( final String pURL, final String pName,
    final String pValue )
  {
    int iStart = pURL.indexOf( QUESTION_MARK );
    final StringBuffer sbOut = new StringBuffer( pURL );
    String sParam = pName + EQUALS + pValue;
    if ( iStart < 0 ) {
      iStart = pURL.length();
      sParam = QUESTION_MARK + sParam;
    } else {
      iStart++;
      sParam = sParam + AMPERSAND;
    }
    sbOut.insert( iStart, sParam );
    return sbOut.toString();
  }


  /**
   * Add a parameter to a URL - encoding both the name and value of the
   * parameter.
   * @param pURL
   *          the URL to add the parameter to - if null, it will return a
   *          ?param=value as the result.
   * @param pName
   *          the name of the parameter to add - if null, it will not do
   *          anything.
   * @param pValue
   *          the value of the parameter to add - if null, it will be added as
   *          an empty value (not the word "null")
   * @return the URL with the encoded parameter added.
   */
  public static String addURLParam( final String pURL, final String pName,
    final String pValue )
  {
    String myURL = pURL;

    if ( pName != null ) {
      if ( myURL == null ) {
        myURL = "";
      }
      String paramValue = pValue;
      if ( paramValue == null ) {
        paramValue = "";
      }
      final StringBuffer newURL = new StringBuffer( myURL.length() + pName.length()
        + paramValue.length() + 1/* 1=length of param seperator */);
      newURL.append( myURL );

      final int posQuestionMark = myURL.indexOf( QUESTION_MARK );
      // Test to see if there is a question mark to start adding parameters
      // after.
      if ( posQuestionMark < 0 ) {
        newURL.append( QUESTION_MARK );
      }
      // Do not add an ampersand if the only value after the URL is a question
      // mark.
      else if ( myURL.length() - posQuestionMark > 1 ) {
        newURL.append( AMPERSAND );
      }

      try {
        newURL.append( java.net.URLEncoder.encode( pName, DEFAULT_ENCODE ) );
        newURL.append( EQUALS );
        newURL.append( java.net.URLEncoder.encode( paramValue, DEFAULT_ENCODE ) );
      } catch( UnsupportedEncodingException e ) {
        sLogger.info( "Encoding error while trying to encode this name (" + pName
          + ") or this value: " + paramValue );
      }

      myURL = newURL.toString();
    }
    return myURL;
  }

  /**
   * <p>
   * encode url
   * </p>
   * @param url
   * @param encodeTyep
   * @return 
   */
  public static String encode( String url, String encodeTyep )
  {
    try {
      return java.net.URLEncoder.encode( url, encodeTyep );
    } catch( Throwable e ) {
      return url;
    }
  }

  /**
   * <p>
   * encode url
   * </p>
   * @param url
   * @return
   */
  public static String encode( String url )
  {
    return encode( url, DEFAULT_ENCODE );
  }

  /**
   * 
   * <p>
   * Remove name/value pair based on name
   * </p>
   * @param pQueryString
   * @param pParameter
   * @return
   */
  public static String removeParameter( final String pQueryString,
    final String pParameter )
  {
    if ( StringUtils.isSet( pQueryString ) && StringUtils.isSet( pParameter ) ) {

      final int startPos = pQueryString.indexOf( pParameter );

      if ( startPos != -1 ) {
        StringBuffer newQuery = new StringBuffer( pQueryString );
        int endPos = pQueryString.indexOf( AMPERSAND, startPos );

        // if there are no other parameters, just goto the end of line
        if ( endPos == -1 ) {
          endPos = pQueryString.length();
        } else {
          // don't pickup the '&'
          endPos++;
        }

        try {
          // remove the parameter
          newQuery = newQuery.delete( startPos, endPos );
          return newQuery.toString();
        }
        // for whatever reason, catch this exception so we don't kill the
        // request
        catch( StringIndexOutOfBoundsException e ) {
          // just return the original string
          return pQueryString;
        }

      }
    }
    // if no work needs to be done, simply return the original string
    return pQueryString;

  }

  // ------------------------------------

  /**
   * This method puts all the request headers in a string
   * @param pRequest -
   *          the request.
   */
  public static String debugRequestHeaders( final HttpServletRequest pRequest )
  {
    final Enumeration reqHeaders = pRequest.getHeaderNames();
    final StringBuffer strbuff = new StringBuffer( 120 );
    String headerName;
    String headerVal;
    strbuff.append( "\n---------------BEGIN HEADERS---------------\n" );
    while ( reqHeaders.hasMoreElements() ) {
      headerName = (String)reqHeaders.nextElement();
      headerVal = pRequest.getHeader( headerName );

      strbuff.append( headerName );
      strbuff.append( SPACE_EQUAL_SPACE );

      if ( headerVal == null ) {
        strbuff.append( "null\n" );
      } else {
        strbuff.append( QUOTE );
        strbuff.append( headerVal );
        strbuff.append( "'\n" );
      }

    }
    strbuff.append( "---------------BEGIN HEADERS---------------\n" );

    return strbuff.toString();
  }

  /**
   * <p>
   * Request data in string
   * </p>
   * @param pRequest
   * @return
   */
  public static String getRequestData( final HttpServletRequest request )
  {
    StringBuffer sBuf = new StringBuffer( 120 );

    if ( request.getParameterMap() == null ) {
      return null;
    }

    final Set keySet = request.getParameterMap().keySet();
    final Iterator iterator = keySet.iterator();
    String name = null;
    String[] values = null;
    while ( iterator.hasNext() ) {
      name = (String)iterator.next();
      values = (String[])request.getParameterMap().get( name );
      if ( StringUtils.isSet( values ) ) {
        for( int i = 0; i < values.length; i++ ) {
          addNameValuePair( sBuf, name, values[i] );
        }
      } else {
        addNameValuePair( sBuf, name, null );
      }
    }

    String requestData = sBuf.toString();

    // remove "&" if it is in the last position.
    if ( requestData.endsWith( "&" ) ) {
      requestData = requestData.substring( 0, requestData.length() - 1 );
    }
    return requestData;
  }

  /**
   * <p>
   * Whether the url is absolute url
   * </p>
   * @param url
   * @return, true is absolute URL.
   */
  public static boolean absoluteURL( String url )
  {
    return StringUtils.containsString( url, HTTPS ) || StringUtils.containsString( url, HTTP );
  }


  /**
   * This method puts all the request parameters in a string
   * @param pRequest -
   *          the request.
   */
  public static String debugRequestParameters( final HttpServletRequest pRequest )
  {
    final Enumeration reqParams = pRequest.getParameterNames();
    final StringBuffer strbuff = new StringBuffer( 120 );
    String paramName;
    String paramVal;
    strbuff.append( "\n---------------BEGIN PARAMETERS---------------\n" );
    while ( reqParams.hasMoreElements() ) {
      paramName = (String)reqParams.nextElement();
      paramVal = pRequest.getParameter( paramName );

      strbuff.append( paramName );
      strbuff.append( SPACE_EQUAL_SPACE );

      if ( paramVal == null ) {
        strbuff.append( "null\n" );
      } else {
        strbuff.append( QUOTE );
        strbuff.append( paramVal );
        strbuff.append( "'\n" );
      }

    }
    strbuff.append( "---------------BEGIN PARAMETERS---------------\n" );

    return strbuff.toString();
  }

  // ------------------------------------

  /**
   * Removes the sessionId from a URL
   * @param pURI -
   *          the URL.
   * @return the URI
   */
  public static String removeSessionId( final String pURI )
  {
    int endPos;

    endPos = pURI.indexOf( SEMI_COLON );

    if ( endPos == -1 ) {
      return pURI;
    } else {
      return pURI.substring( 0, endPos );
    }
  }

  /**
   * Get the Query String from a URL
   * @param pURL -
   *          the URL.
   * @return the query string
   */
  public static String getQueryString( final String pURL )
  {
    int startPos;

    startPos = pURL.indexOf( QUESTION_MARK );

    if ( startPos == -1 ) {
      return null;
    } else {
      return pURL.substring( startPos + 1, pURL.length() );
    }
  }

  /**
   * Returns true if the IP address is a valid syntax.
   * @param pIPAddress -
   *          the IP Address to validate.
   * @return true if the IP address is a valid syntax.
   */
  public static boolean isValidIPAddress( final String pIPAddress )
  {

    final StringTokenizer tokenizer = new StringTokenizer( pIPAddress, "." );
    if ( tokenizer.countTokens() != 4 ) {
      return false;
    }

    while ( tokenizer.hasMoreTokens() ) {
      final String part = tokenizer.nextToken();
      try {
        final int partValue = Integer.parseInt( part );
        if ( partValue < 0 || partValue > 255 ) {
          return false;
        }
      } catch( NumberFormatException ex ) {
        return false;
      }
    }
    // all parts check out
    return true;
  }

  /**
   * <p>
   * Add name=value& to string buffer
   * </p>
   * @param sBuf
   * @param name
   * @param value
   */
  private static void addNameValuePair( StringBuffer sBuf, String name, String value )
  {

    sBuf.append( name );
    sBuf.append( EQUALS );
    if ( StringUtils.isSet( value ) ) {
      sBuf.append( value );
    }
    sBuf.append( AMPERSAND );
  }
}


