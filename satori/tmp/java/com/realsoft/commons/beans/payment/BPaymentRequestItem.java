/*
 * Created on 19.01.2007
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BPaymentRequestItem.java,v 1.1 2014/07/01 11:58:27 dauren_work Exp $
 */
package com.realsoft.commons.beans.payment;

import java.util.Calendar;

public class BPaymentRequestItem {

	private long account;

	private double amount;

	private Calendar currDate;

	private String currency;

	private String documentNo;

	private String netSource;

	private String subDivision;

	private String payType;

	private String terminal;

	private String transNo;

	private String phone;

	/**
	 * @param account
	 * @param amount
	 * @param currDate
	 * @param currency
	 * @param documentNo
	 * @param netSource
	 * @param subDivision
	 * @param payType
	 * @param terminal
	 * @param transNo
	 * @param phone
	 */
	public BPaymentRequestItem(long account, double amount, Calendar currDate,
			String currency, String documentNo, String netSource,
			String subDivision, String payType, String terminal,
			String transNo, String phone) {
		super();
		this.account = account;
		this.amount = amount;
		this.currDate = currDate;
		this.currency = currency;
		this.documentNo = documentNo;
		this.netSource = netSource;
		this.subDivision = subDivision;
		this.payType = payType;
		this.terminal = terminal;
		this.transNo = transNo;
		this.phone = phone;
	}

	public long getAccount() {
		return account;
	}

	public void setAccount(long account) {
		this.account = account;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Calendar getCurrDate() {
		return currDate;
	}

	public void setCurrDate(Calendar currDate) {
		this.currDate = currDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getNetSource() {
		return netSource;
	}

	public void setNetSource(String netSource) {
		this.netSource = netSource;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSubDivision() {
		return subDivision;
	}

	public void setSubDivision(String subDivision) {
		this.subDivision = subDivision;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String toString() {
		return BPaymentRequestItem.class.getName() + "[ account = " + account
				+ "; amount = " + amount + "; currDate = "
				+ currDate.toString() + "; currency = " + currency
				+ "; documentNo = " + documentNo + "; netSource = " + netSource
				+ "; subDivision = " + subDivision + "; payType = " + payType
				+ "; terminal = " + terminal + "; transNo = " + transNo
				+ "; phone = " + phone + " ]";
	}
}
