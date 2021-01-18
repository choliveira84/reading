/**
 * 
 */
package com.reading.msnotificacao.infrastructure.exceptions;

/**
 * @author Carlos H. de Oliveira - carlos.h.oliveira@cho.eti.br
 *
 */
public class FileException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public FileException(String msg) {
		super(msg);
	}

	public FileException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
