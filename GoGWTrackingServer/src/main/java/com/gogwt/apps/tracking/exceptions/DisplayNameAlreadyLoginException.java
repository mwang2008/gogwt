package com.gogwt.apps.tracking.exceptions;

public class DisplayNameAlreadyLoginException extends AppRemoteException {
	  /**
	   * serialVersionUID - long
	   */
	  private static final long serialVersionUID = 1L;

	  public DisplayNameAlreadyLoginException()
	  {
	    super();
	     
	  }

	  public DisplayNameAlreadyLoginException( Throwable throwable )
	  {
	    super( throwable );
	     
	  }
	  
	  public DisplayNameAlreadyLoginException(String message) {
			super(message);
	  }
	  
	  public DisplayNameAlreadyLoginException(String message, Throwable cause) {
			super(message, cause);
	  }
}
