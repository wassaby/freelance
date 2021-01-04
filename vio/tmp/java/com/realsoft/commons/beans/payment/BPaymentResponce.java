/**
 * BPaymentResponce.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.payment;

/**
 * Класс предоставляющий данные по оплате
 * 
 * @author temirbulatov
 * 
 */
public class BPaymentResponce implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private double balance;

	private java.lang.String note;

	private Long reference;

	private java.lang.String responce;

	public BPaymentResponce() {
	}

	/**
	 * 
	 * @param balance
	 *            баланс
	 * @param note
	 * @param reference
	 * @param responce
	 */
	public BPaymentResponce(double balance, java.lang.String note,
			Long reference, java.lang.String responce) {
		this.balance = balance;
		this.note = note;
		this.reference = reference;
		this.responce = responce;
	}

	/**
	 * Gets the balance value for this BPaymentResponce.
	 * 
	 * @return balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Sets the balance value for this BPaymentResponce.
	 * 
	 * @param balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * Gets the note value for this BPaymentResponce.
	 * 
	 * @return note
	 */
	public java.lang.String getNote() {
		return note;
	}

	/**
	 * Sets the note value for this BPaymentResponce.
	 * 
	 * @param note
	 */
	public void setNote(java.lang.String note) {
		this.note = note;
	}

	/**
	 * Gets the reference value for this BPaymentResponce.
	 * 
	 * @return reference
	 */
	public Long getReference() {
		return reference;
	}

	/**
	 * Sets the reference value for this BPaymentResponce.
	 * 
	 * @param reference
	 */
	public void setReference(Long reference) {
		this.reference = reference;
	}

	/**
	 * Gets the responce value for this BPaymentResponce.
	 * 
	 * @return responce
	 */
	public java.lang.String getResponce() {
		return responce;
	}

	/**
	 * Sets the responce value for this BPaymentResponce.
	 * 
	 * @param responce
	 */
	public void setResponce(java.lang.String responce) {
		this.responce = responce;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof BPaymentResponce))
			return false;
		BPaymentResponce other = (BPaymentResponce) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& this.balance == other.getBalance()
				&& ((this.note == null && other.getNote() == null) || (this.note != null && this.note
						.equals(other.getNote())))
				&& ((this.reference == null && other.getReference() == null) || (this.reference != null && this.reference
						.equals(other.getReference())))
				&& ((this.responce == null && other.getResponce() == null) || (this.responce != null && this.responce
						.equals(other.getResponce())));
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		_hashCode += new Double(getBalance()).hashCode();
		if (getNote() != null) {
			_hashCode += getNote().hashCode();
		}
		if (getReference() != null) {
			_hashCode += getReference().hashCode();
		}
		if (getResponce() != null) {
			_hashCode += getResponce().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

}
