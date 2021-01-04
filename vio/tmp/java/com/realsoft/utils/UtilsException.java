/*
 * Created on 09.02.2005
 *
 * Realsoft Ltd.
 * Dmitry Dudorga
 * $Id: UtilsException.java,v 1.2 2016/04/15 10:37:49 dauren_home Exp $
 */
package com.realsoft.utils;

/**
 * @author dimad
 */
public class UtilsException extends Exception {

	private String message = "";

	public UtilsException() {
		super();
	}

	/**
	 * @param message
	 */
	public UtilsException(String message) {
		super(message);
		this.message = message;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UtilsException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	/**
	 * @param cause
	 */
	public UtilsException(Throwable cause) {
		super(cause);
	}

	public String toString() {
		return getClass().getName() + " [ " + message + " ]";
	}

}
