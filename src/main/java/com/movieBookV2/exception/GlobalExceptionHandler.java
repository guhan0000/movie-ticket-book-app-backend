package com.movieBookV2.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String,String>> handleRuntimeException(RuntimeException ex){
		 return new ResponseEntity<>(
	                Map.of("error", ex.getMessage()),
	                HttpStatus.BAD_REQUEST
	        );
		
	}

}
