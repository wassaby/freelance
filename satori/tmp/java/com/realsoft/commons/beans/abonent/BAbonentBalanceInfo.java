/*
 * Created on 14.12.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BAbonentBalanceInfo.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.abonent;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Содержит информацию о балансе абонента.
 * 
 * @author temirbulatov
 * 
 */
public class BAbonentBalanceInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String contract;

	private Calendar openDate;

	private Calendar closeDate;

	private String provider;

	private double balance;

	public BAbonentBalanceInfo() {
		super();
	}

	public BAbonentBalanceInfo(double balance) {
		super();
		this.balance = balance;
	}

	/**
	 * 
	 * @param contract
	 *            контракт
	 * @param openDate
	 *            дата открытия контракта
	 * @param closeDate
	 *            дата закрытия контракта
	 * @param provider
	 *            провайдер
	 * @param balance
	 *            баланс
	 */
	public BAbonentBalanceInfo(String contract, Calendar openDate,
			Calendar closeDate, String provider, double balance) {
		super();
		this.contract = contract;
		this.openDate = openDate;
		this.closeDate = closeDate;
		this.provider = provider;
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Calendar getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Calendar closeDate) {
		this.closeDate = closeDate;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public Calendar getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Calendar openDate) {
		this.openDate = openDate;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

}
