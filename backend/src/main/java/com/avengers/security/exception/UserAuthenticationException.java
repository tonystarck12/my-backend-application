package com.avengers.security.exception;

public class UserAuthenticationException extends Exception {

	private static final long serialVersionUID = 1L;

	private String errorMsg;

	public UserAuthenticationException(String msg, Exception e) {
		super(msg,e);
		this.errorMsg = msg;

	}

	public String toString() {
		return "User Unauthorization Error : " + errorMsg;
	}
}
