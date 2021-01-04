package com.realsoft.commons.beans.calendar;

public class BMonthYearRequest {

	private Integer month = 0;

	private Integer year = 0;

	public BMonthYearRequest(Integer year, Integer month) {
		this.year = year;
		this.month = month;
	}

	public BMonthYearRequest() {
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String toString() {
		return getClass().getSimpleName() + "[ month = " + month + "; year = "
				+ year + " ]";
	}

}
