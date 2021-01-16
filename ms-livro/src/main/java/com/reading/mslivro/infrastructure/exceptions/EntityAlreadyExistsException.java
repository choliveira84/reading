package com.reading.mslivro.infrastructure.exceptions;

/**
 * EntityNotFoundException
 */
public class EntityAlreadyExistsException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public EntityAlreadyExistsException(String msg) {
		super(msg);
	}

	public EntityAlreadyExistsException(String msg, Throwable cause) {
		super(msg, cause);
	}
}