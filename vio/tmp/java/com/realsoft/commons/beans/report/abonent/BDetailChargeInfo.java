/*
 * Created on 12.10.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BDetailChargeInfo.java,v 1.2 2016/04/15 10:37:45 dauren_home Exp $
 */
package com.realsoft.commons.beans.report.abonent;

import java.io.Serializable;
import java.util.Date;

/**
 * Класс, содержащий информацию о начислениях за услуги биллинговых сервисов
 * 
 * @author temirbulatov
 * 
 */
public class BDetailChargeInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String device;

	private Date serviceDate;

	private String detailName;

	private String detailData;

	private String detail;

	private Long serviceCount;

	private double debit;

	private String scName;

	private String reportDate;

	private long billTypeId;

	private String billTypeName;

	private long servicePackTypeId;

	/**
	 * Конструктор класса {@link BDetailChargeInfo}
	 * 
	 * @param device
	 *            код устройства
	 * @param serviceDate
	 *            дата оказания биллинговой услуги
	 * @param detailName
	 *            имя сервиса (НЕТ - не телефонный разговор)
	 * @param detailData
	 *            код услуги
	 * @param detail
	 *            название устройства
	 * @param serviceCount
	 *            количество предоставленного сервиса
	 * @param scName
	 *            наименование типа сервиса
	 * @param debit
	 *            дебит
	 * @param reportDate
	 *            отчетный период
	 * @param billTypeId
	 *            идентификатор типа биллинговой услуги
	 * @param billTypeName
	 *            название типа биллинговой услуги
	 * @param servicePackTypeId
	 *            идентификатор типа сервиса
	 */
	public BDetailChargeInfo(String device, Date serviceDate,
			String detailData, String detail, String detailName,
			Long serviceCount, String scName, double debit, String reportDate,
			long billTypeId, String billTypeName, long servicePackTypeId) {
		super();
		this.device = device;
		this.serviceDate = serviceDate;
		this.detailData = detailData;
		this.detail = detail;
		this.detailName = detailName;
		this.serviceCount = serviceCount;
		this.scName = scName;
		this.debit = debit;
		this.reportDate = reportDate;
		this.billTypeId = billTypeId;
		this.billTypeName = billTypeName;
		this.servicePackTypeId = servicePackTypeId;
	}

	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getDetailData() {
		return detailData;
	}

	public void setDetailData(String detailData) {
		this.detailData = detailData;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public Long getServiceCount() {
		return serviceCount;
	}

	public void setServiceCount(Long serviceCount) {
		this.serviceCount = serviceCount;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String serviceName) {
		this.detailName = serviceName;
	}

	public String getScName() {
		return scName;
	}

	public void setScName(String scName) {
		this.scName = scName;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public long getBillTypeId() {
		return billTypeId;
	}

	public void setBillTypeId(long billTypeId) {
		this.billTypeId = billTypeId;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public long getServicePackTypeId() {
		return servicePackTypeId;
	}

	public void setServicePackTypeId(long servicePackTypeId) {
		this.servicePackTypeId = servicePackTypeId;
	}

}
