package com.gogwt.apps.tracking.exceptions;

public class AppRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AppRuntimeException() {
		super();
	}

	public AppRuntimeException(Throwable throwable) {
		super(throwable);
	}

	public AppRuntimeException(String message) {
		super(message);
	}

	public AppRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
