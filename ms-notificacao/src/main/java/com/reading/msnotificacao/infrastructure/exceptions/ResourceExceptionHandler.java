package com.reading.msnotificacao.infrastructure.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * ResourceExceptionHandler
 */
@ControllerAdvice
public class ResourceExceptionHandler {

	private static final String VALIDATION_ERROR = "Erro de validação";

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiError> objectNotFound(EntityNotFoundException e, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiError(HttpStatus.NOT_FOUND.value(), e.getMessage()));
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ApiError> badRequestException(UsernameNotFoundException e, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiError(HttpStatus.NOT_FOUND.value(), e.getMessage()));
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiError> badRequestException(BadRequestException e, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		ValidationError err = new ValidationError(HttpStatus.UNPROCESSABLE_ENTITY.value(), VALIDATION_ERROR);

		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiError> constraintViolationHandler(ConstraintViolationException e,
			HttpServletRequest request) {
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), VALIDATION_ERROR);

		for (ConstraintViolation<?> x : e.getConstraintViolations()) {
			err.addError(x.getPropertyPath().toString(), x.getMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiError> httpRequestMethodNotSupportHandler(HttpServletRequest req, Exception e) {
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body(new ApiError(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage()));
	}

	@ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiError> methodArgumentTypeMismatchHandler(HttpServletRequest req, Exception e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
	}

	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public ResponseEntity<ApiError> httpMessageNotReadableHandler(HttpServletRequest req, Exception e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ApiError> validationException(ValidationException e, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiError> illegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
	}

	@ExceptionHandler(MissingFieldsException.class)
	public ResponseEntity<ApiError> missingFieldsException(MissingFieldsException e, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
	}

	@ExceptionHandler(EntityAlreadyExistsException.class)
	public ResponseEntity<ApiError> entityAlreadyExistsException(EntityAlreadyExistsException e,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ApiError(HttpStatus.CONFLICT.value(), e.getMessage()));
	}

}