package com.lms.exception;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lms.dto.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleNotFound(ResourceNotFoundException ex) {
		return new ResponseEntity<>(new ApiResponse(ex.getMessage(), 404), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiResponse> handleRuntime(RuntimeException ex) {
		return new ResponseEntity<>(new ApiResponse(ex.getMessage(), 400), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException ex) {

	    String error = ex.getBindingResult()
	            .getFieldError()
	            .getDefaultMessage();

	    return new ResponseEntity<>(
	            new ApiResponse(error, 400),
	            HttpStatus.BAD_REQUEST
	    );
	}
}