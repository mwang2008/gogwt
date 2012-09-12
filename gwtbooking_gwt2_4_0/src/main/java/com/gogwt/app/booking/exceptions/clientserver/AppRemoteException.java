package com.gogwt.app.booking.exceptions.clientserver;

/**
 * <code><B>AppRemoteException<code><B>
 * <p/>
 * Define based exception class, all other checked exception should inherit this.
 * <p/>
 */

public class AppRemoteException extends Exception {
	public AppRemoteException() {
		super();
	}

	public AppRemoteException(Throwable throwable) {
		super(throwable);
	}

	public AppRemoteException(String message) {
		super(message);
	}

	public AppRemoteException(String message, Throwable cause) {
		super(message, cause);
	}

}
