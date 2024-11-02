package com.picpaysimplificado.exceptions;

import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.picpaysimplificado.records.ExceptionRecord;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class RestControllerExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
		String errorMessage = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.joining(", "));
		ExceptionRecord exceptionRecord = new ExceptionRecord(errorMessage, 400);
		return ResponseEntity.badRequest().body(exceptionRecord);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		ExceptionRecord exceptionRecord = new ExceptionRecord("User already exists", 400);
		return ResponseEntity.badRequest().body(exceptionRecord);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException() {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGenericException(Exception ex) {
		ExceptionRecord exceptionRecord = new ExceptionRecord(ex.getMessage(), 500);
		return ResponseEntity.internalServerError().body(exceptionRecord);
	}

}
