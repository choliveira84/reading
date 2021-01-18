package com.reading.msnotificacao.infrastructure.exceptions;

/**
 * @author Carlos H. de Oliveira - carlos.h.oliveira@cho.eti.br
 *
 */
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadRequestException(String msg) {
		super(msg);
	}

	public BadRequestException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
