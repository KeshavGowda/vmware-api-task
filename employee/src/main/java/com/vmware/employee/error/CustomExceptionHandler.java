package com.vmware.employee.error;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler({TaskException.class})
	public ResponseEntity<ErrorResponse> handleTaskNotFoundException(final HttpServletRequest request, final TaskException e) {
		return createResponseEntity(HttpStatus.BAD_REQUEST, request, e);
	}
	
	private ResponseEntity<ErrorResponse> createResponseEntity(final HttpStatus status, final HttpServletRequest request, final Exception e) {
		final ErrorResponse response = new ErrorResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setStatus(status.value());
		response.setError(e.getClass().getName());
		response.setMessage(e.getMessage());
		response.setPath(request.getRequestURL().toString());
		return new ResponseEntity<>(response, status);
	}

}
