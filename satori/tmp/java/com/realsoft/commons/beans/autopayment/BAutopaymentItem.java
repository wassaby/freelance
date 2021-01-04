/**
 * BAutopaymentItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.autopayment;

/**
 * Класс содержаний информацию об автооплате абонента
 * 
 * @author temirbulatov
 * 
 */
public class BAutopaymentItem implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long abonentId;

	private String guid;

	private double amount;

	private double balance;

	private java.lang.String note;

	private java.lang.String phone;

	public BAutopaymentItem() {
	}

	/**
	 * Конструктор класса {@link BAutopaymentItem}
	 * 
	 * @param accountId
	 *            идентификатор счета
	 * @param amount
	 *            сумма
	 * @param note
	 *            дополнительная информация
	 * @param phone
	 *            десятизначный номер телефона (с кодом города)
	 */
	public BAutopaymentItem(long accountId, double amount,
			java.lang.String note, java.lang.String phone) {
		this.abonentId = accountId;
		this.amount = amount;
		this.note = note;
		this.phone = phone;
	}

	/**
	 * Gets the abonentId value for this BAutopaymentItem.
	 * 
	 * @return abonentId
	 */
	public long getAbonentId() {
		return abonentId;
	}

	/**
	 * Sets the abonentId value for this BAutopaymentItem.
	 * 
	 * @param abonentId
	 */
	public void setAbonentId(long abonentId) {
		this.abonentId = abonentId;
	}

	/**
	 * Gets the amount value for this BAutopaymentItem.
	 * 
	 * @return amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount value for this BAutopaymentItem.
	 * 
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the balance value for this BAutopaymentItem.
	 * 
	 * @return balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Sets the balance value for this BAutopaymentItem.
	 * 
	 * @param balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * Gets the note value for this BAutopaymentItem.
	 * 
	 * @return note
	 */
	public java.lang.String getNote() {
		return note;
	}

	/**
	 * Sets the note value for this BAutopaymentItem.
	 * 
	 * @param note
	 */
	public void setNote(java.lang.String note) {
		this.note = note;
	}

	/**
	 * Gets the phone value for this BAutopaymentItem.
	 * 
	 * @return phone
	 */
	public java.lang.String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone value for this BAutopaymentItem.
	 * 
	 * @param phone
	 */
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

}
