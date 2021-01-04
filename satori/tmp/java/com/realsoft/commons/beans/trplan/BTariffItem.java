/*
 * Created on 01.12.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BTariffItem.java,v 1.1 2014/07/01 11:58:27 dauren_work Exp $
 */
package com.realsoft.commons.beans.trplan;

import java.io.Serializable;
import java.util.Date;

public class BTariffItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long tariffId;

	private String name;

	private Date systemDate;

	private long servicePacketTypeId;

	private long serviceCountGroupId;

	private double tariff;

	public BTariffItem() {
		super();
	}

	/**
	 * 
	 * @param tariffId
	 *            Идентификатор тарифного плана
	 * @param name
	 *            Наименование тарифного плана
	 */
	public BTariffItem(long tariffId, String name) {
		super();
		this.tariffId = tariffId;
		this.name = name;
	}

	/**
	 * 
	 * @param tariffId
	 *            Идентификатор тарифного плана
	 * @param name
	 *            Наименование тарифного плана
	 * @param systemDate
	 *            Системная дата
	 */
	public BTariffItem(long tariffId, String name, Date systemDate) {
		super();
		this.tariffId = tariffId;
		this.name = name;
		this.systemDate = systemDate;
	}

	/**
	 * 
	 * @param tariffId
	 *            Идентификатор тарифного плана
	 * @param name
	 *            Наименование тарифного плана
	 * @param systemDate
	 *            Системная дата
	 * @param tariff
	 *            Тарифный план
	 */
	public BTariffItem(long tariffId, String name, Date systemDate,
			double tariff) {
		super();
		this.tariffId = tariffId;
		this.name = name;
		this.systemDate = systemDate;
		this.tariff = tariff;
	}

	/**
	 * 
	 * @param tariffId
	 *            Идентификатор тарифного плана
	 * @param name
	 *            Наименование тарифного плана
	 * @param systemDate
	 *            Системная дата
	 * @param servicePacketTypeId
	 * @param serviceCountGroupId
	 * @param tariff
	 *            Тариф
	 */
	public BTariffItem(long tariffId, String name, Date systemDate,
			long servicePacketTypeId, long serviceCountGroupId, double tariff) {
		super();
		this.tariffId = tariffId;
		this.name = name;
		this.systemDate = systemDate;
		this.servicePacketTypeId = servicePacketTypeId;
		this.serviceCountGroupId = serviceCountGroupId;
		this.tariff = tariff;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getSystemDate() {
		return systemDate;
	}

	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}

	public long getTariffId() {
		return tariffId;
	}

	public void setTariffId(long tariffId) {
		this.tariffId = tariffId;
	}

	public long getServiceCountGroupId() {
		return serviceCountGroupId;
	}

	public void setServiceCountGroupId(long serviceCountGroupId) {
		this.serviceCountGroupId = serviceCountGroupId;
	}

	public long getServicePacketTypeId() {
		return servicePacketTypeId;
	}

	public void setServicePacketTypeId(long servicePacketTypeId) {
		this.servicePacketTypeId = servicePacketTypeId;
	}

	public double getTariff() {
		return tariff;
	}

	public void setTariff(double tariff) {
		this.tariff = tariff;
	}

}
