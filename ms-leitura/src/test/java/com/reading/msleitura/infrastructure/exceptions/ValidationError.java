package com.reading.msleitura.infrastructure.exceptions;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * ValidationError
 */
@Getter
@Setter
class ValidationError extends ApiError {

	public ValidationError(Integer errorCode, String message) {
		super(errorCode, message);
	}

	private List<FieldMessage> errors = new ArrayList<>();

	public void addError(String fieldName, String messagem) {
		errors.add(new FieldMessage(fieldName, messagem));
	}

}