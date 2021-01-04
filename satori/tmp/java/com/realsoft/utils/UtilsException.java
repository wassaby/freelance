/*
 * Created on 09.02.2005
 *
 * Realsoft Ltd.
 * Dmitry Dudorga
 * $Id: UtilsException.java,v 1.1 2014/07/01 11:58:28 dauren_work Exp $
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
