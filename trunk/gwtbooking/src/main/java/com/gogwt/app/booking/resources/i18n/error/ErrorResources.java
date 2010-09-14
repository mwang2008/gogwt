package com.gogwt.app.booking.resources.i18n.error;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	D:/gogwt/gwtbooking/src/main/resources/com/gogwt/app/booking/resources/i18n/error/ErrorResources.properties'.
 */
public interface ErrorResources extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Please type in location field.".
   * 
   * @return translated "Please type in location field."
   */
  @DefaultMessage("Please type in location field.")
  @Key("error.empty.location")
  String error_empty_location();

  /**
   * Translated "Session Timedout".
   * 
   * @return translated "Session Timedout"
   */
  @DefaultMessage("Session Timedout")
  @Key("error.session.timedout")
  String error_session_timedout();

  /**
   * Translated "Please enter your {0}.".
   * 
   * @return translated "Please enter your {0}."
   */
  @DefaultMessage("Please enter your {0}.")
  @Key("input.field.required")
  String input_field_required(String arg0);
}
