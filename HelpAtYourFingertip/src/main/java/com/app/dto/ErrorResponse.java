package com.app.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ErrorResponse {
	private String message;
	private LocalDateTime timeStamp;
	
	public ErrorResponse() {
		super();
	}
	public ErrorResponse(String message, LocalDateTime timeStamp) {
		super();
		this.message = message;
		this.timeStamp = timeStamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
