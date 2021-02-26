package com.vmware.employee.error;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler class.
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	/** 
	 * Error handler for {@link TaskException}.
	 * 
	 * @param request HTTP request object.
	 * @param e the {@link TaskException}.
	 * @return entity with status {@link HttpStatus#BAD_REQUEST}
	 */
	@ExceptionHandler({TaskException.class})
	public ResponseEntity<ErrorResponse> handleTaskException(final HttpServletRequest request, final TaskException e) {
		return createResponseEntity(HttpStatus.BAD_REQUEST, request, e);
	}
	
	/**
	 * Creates a {@link ResponseEntity} object for error response.
	 * 
	 * @param status Http status to e used in response.
	 * @param request HTTP request object. 
	 * @param e Exception being handled.
	 * @return {@link ResponseEntity} containing error details.
	 */
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
