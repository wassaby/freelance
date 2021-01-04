/*
 * Created on 06.10.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: StrutsHelperException.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.struts.helper;

public class StrutsHelperException extends Exception {

	private String message = "";

	public StrutsHelperException() {
		super();

	}

	public StrutsHelperException(String message) {
		super(message);
		this.message = message;
	}

	public StrutsHelperException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	public StrutsHelperException(Throwable cause) {
		super(cause);
	}

	public String toString() {
		return "StrutsHelperException[" + message + "]";
	}

}