package com.parkinglot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.parkinglot.exceptions.APIException;
import com.parkinglot.exceptions.InvalidParkingLotException;
import com.parkinglot.exceptions.OverlappingScheduleException;
import com.parkinglot.exceptions.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	
	@ExceptionHandler(InvalidParkingLotException.class)
	protected ResponseEntity<Object> handleInvalidParkingLotException(InvalidParkingLotException ex){
		APIException apiException = new APIException(ex.getMessage(), HttpStatus.BAD_REQUEST);
		return buildResponseEntity(apiException);
	}
	
	@ExceptionHandler(OverlappingScheduleException.class)
	protected ResponseEntity<Object> handleOverlappingScheduleException(OverlappingScheduleException ex){
		APIException apiException = new APIException(ex.getMessage(), HttpStatus.BAD_REQUEST);
		return buildResponseEntity(apiException);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex){
		APIException apiException = new APIException(ex.getMessage(), HttpStatus.BAD_REQUEST);
		return buildResponseEntity(apiException);
	}
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleException(Exception ex){
		APIException apiException = new APIException(ex.getMessage(), HttpStatus.BAD_REQUEST);
		return buildResponseEntity(apiException);
	}
	
	private ResponseEntity<Object> buildResponseEntity(APIException apiException) {
		return new ResponseEntity<Object>(apiException, apiException.getHttpStatus());
	}
}
