package org.hibernate.tutorial.resources.i18n;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	D:/Trial/gwt_spring/src/main/resources/org/hibernate/tutorial/resources/i18n/LabelResources.properties'.
 */
public interface LabelResources extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "First Name".
   * 
   * @return translated "First Name"
   */
  @DefaultMessage("First Name")
  @Key("label.First.Name")
  String label_First_Name();

  /**
   * Translated "First Name:".
   * 
   * @return translated "First Name:"
   */
  @DefaultMessage("First Name:")
  @Key("label.First.Name.colon")
  String label_First_Name_colon();

  /**
   * Translated "Last Name".
   * 
   * @return translated "Last Name"
   */
  @DefaultMessage("Last Name")
  @Key("label.Last.Name")
  String label_Last_Name();

  /**
   * Translated "Last Name:".
   * 
   * @return translated "Last Name:"
   */
  @DefaultMessage("Last Name:")
  @Key("label.Last.Name.colon")
  String label_Last_Name_colon();

  /**
   * Translated "Welcome Spring, GWT, Hibernate".
   * 
   * @return translated "Welcome Spring, GWT, Hibernate"
   */
  @DefaultMessage("Welcome Spring, GWT, Hibernate")
  @Key("label.welcome")
  String label_welcome();
}
