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
   * Translated "Could not find Latitude and Longitude for the given location ".
   * 
   * @return translated "Could not find Latitude and Longitude for the given location "
   */
  @DefaultMessage("Could not find Latitude and Longitude for the given location ")
  @Key("error.invalid.geocode")
  String error_invalid_geocode();

  /**
   * Translated "No result has been found, please try different criteria".
   * 
   * @return translated "No result has been found, please try different criteria"
   */
  @DefaultMessage("No result has been found, please try different criteria")
  @Key("error.no.search.result")
  String error_no_search_result();

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
