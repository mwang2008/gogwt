package com.vm.templateProcess;

import com.vm.templateProcess.velocity.VelocityTemplateProcessor;

 
public class TemplateProcessorFactory
{
  public static int VELOCITY = 1;

  /**
   * Get instance of TemplateProcessor. 
   * <p/>
   * 
   * @param implementorId
   *          the id binded with real implementation
   * @param isLocal
   *          TRUE from unit test, FALSE from app server
   * @return TemplateProcessor the instance of implementation.
   * @throws TemplateProcessorException
   *           If provide wrong implementorId
   */
  public static TemplateProcessor getInstance( int implementorId)
    throws TemplateProcessorException
  {
    if ( implementorId == VELOCITY ) {
      return new VelocityTemplateProcessor();
    }

    // otherwise
    throw new TemplateProcessorException( "No processor invoke for type of "
      + implementorId );
  }
}
