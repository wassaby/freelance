package com.realsoft.commons.beans.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.classic.Session;

import com.realsoft.commons.beans.CommonsBeansConstants;
import com.realsoft.commons.beans.CommonsBeansException;
import com.realsoft.commons.beans.HibernateManager;
import com.realsoft.utils.UtilsException;
import com.realsoft.utils.formatter.IFormatter;
import com.realsoft.utils.reflection.ObjectMethodCaller;

public class BCalendarImpl implements IBCalendar {

	private Logger log = Logger.getLogger(BCalendarImpl.class);

	private HibernateManager hibernateManager = null;

	private IFormatter formatter = null;

	public BCalendarImpl() {
	}

	public List<BSpecialDay> getSpecialDayList(Calendar dateFrom,
			Calendar dateTo) throws CommonsBeansException {
		try {
			log.debug("dateFrom = "
					+ formatter
							.format("%td-%<tm-%<tY %<tH:%<tM:%<tS", dateFrom)
					+ "; dateTo = "
					+ formatter.format("%td-%<tm-%<tY %<tH:%<tM:%<tS", dateTo));
			Session session = null;
			try {
				session = hibernateManager.openSession();
				Map<String, Object> criteria = new HashMap<String, Object>();
				criteria.put("dayFrom", dateFrom.getTime());
				criteria.put("dayTo", dateTo.getTime());
				List specialDays = hibernateManager
						.executeQuery(
								session,
								"select c from com.ujdg.gdesktop.telephony.dao.Calendar c where c.day>:dayFrom and c.day<:dayTo order by c",
								criteria);
				List<BSpecialDay> specialDaysList = new LinkedList<BSpecialDay>();
				for (Iterator iterator = specialDays.iterator(); iterator
						.hasNext();) {
					Object calendar = iterator.next();
					Date calendarDate = (Date) ObjectMethodCaller
							.invokeGetterMethod(calendar, "day");
					log.debug("specialDate = "
							+ formatter.format("%td-%<tm-%<tY %<tH:%<tM:%<tS",
									calendarDate));
					Map<String, Object> criterias = new HashMap<String, Object>();
					criterias.put("day", ObjectMethodCaller.invokeGetterMethod(
							calendar, "day"));
					Calendar date = Calendar.getInstance();
					date.setTime(calendarDate);
					Object dayType = ObjectMethodCaller.invokeGetterMethod(
							calendarDate, "dayType");
					BSpecialDay specialDay = new BSpecialDay(date,
							(String) ObjectMethodCaller.invokeGetterMethod(
									dayType, "name"),
							((Long) ObjectMethodCaller.invokeGetterMethod(
									dayType, "color")).intValue());
					List notifications = hibernateManager
							.executeQuery(
									session,
									"select n from com.ujdg.gdesktop.telephony.dao.Notify n where n.day=:day",
									criterias);
					for (Iterator iter = notifications.iterator(); iter
							.hasNext();) {
						Object notify = iter.next();
						for (Iterator i = ((Set) ObjectMethodCaller
								.invokeGetterMethod(notify, "notifyDescs"))
								.iterator(); i.hasNext();) {
							Object desc = i.next();
							specialDay.addNote((String) ObjectMethodCaller
									.invokeGetterMethod(desc, "line"));
						}
					}
					specialDaysList.add(specialDay);
				}

				return specialDaysList;
			} finally {
				hibernateManager.closeSession(session);
			}
		} catch (UtilsException e) {
			log.error("Could not get special day list", e);
			throw new BCalendarException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.calendar.getSpecialDayList.error",
					"Could not get special day list", e);
		}
	}

	public List<BSpecialDay> getSpecialDayList(BCalendarRequest request)
			throws CommonsBeansException {
		return getSpecialDayList(request.getDateFrom(), request.getDateTo());
	}

	public List<BSpecialDay> getSpecialDayList(BMonthYearRequest request)
			throws CommonsBeansException {
		log.debug("BMonthYearRequest = " + request.toString());
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, request.getYear());
		calendar.set(Calendar.MONTH, request.getMonth());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		Calendar dateFrom = (Calendar) calendar.clone();
		dateFrom.add(Calendar.MINUTE, -1);
		Calendar dateTo = (Calendar) calendar.clone();
		dateTo.add(Calendar.MONTH, 1);
		dateTo.add(Calendar.MINUTE, 1);

		return getSpecialDayList(dateFrom, dateTo);
	}

	public List<String> getSpendTasksNotificationList()
			throws CommonsBeansException, UtilsException {
		Session session = null;
		try {
			session = hibernateManager.openSession();
			List notifications = hibernateManager.executeQuery(session,
					"select n from com.ujdg.gdesktop.telephony.dao.Notify n");
			List<String> notificationList = new LinkedList<String>();
			for (Iterator iterator = notifications.iterator(); iterator
					.hasNext();) {
				Object notify = iterator.next();
				notificationList.add((String) ObjectMethodCaller
						.invokeGetterMethod(notify, "theme"));
			}
			return notificationList;
		} finally {
			hibernateManager.closeSession(session);
		}
	}

	public IFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(IFormatter formatter) {
		this.formatter = formatter;
	}

	public HibernateManager getHibernateManager() {
		return hibernateManager;
	}

	public void setHibernateManager(HibernateManager hibernateManager) {
		this.hibernateManager = hibernateManager;
	}

}
