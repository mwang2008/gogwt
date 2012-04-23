package com.gogwt.apps.tracking.exceptions;

public class CouldNotFindException extends AppRemoteException {
	public CouldNotFindException() {
		super();
	}

	public CouldNotFindException(String message) {
		super(message);
	}

	public CouldNotFindException(String message, Throwable cause) {
		super(message, cause);
	}

	public CouldNotFindException(Throwable cause) {
		super(cause);
	}
}
