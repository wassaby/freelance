package com.realsoft.commons.beans.calendar;

import java.util.Calendar;
import java.util.List;

import com.realsoft.commons.beans.CommonsBeansException;
import com.realsoft.utils.UtilsException;

public interface IBCalendar {

	List<BSpecialDay> getSpecialDayList(Calendar monthFrom, Calendar monthTo)
			throws CommonsBeansException;

	List<BSpecialDay> getSpecialDayList(BCalendarRequest request)
			throws CommonsBeansException;

	List<BSpecialDay> getSpecialDayList(BMonthYearRequest request)
			throws CommonsBeansException;

	List<String> getSpendTasksNotificationList() throws CommonsBeansException,
			UtilsException;

}
