package com.realsoft.commons.beans.report.abonent;

import java.io.Serializable;
import java.util.Date;

/**
 * Этот класс содержит информацию по определенному счету за некоторый период
 * 
 * @author temirbulatov
 * 
 */
public class BAbonentForPeriodItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date servDate;

	private String login;

	private String name;

	private double techVolume;

	private double volume;

	private String unit;

	private double debit;

	private String currency;

	private long rowType;

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
	 * Конструктор класса BAbonentForPeriodItem
	 * 
	 * @param date
	 * @param login
	 *            логин
	 * @param name
	 * @param techVolume
	 * @param volume
	 * @param unit
	 * @param debit
	 * @param currency
	 * @param type
	 */
	public BAbonentForPeriodItem(Date date, String login, String name,
			double techVolume, double volume, String unit, double debit,
			String currency, long type) {
		super();
		// TODO Auto-generated constructor stub
		servDate = date;
		this.login = login;
		this.name = name;
		this.techVolume = techVolume;
		this.volume = volume;
		this.unit = unit;
		this.debit = debit;
		this.currency = currency;
		rowType = type;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getRowType() {
		return rowType;
	}

	public void setRowType(long rowType) {
		this.rowType = rowType;
	}

	public Date getServDate() {
		return servDate;
	}

	public void setServDate(Date servDate) {
		this.servDate = servDate;
	}

	public double getTechVolume() {
		return techVolume;
	}

	public void setTechVolume(double techVolume) {
		this.techVolume = techVolume;
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
