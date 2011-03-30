package com.gogwt.app.booking.resources.i18n.error;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	D:/gogwt/gwtbooking/src/main/resources/com/gogwt/app/booking/resources/i18n/error/ErrorResources.properties'.
 */
public interface ErrorResources extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "The confirm email dose not match email address.".
   * 
   * @return translated "The confirm email dose not match email address."
   */
  @DefaultMessage("The confirm email dose not match email address.")
  @Key("error.email.notmatch")
  String error_email_notmatch();

  /**
   * Translated "Please type in location field.".
   * 
   * @return translated "Please type in location field."
   */
  @DefaultMessage("Please type in location field.")
  @Key("error.empty.location")
  String error_empty_location();

  /**
   * Translated "System has problem currently, please try again.".
   * 
   * @return translated "System has problem currently, please try again."
   */
  @DefaultMessage("System has problem currently, please try again.")
  @Key("error.generic.message")
  String error_generic_message();

  /**
   * Translated "System has error at this moment, please try again.".
   * 
   * @return translated "System has error at this moment, please try again."
   */
  @DefaultMessage("System has error at this moment, please try again.")
  @Key("error.generic.msg")
  String error_generic_msg();

  /**
   * Translated "Please specify full birthday.".
   * 
   * @return translated "Please specify full birthday."
   */
  @DefaultMessage("Please specify full birthday.")
  @Key("error.invalid.birthday")
  String error_invalid_birthday();

  /**
   * Translated "Invalid Email Address".
   * 
   * @return translated "Invalid Email Address"
   */
  @DefaultMessage("Invalid Email Address")
  @Key("error.invalid.email")
  String error_invalid_email();

  /**
   * Translated "Could not find Latitude and Longitude for the given location ".
   * 
   * @return translated "Could not find Latitude and Longitude for the given location "
   */
  @DefaultMessage("Could not find Latitude and Longitude for the given location ")
  @Key("error.invalid.geocode")
  String error_invalid_geocode();

  /**
   * Translated "The Password is not correct, please provide correct Password.".
   * 
   * @return translated "The Password is not correct, please provide correct Password."
   */
  @DefaultMessage("The Password is not correct, please provide correct Password.")
  @Key("error.invalid.password")
  String error_invalid_password();

  /**
   * Translated "The User Name is not correct, please provide correct User Name.".
   * 
   * @return translated "The User Name is not correct, please provide correct User Name."
   */
  @DefaultMessage("The User Name is not correct, please provide correct User Name.")
  @Key("error.invalid.username")
  String error_invalid_username();

  /**
   * Translated "No result has been found, please try different location (US only)".
   * 
   * @return translated "No result has been found, please try different location (US only)"
   */
  @DefaultMessage("No result has been found, please try different location (US only)")
  @Key("error.no.search.result")
  String error_no_search_result();

  /**
   * Translated "Please specify a more secure password.".
   * 
   * @return translated "Please specify a more secure password."
   */
  @DefaultMessage("Please specify a more secure password.")
  @Key("error.password.invalid.format")
  String error_password_invalid_format();

  /**
   * Translated "The confirm password does not match with password.".
   * 
   * @return translated "The confirm password does not match with password."
   */
  @DefaultMessage("The confirm password does not match with password.")
  @Key("error.password.notmatch")
  String error_password_notmatch();

  /**
   * Translated "Session Timedout".
   * 
   * @return translated "Session Timedout"
   */
  @DefaultMessage("Session Timedout")
  @Key("error.session.timedout")
  String error_session_timedout();

  /**
   * Translated "The User Name is already used by others, please try another one.".
   * 
   * @return translated "The User Name is already used by others, please try another one."
   */
  @DefaultMessage("The User Name is already used by others, please try another one.")
  @Key("error.username.duplicated")
  String error_username_duplicated();

  /**
   * Translated "Please specify User Name with correct format.".
   * 
   * @return translated "Please specify User Name with correct format."
   */
  @DefaultMessage("Please specify User Name with correct format.")
  @Key("error.username.invalid.format")
  String error_username_invalid_format();

  /**
   * Translated "Please enter your {0}.".
   * 
   * @return translated "Please enter your {0}."
   */
  @DefaultMessage("Please enter your {0}.")
  @Key("input.field.required")
  String input_field_required(String arg0);
}
