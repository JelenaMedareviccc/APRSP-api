package com.example.project.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityDoesNotExist.class)
	public ResponseEntity<CustomErrorResponse> springHandleNotFound(Exception ex, WebRequest request) {
	    CustomErrorResponse errors = new CustomErrorResponse();
	    errors.setTimestamp(LocalDateTime.now());
	    errors.setError(ex.getMessage());
	    errors.setStatus(HttpStatus.NOT_FOUND.value());

	    return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}

}
