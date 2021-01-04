/*
 * Created on 03.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: IBReport.java,v 1.2 2016/04/15 10:37:39 dauren_home Exp $
 */
package com.realsoft.commons.beans.report.mbd;

import java.util.List;

import com.realsoft.commons.beans.report.abonent.BReportException;

/**
 * Используется в проектах: billing-operator, billing-reporter.
 * 
 * @author temirbulatov
 * 
 */
public interface IBReport {
	/**
	 * Получает информацию по оказанным услугам по месяцам.
	 * 
	 * @param abonentId
	 *            Лицевой счет абонента
	 * @param deviceId
	 *            Идентификатор устройства
	 * @param reportDate
	 *            Дата отчета
	 * @return {@link BReportItem}
	 * @throws BMonthByDayException
	 */
	public List getMonthByServices(long abonentId, long deviceId,
			long reportDate) throws BMonthByDayException;

	/**
	 * Получает информацию по оказанным услугам за определенную дату.
	 * 
	 * @param reportDate
	 *            Дата отчета
	 * @param abonentId
	 *            Лицевой счет абонента
	 * @return {@link BMonthByDayItem}
	 * @throws BReportException
	 */
	public List getReportForPeriod(long reportDate, long abonentId)
			throws BReportException;

	/**
	 * Получает информацию по оказанным услугам за определенную дату.
	 * 
	 * @param abonentId
	 *            Лицевой счет абонента
	 * @param deviceId
	 *            Идентификатор устройства
	 * @param reportDate
	 *            Дата отчета
	 * @return {@link BReportForPeriodItem}
	 * @throws BMonthByDayException
	 */
	public List getReportForPeriod(long abonentId, long deviceId,
			long reportDate) throws BMonthByDayException;

	/**
	 * 
	 * @param abonentId
	 *            Лицевой счет абонента
	 * @param deviceId
	 *            Идентификатор устройства
	 * @param reportDate
	 *            Дата отчета
	 * @return {@link BMonthByDayItem}
	 * @throws BMonthByDayException
	 */
	public List getMonthByDay(long abonentId, long deviceId, long reportDate)
			throws BMonthByDayException;

	/**
	 * 
	 * @param reportDate
	 *            Дата отчета
	 * @param abonentId
	 *            Лицевой счет абонента
	 * @param connect
	 *            Устройство
	 * @return {@link BReportByConnectionItem}
	 * @throws BReportException
	 */
	public List getReportByConnection(String reportDate, long abonentId,
			String connect) throws BReportException;

}