package com.realsoft.commons.beans.control;

import java.util.List;

public interface IBCalendarSet extends IBControl {

	List<BCalendar> getCalendarItems();

	void setCalendarItems(List<BCalendar> calendarItems);

}
