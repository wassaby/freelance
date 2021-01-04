/**
 * BPaymentItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 16, 2004 (12:19:44 EST) WSDL2Java emitter.
 */

package com.realsoft.commons.beans.report.mbd;

import java.util.Date;

/**
 * 
 * @author temirbulatov
 * 
 */
public class BReportForPeriodItem implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Date serviceDate;

	private String service;

	private String tariffPlan;

	private String code;

	private Long volume;

	private String connection;

	private String clientName;

	private String unit;

	/**
	 * 
	 * @param serviceDate
	 *            Дата оказания услуги
	 * @param service
	 *            Наименование подключения
	 * @param tariffPlan
	 *            Нименование тарифного плана
	 * @param code
	 *            Код услуги
	 * @param volume
	 *            Сумма объема услуги. Указывается в единицах измерения, которые
	 *            заданы для услуги в тарифном плане
	 * @param connection
	 *            Наименование устройства
	 * @param clientName
	 *            Примечание параметров связи абонент устройство
	 * @param unit
	 *            Наименование единицы измерения
	 */
	public BReportForPeriodItem(Date serviceDate, String service,
			String tariffPlan, String code, Long volume, String connection,
			String clientName, String unit) {
		super();
		// TODO Auto-generated constructor stub
		this.serviceDate = serviceDate;
		this.service = service;
		this.tariffPlan = tariffPlan;
		this.code = code;
		this.volume = volume;
		this.connection = connection;
		this.clientName = clientName;
		this.unit = unit;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
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

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

}