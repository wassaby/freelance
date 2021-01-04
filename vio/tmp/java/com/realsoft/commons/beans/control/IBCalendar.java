package com.realsoft.commons.beans.control;

import java.util.List;

import com.realsoft.commons.beans.calendar.BSpecialDay;

public interface IBCalendar extends IBControl {

	List<BSpecialDay> getSpecialDays();

	void setSpecialDays(List<BSpecialDay> specialDays);

}
