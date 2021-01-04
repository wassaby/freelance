package com.realsoft.commons.beans.abonent;

import java.io.Serializable;

public class BAbonentDebitItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long reportDateId;

	private long billId;

	private String reportDateName = null;

	private String billTypeName = null;

	/**
	 * Входящее сальдо
	 */
	private double inMoney;

	/**
	 * Начислено
	 */
	private double billDebit;

	/**
	 * Оплачено
	 */
	private double credit;

	/**
	 * Исходящее сальдо
	 */
	private double outMoney;

	/**
	 * Позиция счета
	 */
	private String dbillTypeName = null;

	private double dbillDebit;

	/**
	 * Корректировка
	 */
	private double changeDebit;

	public BAbonentDebitItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BAbonentDebitItem(long reportDateId, String reportDateName,
			String billTypeName, double inMoney, double billDebit,
			double credit, double outMoney, String dbillTypeName,
			double dbillDebit, double changeDebit, long billId) {
		super();
		this.reportDateId = reportDateId;
		this.reportDateName = reportDateName;
		this.billTypeName = billTypeName;
		this.inMoney = inMoney;
		this.billDebit = billDebit;
		this.credit = credit;
		this.outMoney = outMoney;
		this.dbillTypeName = dbillTypeName;
		this.dbillDebit = dbillDebit;
		this.changeDebit = changeDebit;
		this.billId = billId;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public double getChangeDebit() {
		return changeDebit;
	}

	public void setChangeDebit(double changeDebit) {
		this.changeDebit = changeDebit;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public String getDbillTypeName() {
		return dbillTypeName;
	}

	public void setDbillTypeName(String dbillTypeName) {
		this.dbillTypeName = dbillTypeName;
	}

	public double getBillDebit() {
		return billDebit;
	}

	public void setBillDebit(double debit1) {
		this.billDebit = debit1;
	}

	public double getDbillDebit() {
		return dbillDebit;
	}

	public void setDbillDebit(double debit2) {
		this.dbillDebit = debit2;
	}

	public double getInMoney() {
		return inMoney;
	}

	public void setInMoney(double inMoney) {
		this.inMoney = inMoney;
	}

	public double getOutMoney() {
		return outMoney;
	}

	public void setOutMoney(double out_money) {
		this.outMoney = out_money;
	}

	public long getReportDateId() {
		return reportDateId;
	}

	public void setReportDateId(long reportDateId) {
		this.reportDateId = reportDateId;
	}

	public String getReportDateName() {
		return reportDateName;
	}

	public void setReportDateName(String reportDateName) {
		this.reportDateName = reportDateName;
	}

	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Abonent debit item: ");
		return super.toString();
	}

	public long getBillId() {
		return billId;
	}

	public void setBillId(long billId) {
		this.billId = billId;
	}

}
