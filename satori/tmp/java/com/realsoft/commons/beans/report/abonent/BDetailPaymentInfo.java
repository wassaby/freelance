/*
 * Created on 12.10.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BDetailPaymentInfo.java,v 1.1 2014/07/01 11:58:25 dauren_work Exp $
 */
package com.realsoft.commons.beans.report.abonent;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author dimad
 */
public class BDetailPaymentInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String reportDate;

	private double checkNum;

	private String moneyTypeName;

	private Calendar systemDate;

	private double summa;

	private String workPlace;

	/**
	 * Конструктор класса BDetailChargeInfo
	 * 
	 * @param reportDate
	 * @param checkNum
	 * @param name
	 * @param systemDate
	 * @param summa
	 * @param workPlace
	 */
	public BDetailPaymentInfo(String reportDate, double checkNum, String name,
			Calendar systemDate, double summa, String workPlace) {
		super();
		this.reportDate = reportDate;
		this.checkNum = checkNum;
		this.moneyTypeName = name;
		this.systemDate = systemDate;
		this.summa = summa;
		this.workPlace = workPlace;
	}

	public double getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(double checkSum) {
		this.checkNum = checkSum;
	}

	public String getMoneyTypeName() {
		return moneyTypeName;
	}

	public void setMoneyTypeName(String moneyTypeName) {
		this.moneyTypeName = moneyTypeName;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public double getSumma() {
		return summa;
	}

	public void setSumma(double summa) {
		this.summa = summa;
	}

	public Calendar getSystemDate() {
		return systemDate;
	}

	public void setSystemDate(Calendar systemDate) {
		this.systemDate = systemDate;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

}
