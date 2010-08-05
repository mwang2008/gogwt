package org.hibernate.tutorial.resources.i18n;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	D:/Trial/gwt_spring/src/main/resources/org/hibernate/tutorial/resources/i18n/ViewResources.properties'.
 */
public interface ViewResources extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Change".
   * 
   * @return translated "Change"
   */
  @DefaultMessage("Change")
  @Key("change")
  String change();

  /**
   * Translated "Edit".
   * 
   * @return translated "Edit"
   */
  @DefaultMessage("Edit")
  @Key("edit")
  String edit();

  /**
   * Translated "Start this reservation over".
   * 
   * @return translated "Start this reservation over"
   */
  @DefaultMessage("Start this reservation over")
  @Key("progress.bar.startover.link")
  String progress_bar_startover_link();

  /**
   * Translated "Guest Information and Confirmation ".
   * 
   * @return translated "Guest Information and Confirmation "
   */
  @DefaultMessage("Guest Information and Confirmation ")
  @Key("progress.bar.your.guestinfo")
  String progress_bar_your_guestinfo();

  /**
   * Translated "Hotel".
   * 
   * @return translated "Hotel"
   */
  @DefaultMessage("Hotel")
  @Key("progress.bar.your.hotel")
  String progress_bar_your_hotel();

  /**
   * Translated "Room and Rate".
   * 
   * @return translated "Room and Rate"
   */
  @DefaultMessage("Room and Rate")
  @Key("progress.bar.your.room")
  String progress_bar_your_room();

  /**
   * Translated "Search ".
   * 
   * @return translated "Search "
   */
  @DefaultMessage("Search ")
  @Key("progress.bar.your.search")
  String progress_bar_your_search();

  /**
   * Translated "to".
   * 
   * @return translated "to"
   */
  @DefaultMessage("to")
  @Key("to")
  String to();
}
