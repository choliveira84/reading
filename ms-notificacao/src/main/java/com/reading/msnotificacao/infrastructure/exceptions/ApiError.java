package com.reading.msnotificacao.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DefaultError
 */
@Getter
@Setter
@AllArgsConstructor
class ApiError {

	private Integer errorCode;

	private String message;
}