package com.reading.msnotificacao.infrastructure.exceptions;

/**
 * @author Carlos H. de Oliveira - carlos.h.oliveira@cho.eti.br
 *
 */
public class MissingFieldsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MissingFieldsException(String msg) {
		super(msg);
	}

	public MissingFieldsException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
