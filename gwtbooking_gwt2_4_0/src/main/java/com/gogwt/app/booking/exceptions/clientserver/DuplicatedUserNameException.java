package com.gogwt.app.booking.exceptions.clientserver;

public class DuplicatedUserNameException extends AppRemoteException
{

	  /**
	   * serialVersionUID - long
	   */
	  private static final long serialVersionUID = 1L;

	  public DuplicatedUserNameException()
	  {
	    super();
	     
	  }

	  public DuplicatedUserNameException( Throwable throwable )
	  {
	    super( throwable );
	     
	  }
	  
	  public DuplicatedUserNameException(String message) {
			super(message);
	  }
	  
	  public DuplicatedUserNameException(String message, Throwable cause) {
			super(message, cause);
	  }

}
