package com.daurenzg.betstars.utils;

public class BUtilsException extends Exception {

	private static final long serialVersionUID = -1186978683527828276L;
	private int code;
	private String message;
	
	public BUtilsException(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
