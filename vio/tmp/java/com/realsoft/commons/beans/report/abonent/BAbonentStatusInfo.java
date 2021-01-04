/*
 * Created on 11.10.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BAbonentStatusInfo.java,v 1.2 2016/04/15 10:37:45 dauren_home Exp $
 */
package com.realsoft.commons.beans.report.abonent;

import java.io.Serializable;

/**
 * Этот класс содержит информацию о статусе некоторого счета
 * 
 * @author temirbulatov
 * 
 */
public class BAbonentStatusInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String reportDateName;

	private String billTypeName;

	private double inMoney;

	private double debit;

	private double credit;

	private double outMoney;

	private long reportDateId;

	private long billTypeId;

	public BAbonentStatusInfo() {
		super();
	}

	/**
	 * 
	 * @param reportDateName
	 *            название отчета
	 * @param billTypeName
	 *            название типа биллинговой связи (если НЕТ - то это не звонки,
	 *            если что-то другое то звонки по каким-то направлениям например
	 *            k-mobile)
	 * @param inMoney
	 *            сумма поступивших денег
	 * @param debit
	 *            дебит
	 * @param credit
	 *            кредит
	 * @param outMoney
	 *            сумма выбывших денег
	 * @param reportDateId
	 *            дата отчета
	 * @param billTypeId
	 *            тип связи
	 */
	public BAbonentStatusInfo(String reportDateName, String billTypeName,
			double inMoney, double debit, double credit, double outMoney,
			long reportDateId, long billTypeId) {
		super();
		this.reportDateName = reportDateName;
		this.billTypeName = billTypeName;
		this.inMoney = inMoney;
		this.debit = debit;
		this.credit = credit;
		this.outMoney = outMoney;
		this.reportDateId = reportDateId;
		this.billTypeId = billTypeId;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
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

	public void setOutMoney(double outMoney) {
		this.outMoney = outMoney;
	}

	public String getReportDateName() {
		return reportDateName;
	}

	public void setReportDateName(String reportDateName) {
		this.reportDateName = reportDateName;
	}

	public long getBillTypeId() {
		return billTypeId;
	}

	public void setBillTypeId(long billTypeId) {
		this.billTypeId = billTypeId;
	}

	public long getReportDateId() {
		return reportDateId;
	}

	public void setReportDateId(long reportDateId) {
		this.reportDateId = reportDateId;
	}

}
