package org.molgenis.database;

public class UnknownMolgenisEntityException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnknownMolgenisEntityException() {
	}

	public UnknownMolgenisEntityException(String message) {
		super(message);
	}

	public UnknownMolgenisEntityException(Throwable cause) {
		super(cause);
	}

	public UnknownMolgenisEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownMolgenisEntityException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
