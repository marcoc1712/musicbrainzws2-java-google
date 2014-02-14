package org.mc2.util.exceptions;

/**
 * All application specific exceptions extend this class.
 */

public class ObjectPersistException extends MC2Exception {
    private static final long serialVersionUID = 1L;
    public ObjectPersistException() {
		super();
	}

	public ObjectPersistException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectPersistException(String message) {
		super(message);
	}

	public ObjectPersistException(Throwable cause) {
		super(cause);
	}

}
