package com.gogwt.app.booking.exceptions.clientserver;

public class InvalidPasswordException extends AppRemoteException {
	public InvalidPasswordException() {
		super();
	}

	public InvalidPasswordException(String message) {
		super(message);
	}

	public InvalidPasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPasswordException(Throwable cause) {
		super(cause);
	}

}
