package com.vm.templateProcess;

 
public interface TemplateProcessor
{
  /**
   * process tempalte and return string. <p/>
   * 
   * @param templateRequest
   *          the template input request
   * @return String - the result of processed template in the form of string.
   * @throws TemplateProcessorException
   *           If fail when process template.
   */
  public String process( TemplateRequest templateRequest )
    throws TemplateProcessorException;
}
