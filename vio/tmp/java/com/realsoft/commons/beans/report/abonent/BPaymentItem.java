/**
 * BPaymentItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.report.abonent;

import java.util.Calendar;

/**
 * Класс содержащий информацию об оплате абонента
 * 
 * @author temirbulatov
 * 
 */
public class BPaymentItem implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long abonentId;

	private double amount;

	private String currency;

	private Calendar payDate;

	private String phone;

	private String transNo;

	private String note;

	private String message;

	/**
	 * Конструктор класса {@link BPaymentItem}
	 * 
	 */
	public BPaymentItem() {
	}

	/**
	 * Конструктор класса {@link BPaymentItem}
	 * 
	 * @param abonentId
	 *            идентификатор абонента
	 * @param amount
	 *            сумма денег, обозначенная как "credit"
	 * @param currency
	 *            вид валюты
	 * @param payDate
	 *            дата оплаты
	 * @param phone
	 *            десятизначный номер телефона(с кодом города)
	 * @param message
	 *            сообщение
	 * @param note
	 *            запись
	 */
	public BPaymentItem(long abonentId, double amount, String currency,
			Calendar payDate, String phone, String message, String note,
			String transNo) {
		this.abonentId = abonentId;
		this.amount = amount;
		this.currency = currency;
		this.payDate = payDate;
		this.phone = phone;
		this.message = message;
		this.note = note;
		this.transNo = transNo;
	}

	/**
	 * Gets the abonentId value for this BPaymentItem.
	 * 
	 * @return abonentId
	 */
	public long getAbonentId() {
		return abonentId;
	}

	/**
	 * Sets the abonentId value for this BPaymentItem.
	 * 
	 * @param abonentId
	 */
	public void setAbonentId(long abonentId) {
		this.abonentId = abonentId;
	}

	/**
	 * Gets the amount value for this BPaymentItem.
	 * 
	 * @return amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount value for this BPaymentItem.
	 * 
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the currency value for this BPaymentItem.
	 * 
	 * @return currency
	 */
	public java.lang.String getCurrency() {
		return currency;
	}

	/**
	 * Sets the currency value for this BPaymentItem.
	 * 
	 * @param currency
	 */
	public void setCurrency(java.lang.String currency) {
		this.currency = currency;
	}

	/**
	 * Gets the payDate value for this BPaymentItem.
	 * 
	 * @return payDate
	 */
	public java.util.Calendar getPayDate() {
		return payDate;
	}

	/**
	 * Sets the payDate value for this BPaymentItem.
	 * 
	 * @param payDate
	 */
	public void setPayDate(java.util.Calendar payDate) {
		this.payDate = payDate;
	}

	/**
	 * Gets the phone value for this BPaymentItem.
	 * 
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone value for this BPaymentItem.
	 * 
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

}