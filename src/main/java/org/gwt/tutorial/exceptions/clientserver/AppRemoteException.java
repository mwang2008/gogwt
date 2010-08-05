package org.gwt.tutorial.exceptions.clientserver;

/**
 * <code><B>AppRemoteException<code><B>
 * <p/>
 * Define based exception class, all other checked exception should inherit this.
 * <p/>
 * @author mwang
 
 */

public class AppRemoteException
  extends Exception
{
  public AppRemoteException()
  {
    super();
  }

  public AppRemoteException( Throwable throwable )
  {
    super( throwable );
  }
  
  public AppRemoteException(String message) {
	super(message);
  }
} 
