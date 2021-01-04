package com.realsoft.commons.beans.report.mbd;

import java.io.Serializable;

/**
 * Информация по оказанной услуге за месяц.
 * 
 * @author temirbulatov
 * 
 */
public class BReportItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String service = null;

	private double volume;

	private double billingVolume;

	private String unit = null;

	private double charged;

	private String currency = null;

	// private static DecimalFormat volumeFormat;
	//
	// private static DecimalFormat amountFormat;
	//
	// static {
	// DecimalFormatSymbols symbols = new DecimalFormatSymbols();
	// symbols.setDecimalSeparator('.');
	// symbols.setGroupingSeparator(',');
	//
	// volumeFormat = new DecimalFormat("###,##0.###");
	// volumeFormat.setDecimalFormatSymbols(symbols);
	//
	// amountFormat = new DecimalFormat("###,##0.##");
	// amountFormat.setDecimalFormatSymbols(symbols);
	// }
	/**
	 * 
	 * @param service
	 *            Название услуги (tr.service.name)
	 * @param volume
	 *            Сумма отношений объема в базовых единицах к коэффициенту
	 *            умножения на базовую единицу измерения.
	 * @param billingVolume
	 *            Биллинговый объем
	 * @param unit
	 *            Сокращенное наименование единицы измерения(tr.unit.short_name)
	 * @param charged
	 *            Начислено (db.tdr.debit)
	 * @param currency
	 *            Код типа валюты, например KZT (tr.currency_type.code)
	 */
	public BReportItem(String service, double volume, double billingVolume,
			String unit, double charged, String currency) {
		super();
		// TODO Auto-generated constructor stub
		this.service = service;
		this.volume = volume;
		this.billingVolume = billingVolume;
		this.unit = unit;
		this.charged = charged;
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
}
