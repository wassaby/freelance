package com.realsoft.commons.beans.connections;

import java.io.Serializable;
import java.util.Date;

public class BConnectorInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String address;

	private Date registrationDate;

	private Double personalAccount;

	private Double chargeCurrent;

	private Double paymentCurrent;

	private Double paymentPrevious;

	private String currency;

	/**
	 * 
	 * @param name
	 *            имя абонента в системе bittl135
	 * @param address
	 *            адрес абонента
	 * @param registrationDate
	 *            дата регистрации
	 * @param personalAccount
	 *            величина исходящего сальдо
	 * @param chargeCurrent
	 *            величина ничисления
	 * @param paymentCurrent
	 *            величина оплаты
	 * @param paymentPrevious
	 *            величина исходящего сальдо
	 * @param currency
	 *            тип валюты
	 */
	public BConnectorInfo(String name, String address, Date registrationDate,
			Double personalAccount, Double chargeCurrent,
			Double paymentCurrent, Double paymentPrevious, String currency) {
		super();
		// TODO Auto-generated constructor stub
		this.name = name;
		this.address = address;
		this.registrationDate = registrationDate;
		this.personalAccount = personalAccount;
		this.chargeCurrent = chargeCurrent;
		this.paymentCurrent = paymentCurrent;
		this.paymentPrevious = paymentPrevious;
		this.currency = currency;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getChargeCurrent() {
		return chargeCurrent;
	}

	public void setChargeCurrent(Double chargeCurrent) {
		this.chargeCurrent = chargeCurrent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPaymentCurrent() {
		return paymentCurrent;
	}

	public void setPaymentCurrent(Double paymentCurrent) {
		this.paymentCurrent = paymentCurrent;
	}

	public Double getPaymentPrevious() {
		return paymentPrevious;
	}

	public void setPaymentPrevious(Double paymentPrevious) {
		this.paymentPrevious = paymentPrevious;
	}

	public Double getPersonalAccount() {
		return personalAccount;
	}

	public void setPersonalAccount(Double personalAccount) {
		this.personalAccount = personalAccount;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
