/**
 * BTotalItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.report.abonent;

/**
 * Этот класс содержит всю информацию об абоненте. Используется при генерации
 * отчетов.
 * 
 * @author temirbulatov
 * 
 */
public class BTotalItem implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private double amount;

	private java.lang.String currency;

	private double debit;

	private String note;

	private String message;

	public BTotalItem() {
	}

	/**
	 * Конструктор класса BTotalItem
	 * 
	 * @param amount
	 *            сумма денег
	 * @param currency
	 *            вид валюты
	 * @param debit
	 *            дебит счета
	 * @param message
	 *            сообщение
	 * @param note
	 *            запись
	 */
	public BTotalItem(double amount, java.lang.String currency, double debit,
			String message, String note) {
		this.amount = amount;
		this.currency = currency;
		this.debit = debit;
		this.message = message;
		this.note = note;
	}

	/**
	 * Gets the amount value for this BTotalItem.
	 * 
	 * @return amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount value for this BTotalItem.
	 * 
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the currency value for this BTotalItem.
	 * 
	 * @return currency
	 */
	public java.lang.String getCurrency() {
		return currency;
	}

	/**
	 * Sets the currency value for this BTotalItem.
	 * 
	 * @param currency
	 */
	public void setCurrency(java.lang.String currency) {
		this.currency = currency;
	}

	/**
	 * Gets the debit value for this BTotalItem.
	 * 
	 * @return debit
	 */
	public double getDebit() {
		return debit;
	}

	/**
	 * Sets the debit value for this BTotalItem.
	 * 
	 * @param debit
	 */
	public void setDebit(double debit) {
		this.debit = debit;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}