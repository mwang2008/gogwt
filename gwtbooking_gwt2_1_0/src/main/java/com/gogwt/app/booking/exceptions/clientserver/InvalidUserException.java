package com.gogwt.app.booking.exceptions.clientserver;

public class InvalidUserException extends AppRemoteException {
	public InvalidUserException() {
		super();
	}

	public InvalidUserException(String message) {
		super(message);
	}

	public InvalidUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidUserException(Throwable cause) {
		super(cause);
	}
}
