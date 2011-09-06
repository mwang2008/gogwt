package com.gogwt.apps.tracking.exceptions;

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
