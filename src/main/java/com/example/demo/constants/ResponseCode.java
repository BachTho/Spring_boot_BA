package com.example.demo.constants;

public enum ResponseCode {
	SUCCESS(200, "OK"), NOT_FOUND(404, "Not found"), NO_PARAM(6001, "No param"), NO_CONTENT(2004, "No content"),
	INTERNAL_SERVER_ERROR(5000, "Internal server error"), DATA_EXISTS(2005, "User is exists");

	private int code;
	private String message;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	private ResponseCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
}