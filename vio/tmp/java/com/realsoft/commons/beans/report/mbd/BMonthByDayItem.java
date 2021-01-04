package com.realsoft.commons.beans.report.mbd;

import java.io.Serializable;
import java.util.Date;

/**
 * Информация по оказанной услуге за определенную дату.
 * 
 * @author temirbulatov
 * 
 */
public class BMonthByDayItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long serviceId;

	private Date date;

	private String service = null;

	private double volume;

	private String unit = null;

	private double billingVolume;

	private double charged;

	private String direction;

	private String currency = null;

	/**
	 * 
	 * @param serviceId
	 *            Не нашел поля web.view_accounting_for_period.service_id
	 * @param date
	 *            Дата оказания услуг
	 * @param service
	 *            Наименование услуг
	 * @param volume
	 *            (Объем в базовых единицах)/(Коэф. умножения на базовую единицу
	 *            измерения)
	 * @param unit
	 *            Сокращенное название единицы измерения (tr.unit.short_name)
	 * @param billingVolume
	 *            Биллинговый объем (db.tdr.service_count)
	 * @param charged
	 *            Начислено (db.tdr.debit)
	 * @param direction
	 *            Не нашел поля web.view_accounting_for_period.DETAIL_TYPE_NAME
	 * @param currency
	 *            Код типа валюты, например KZT (tr.currency_type.code)
	 */
	public BMonthByDayItem(long serviceId, Date date, String service,
			double volume, String unit, double billingVolume, double charged,
			String direction, String currency) {
		super();
		this.serviceId = serviceId;
		this.date = date;
		this.service = service;
		this.volume = volume;
		this.unit = unit;
		this.billingVolume = billingVolume;
		this.charged = charged;
		this.direction = direction;
		this.currency = currency;
	}

	public double getBillingVolume() {
		return billingVolume;
	}

	public void setBillingVolume(double billingVolume) {
		this.billingVolume = billingVolume;
	}

	public double getCharged() {
		return charged;
	}

	public void setCharged(double charged) {
		this.charged = charged;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
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

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
