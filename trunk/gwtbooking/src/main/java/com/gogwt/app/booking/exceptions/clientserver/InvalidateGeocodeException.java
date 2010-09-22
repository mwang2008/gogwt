package com.gogwt.app.booking.exceptions.clientserver;

public class InvalidateGeocodeException extends AppRemoteException
{

	  /**
	   * serialVersionUID - long
	   */
	  private static final long serialVersionUID = 1L;

	  public InvalidateGeocodeException()
	  {
	    super();
	     
	  }

	  public InvalidateGeocodeException( Throwable throwable )
	  {
	    super( throwable );
	     
	  }
	  
	  public InvalidateGeocodeException(String message) {
			super(message);
	  }

}
