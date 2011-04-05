package com.vm.email;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class EmailUtils
{
  private String mHostName;
  
  public  void sendEmail(String fromAddress, 
                               String toAddress,
                               String subject,
                               String body) throws EmailException  {
    HtmlEmail email = new HtmlEmail();
    //email.setHostName("mail.myserver.com");
    email.setHostName(mHostName);
    email.addTo(toAddress);
    email.setFrom(fromAddress, "Ford Rent Company");
    email.setSubject(subject);
    email.setDebug(true);
    email.setCharset( "UTF-8" );
    email.setAuthentication("10676624", "freedog");
    //email.setAuthentication("bradwa2", "freeweb");
    
    //set the html message
    email.setHtmlMsg(body);

    //send the email
    email.send();

  }

  /**
   * @return Returns the value of {@link #mHostName}.
   */
  public String getHostName()
  {
    return mHostName;
  }

  /**
   * @param hostName Set the value of {@link #mHostName}.
   */
  public void setHostName( String hostName )
  {
    mHostName = hostName;
  }
  
  
}
