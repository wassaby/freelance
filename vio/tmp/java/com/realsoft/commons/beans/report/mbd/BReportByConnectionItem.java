/**
 * BPaymentItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.report.mbd;

/**
 * Отчет по подключению
 * 
 * @author temirbulatov
 * 
 */
public class BReportByConnectionItem implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String serviceDate;

	private String service;

	private String tariffPlan;

	private String code;

	private double volume;

	private String unit;

	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getTariffPlan() {
		return tariffPlan;
	}

	public void setTariffPlan(String tariffPlan) {
		this.tariffPlan = tariffPlan;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double value) {
		this.volume = value;
	}

	/**
	 * 
	 * @param serviceDate
	 *            Дата оказания услуги
	 * @param service
	 *            Наименование типа подключения
	 * @param tariffPlan
	 *            Наименование тарифного плана
	 * @param code
	 *            Код услуги
	 * @param volume
	 *            объем услуги. Указывается в единицах измерения, которые заданы
	 *            для услуги в тарифном плане
	 * @param unit
	 *            Наименование единицы измерения
	 * @param account
	 *            Логин аккаунта
	 */
	public BReportByConnectionItem(String serviceDate, String service,
			String tariffPlan, String code, double volume, String unit,
			String account) {
		super();
		// TODO Auto-generated constructor stub
		this.serviceDate = serviceDate;
		this.service = service;
		this.tariffPlan = tariffPlan;
		this.code = code;
		this.volume = volume;
		this.unit = unit;
		this.account = account;
	}
}
