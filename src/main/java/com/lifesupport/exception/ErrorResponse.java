package com.lifesupport.exception;

import java.util.Date;

public class ErrorResponse {
	private int status;
	private String message;
	private Date date;

	public ErrorResponse() {
		// TODO Auto-generated constructor stub
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public ErrorResponse(int status, String message, Date date) {
		super();
		this.status = status;
		this.message = message;
		this.date = date;
	}

	@Override
	public String toString() {
		return "ErrorResponse [status = " + status + ", message = " + message + ", Date = " + date + "]";
	}

	public void ShowErrors() {
		System.out.println("message: " + message);
		System.out.println("status: " + status);
		System.out.println("date: " + date);

	}
}
