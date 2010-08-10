package com.gogwt.app.booking.resources.i18n.misc;

/**
 * Interface to represent the constants contained in resource bundle:
 * 	'D:/ebook/gwtbooking/src/main/resources/com/gogwt/app/booking/resources/i18n/misc/MiscResources.properties'.
 */
public interface MiscResources extends com.google.gwt.i18n.client.ConstantsWithLookup {
  
  /**
   * Translated "US English".
   * 
   * @return translated "US English"
   */
  @DefaultStringValue("US English")
  @Key("language.selector.en-US")
  String language_selector_en_US();

  /**
   * Translated "Español".
   * 
   * @return translated "Español"
   */
  @DefaultStringValue("Español")
  @Key("language.selector.es-ES")
  String language_selector_es_ES();

  /**
   * Translated "中文/中国".
   * 
   * @return translated "中文/中国"
   */
  @DefaultStringValue("中文/中国")
  @Key("language.selector.zh-CN")
  String language_selector_zh_CN();

  /**
   * Translated "en-US,zh-CN,es-ES".
   * 
   * @return translated "en-US,zh-CN,es-ES"
   */
  @DefaultStringValue("en-US,zh-CN,es-ES")
  @Key("language.support.list")
  String language_support_list();
}
