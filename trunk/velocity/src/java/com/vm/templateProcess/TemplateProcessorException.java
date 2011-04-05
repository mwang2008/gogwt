package com.vm.templateProcess;


public class TemplateProcessorException
extends RuntimeException
{
  /**
   * Constructor
   * <p />
   * 
   * @param message
   *          A description for this exception
   */
  public TemplateProcessorException( String message )
  {
    super( message );
  }

   
  /**
   * Constructor
   * <p />
   * 
   * @param message
   *          A description for this exception
   * @param origException
   *          Previous exception
   */
  public TemplateProcessorException( String message, Throwable origException )
  {
    super( message, origException );
  }
}
