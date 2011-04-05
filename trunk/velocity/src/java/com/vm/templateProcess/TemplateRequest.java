package com.vm.templateProcess;

import java.util.HashMap;

public class TemplateRequest
{
  private HashMap mMap;
  private String mPath;


  /**
   * construct
   */
  public TemplateRequest()
  {
    mMap = new HashMap();
  }

  /**
   * Add template key/value pair <p/>
   * 
   * @param key
   *          the key for the map
   * @param value
   *          the value for the map
   */
  public void addToMap( String key, Object value )
  {
    mMap.put( key, value );
  }

  /**
   * get hasp map attrbute
   * 
   * @return HashMap - the internal state of hashMap
   */
  public HashMap getMap()
  {
    return mMap;
  }

  /**
   * @return String - the value of {@link #path}.
   */
  public String getPath()
  {
    return mPath;
  }

  /**
   * @param path
   *          Set the value of {@link #path}.
   */
  public void setPath( String path )
  {
    mPath = path;
  }

}
