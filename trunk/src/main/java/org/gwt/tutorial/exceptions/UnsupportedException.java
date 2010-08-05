package org.gwt.tutorial.exceptions;

public class UnsupportedException extends RuntimeException {

	/**
	 * serialVersionUID - long
	 */
	private static final long serialVersionUID = 6352051459180236077L;

	public UnsupportedException() {
		super();
	}

	public UnsupportedException(String message) {
		super(message);
	}

	public UnsupportedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedException(Throwable cause) {
		super(cause);
	}
}
