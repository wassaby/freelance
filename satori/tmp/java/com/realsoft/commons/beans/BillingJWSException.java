/*
 * Created on 15.02.2005
 *
 * Realsoft Ltd.
 * Dmitry Dudorga.
 * $Id: BillingJWSException.java,v 1.1 2014/07/01 11:58:26 dauren_work Exp $
 */
package com.realsoft.commons.beans;

import java.rmi.RemoteException;

/**
 * 
 */
public class BillingJWSException extends RemoteException {

	private static final long serialVersionUID = 3691038759885944629L;

	private String message;

	private String note;

	private String responce;

	/**
	 * 
	 */
	public BillingJWSException() {
		super();
	}

	public BillingJWSException(String s, String note, String responce) {
		super(s);
		this.message = s;
		this.note = note;
		this.responce = responce;
	}

	public BillingJWSException(String s, String note, String responce,
			Throwable e) {
		super(s, e);
		this.message = s;
		this.note = note;
		this.responce = responce;
	}

	public BillingJWSException(String s, String responce, Throwable e) {
		super(s, e);
		this.message = s;
		this.note = s;
		this.responce = responce;
	}

	public BillingJWSException(String s, String responce) {
		super(s);
		this.message = s;
		this.note = s;
		this.responce = responce;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[" + message + "\n\t" + note + "\n\t" + responce + "]";
	}

	/**
	 * @return Returns the note.
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @return Returns the responce.
	 */
	public String getResponce() {
		return responce;
	}
}
