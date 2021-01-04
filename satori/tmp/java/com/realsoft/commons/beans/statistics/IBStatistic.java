package com.realsoft.commons.beans.statistics;

import java.util.Date;
import java.util.List;

/**
 * тут путаница: в имплементиции вместо List getDefaultStatistic(String
 * radiusLoginName) стоит List getDefaultStatistic(String webLoginName) вместо
 * List getStatistic(String webLoginName, int currDate) throws
 * BStatisticException; стоит List getStatistic(String radiusLoginName, int
 * currDate) throws BStatisticException;
 * 
 * @author temirbulatov
 * 
 */
public interface IBStatistic {

	/**
	 * Получает информацию об оказанных услугах определенного логина радиуса
	 * 
	 * @param radiusLoginId
	 *            ID радиус-логина
	 * @return информация об оказанных услугах
	 * @throws BStatisticException
	 */
	List getDefaultStatistic(long radiusLoginId) throws BStatisticException;

	/**
	 * Получает
	 * 
	 * @param radiusLoginId
	 *            ID радиус-логина
	 * @param currDate
	 *            дата
	 * @return
	 * @throws BStatisticException
	 */
	List getStatistic(long radiusLoginId, int currDate)
			throws BStatisticException;

	/**
	 * 
	 * @return
	 * @throws BStatisticException
	 */
	List getStatisticPeriods() throws BStatisticException;

	List getStatisticPeriods(Date fromDate, Date toDate)
			throws BStatisticException;

	BStatisticItem getStatistic(long deviceId) throws BStatisticException;

	List getTrafficForPeriod(Date dateFrom, Date dateTo)
			throws BStatisticException;

	List getConnectionForPeriod(Date dateFrom, Date dateTo)
			throws BStatisticException;

	List getNewAccountForPeriod(Date dateFrom, Date dateTo)
			throws BStatisticException;

}
