/**
 * BRegistrationInfo13.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.registration;

/**
 * Интерфейс, содержащий информацию об абоненте или операторе.
 * 
 * @author temirbulatov
 * 
 */
public class BRegistrationInfo13 implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private double balance;

	private long abonentId;

	private String guid;

	private java.lang.String name;

	private java.lang.String note;

	private java.lang.String password;

	private java.lang.String responce;

	private long workPlaceId;

	private long townId;

	public BRegistrationInfo13() {
		super();
	}

	/**
	 * 
	 * @param balance
	 *            баланс абонента
	 * @param abonentId
	 *            лицевой счет абонента или идентификатор оператора
	 * @param name
	 *            имя абонента или оператора
	 * @param note
	 *            дополнительная информация
	 * @param password
	 *            пароль оператора
	 * @param responce
	 *            строковые константы используемые в народном банке
	 */
	public BRegistrationInfo13(double balance, long abonentId, String name,
			String password, String note, String responce) {
		super();
		this.balance = balance;
		this.abonentId = abonentId;
		this.name = name;
		this.note = note;
		this.password = password;
		this.responce = responce;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getPassword() {
		return password;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	public java.lang.String getResponce() {
		return responce;
	}

	public void setResponce(java.lang.String responce) {
		this.responce = responce;
	}

	public long getAbonentId() {
		return abonentId;
	}

	public void setAbonentId(long abonentId) {
		this.abonentId = abonentId;
	}

	public long getWorkPlaceId() {
		return workPlaceId;
	}

	public void setWorkPlaceId(long workPlaceId) {
		this.workPlaceId = workPlaceId;
	}

	public long getTownId() {
		return townId;
	}

	public void setTownId(long townId) {
		this.townId = townId;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

}
