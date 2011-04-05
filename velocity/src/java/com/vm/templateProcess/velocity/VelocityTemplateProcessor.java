package com.vm.templateProcess.velocity;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.vm.templateProcess.TemplateConstant;
import com.vm.templateProcess.TemplateContext;
import com.vm.templateProcess.TemplateProcessor;
import com.vm.templateProcess.TemplateProcessorException;
import com.vm.templateProcess.TemplateRequest;


public class VelocityTemplateProcessor implements TemplateProcessor
{
  private static Logger sLogger = Logger.getLogger( VelocityTemplateProcessor.class.getName() );

  private static final String VELOCITY_PROPERTIES = "velocity/velocity.properties";

  private VelocityEngine mVelocityEngine = null;

  /**
   * constructor
   * 
   */
  public VelocityTemplateProcessor()
  {
    InputStream iStream = null;

    iStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
      VELOCITY_PROPERTIES );

    Properties props = new Properties();
    if ( iStream == null ) {
      throw new TemplateProcessorException(
        "Could not find velocity configuration file: velocity/velocity.properties in class path" );
    }

    try {
      props.load( iStream );
      
      mVelocityEngine = new VelocityEngine();
      mVelocityEngine.init( props );

    } catch( Throwable e ) {
      throw new TemplateProcessorException( "could not initilize ", e );
    }

    sLogger.info( "Successfully loaded the velocity configuration file: "
      + "[velocity/velocity.properties] from classpath" );
  }

  public String process( TemplateRequest templateRequest )
    throws TemplateProcessorException
  {

    sLogger.debug( "Start template processing" );

    try {
      Template template = mVelocityEngine.getTemplate( templateRequest.getPath() );

      VelocityContext context = createContext( templateRequest );

      // 1. assign client value to velocity context
      Map vMap = templateRequest.getMap();
      String key;
      Object value;
      if ( vMap != null ) {
        Iterator it = vMap.entrySet().iterator();
        while ( it.hasNext() ) {
          Map.Entry entry = (Map.Entry)it.next();
          key = (String)entry.getKey();
          value = entry.getValue();
          context.put( key, value );
        }
      }

      // 2. bind with context and return String Writer
      StringWriter writer = new StringWriter();
      template.merge( context, writer );
      writer.close();

      return writer.getBuffer().toString();

    } catch( Throwable e ) {
      throw new TemplateProcessorException( "Could not process template "
        + " request is " + templateRequest.toString(), e );
    }
  }

  /**
   * Create context, common seting should be put after create context. <p/>
   * 
   * @param TemplateRequest
   *          domain object containing request specific data.
   * 
   * @return VelocityContext - Instance of VelocityContext
   */
  private VelocityContext createContext( TemplateRequest templateRequest )
    throws TemplateProcessorException
  {
    try {
      VelocityContext context = new VelocityContext();

      /*---------------------------------------------------
       * add common setting here ...
       *---------------------------------------------------*/

      Locale locale = (Locale)templateRequest.getMap().get(
        TemplateConstant.TMPL_LOCALE );

      TemplateContext templateContext = new TemplateContext(locale);

      String defaultResourceBundlePath = (String)templateRequest.getMap().get(
        TemplateConstant.TMPL_DEFAULT_RESOUCE_BUNDLE_PATH );
      templateContext.setDefaultResourceBundlePath( defaultResourceBundlePath );
      
      
      context.put( TemplateConstant.TMPL_CONTEXT_REFERENCE, templateContext );

      context.put( TemplateConstant.TMPL_LOCALE, locale );
      context.put(
        TemplateConstant.TMPL_DEFAULT_RESOUCE_BUNDLE_PATH, defaultResourceBundlePath );

      // adding output encoding type: TMPL_OUTPUT_ENCODING
      context.put(
        TemplateConstant.TMPL_OUTPUT_ENCODING,
        mVelocityEngine.getProperty( "output.encoding" ) );

      return context;
      
    } catch( Throwable e ) {

      throw new TemplateProcessorException( "Could not createContext "
        + templateRequest.toString(), e );
    }
  }

}
