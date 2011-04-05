package com.vm.reservationProcess;

import java.util.Locale;

import org.apache.log4j.Logger;

import com.vm.reservationData.Reservation;
import com.vm.templateProcess.TemplateConstant;
import com.vm.templateProcess.TemplateProcessor;
import com.vm.templateProcess.TemplateProcessorFactory;
import com.vm.templateProcess.TemplateRequest;
import com.vm.templateProcess.velocity.VelocityTemplateProcessor;

public class ReservationTemplateProcessor
{
  private static Logger sLogger = Logger.getLogger( VelocityTemplateProcessor.class.getName() );

  private static String DEFAULT_RESOURCE_BUNDLE_PATH = "com.vm.i18n.reservation.JSPResources";
  public static String CONFIRMATION_EMAIL_HTML_PATH =   "com/vm/template/reservation_html_confirmation.html";

  private static String RESERVATION = "reservation";
  private static String COMMENTS = "comments";
  
  public String processReservationTemplate( 
    final Reservation reservation, final Locale locale )
  {
    sLogger.debug("Start processReservationTemplate" );

    TemplateRequest templateRequest = new TemplateRequest();
    templateRequest.setPath( CONFIRMATION_EMAIL_HTML_PATH );
    templateRequest.addToMap( TemplateConstant.TMPL_LOCALE, locale );
    
    templateRequest.addToMap(
      TemplateConstant.TMPL_DEFAULT_RESOUCE_BUNDLE_PATH,
      DEFAULT_RESOURCE_BUNDLE_PATH );

    templateRequest.addToMap( RESERVATION, reservation );
    templateRequest.addToMap( COMMENTS, "Good Weekend" );

    TemplateProcessor templateProcess = 
      TemplateProcessorFactory.getInstance( TemplateProcessorFactory.VELOCITY );
    
    String body = templateProcess.process( templateRequest );

    return body;
  }
}
