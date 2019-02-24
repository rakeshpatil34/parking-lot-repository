package com.parkinglot.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class APIException {
	
	private String exceptionMessage;
	private HttpStatus httpStatus;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	private LocalDateTime time;
	
	public APIException(String exceptionMessage, HttpStatus httpStatus) {
		super();
		this.exceptionMessage = exceptionMessage;
		this.httpStatus = httpStatus;
		this.time = LocalDateTime.now();
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
		
	
	

}
