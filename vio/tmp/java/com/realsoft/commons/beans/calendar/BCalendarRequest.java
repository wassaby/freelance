package com.realsoft.commons.beans.calendar;

import java.util.Calendar;

public class BCalendarRequest {

	private Calendar dateFrom = null;

	private Calendar dateTo = null;

	public BCalendarRequest() {
	}

	public Calendar getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Calendar dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Calendar getDateTo() {
		return dateTo;
	}

	public void setDateTo(Calendar dateTo) {
		this.dateTo = dateTo;
	}

}
