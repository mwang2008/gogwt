package com.vm.test;

import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.vm.email.EmailUtils;
import com.vm.reservationData.Car;
import com.vm.reservationData.Guest;
import com.vm.reservationData.Reservation;
import com.vm.reservationProcess.ReservationTemplateProcessor;

public class TestVelocity
{
  private static Logger sLogger = Logger.getLogger( TestVelocity.class.getName() );
  
  /** 
   * email host server
   * You got to change it to your email host.
   */
  private final static String EMAIL_HOST = "mail.allhotelmotel.com";  //"your.email.host, mail.allhotelmotel.com, mail.bradways.com";   
   
  /**
   * Process Velocity template and send email
   * @param locale
   * @throws Throwable
   */
  private void processVelocityAndSendEmail(Locale locale)
    throws Throwable
  {
    sLogger.info( "processVelocityAndSendEmail" );
    
    //prepare data for template
    Reservation reservation = constructCarReservatoin();
           
    if (sLogger.isDebugEnabled()) {
      sLogger.debug("\n");
      sLogger.debug( reservation.toString() );
    }

    
    //1. get email body 
    ReservationTemplateProcessor resTemplateProcessor = new ReservationTemplateProcessor();
    String body = resTemplateProcessor.processReservationTemplate( reservation, locale );

    //2. send email
    String subject = "Welcome to rent Ford's car";
    String fromAddress = "rent.car@ford.com";
    String toAddress = reservation.getGuest().getEmail();

    if (sLogger.isDebugEnabled()) {
      sLogger.debug("\n");
      sLogger.debug( "body=" + body );
    }

    EmailUtils emailUtils = new EmailUtils();
    emailUtils.setHostName( EMAIL_HOST );
    emailUtils.sendEmail( fromAddress, toAddress, subject, body );
  }

  /**
   * process
   * @param args
   * @throws Throwable
   */
  private void process(String[] args)
    throws Throwable
  {
       
    String arg1="en";
    if (args.length>0) {
      arg1 = args[0];
    }
    
    Locale locale = null;
    if ("zh".equalsIgnoreCase( arg1 )) {
       locale = new Locale("zh", "CN");
    }
    else {
      locale = new Locale("en", "US");
    }
    
    sLogger.info( "language="+arg1);
    
    // 1. test velocity
    processVelocityAndSendEmail(locale);
  }

  public static void main( String[] args )
  {
    try {
      String log4j = System.getProperty( "log4j.configuration", "log4j.properties" );
      sLogger.info( "log4j="+log4j);
      
      PropertyConfigurator.configure(log4j);
      
      sLogger.info("Entering application.");
      
      new TestVelocity().process(args);
      
    } catch( Throwable e ) {
      e.printStackTrace();
    }
  }

  /** 
   * Construct fake data for testing
   * @return
   */
  private Reservation constructCarReservatoin()
  {
    Reservation reservation = new Reservation();
    reservation.setConfirmationNumber( "124457788" );
    
    Guest guest = new Guest();
    guest.setFirstName( "Bob" );
    guest.setLastName( "Park" );
    guest.setEmail( "michael.wang@ichotelsgroup.com" );

    reservation.setGuest( guest );

    Car car = new Car();
    car.setId( Car.FORD );

    reservation.setCar( car );

    reservation.setCharge( 123.56 );
    reservation.setRentDays( 2 );

    Date today = new Date();
    reservation.setStartDate( today );
    
    return reservation;
  }
}
