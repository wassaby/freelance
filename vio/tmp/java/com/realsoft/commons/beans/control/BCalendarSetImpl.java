package com.realsoft.commons.beans.control;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class BCalendarSetImpl extends AbstractControl implements IBCalendarSet {

	private static final long serialVersionUID = 673845208300178519L;

	private List<BCalendar> calendarItems = new LinkedList<BCalendar>();

	private Calendar calendar = null;

	public BCalendarSetImpl(String name) {
		super(name);
	}

	public BCalendarSetImpl(String name, IBRequest request) {
		super(name, request);
	}

	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof BCalendarSetImpl) {
			calendarItems = ((BCalendarSetImpl) model).calendarItems;
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	public List<BCalendar> getCalendarItems() {
		return calendarItems;
	}

	public void setCalendarItems(List<BCalendar> calendarItems) {
		this.calendarItems = calendarItems;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

}
