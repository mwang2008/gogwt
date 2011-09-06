package com.gogwt.apps.tracking.exceptions;


public class SessionTimedoutException extends AppRemoteException
{

	  /**
	   * serialVersionUID - long
	   */
	  private static final long serialVersionUID = 1L;

	  public SessionTimedoutException()
	  {
	    super();
	     
	  }

	  public SessionTimedoutException( Throwable throwable )
	  {
	    super( throwable );
	     
	  }
	  
	  public SessionTimedoutException(String message) {
			super(message);
	  }


}
