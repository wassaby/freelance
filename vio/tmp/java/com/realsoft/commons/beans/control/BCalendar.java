package com.realsoft.commons.beans.control;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import com.realsoft.commons.beans.calendar.BSpecialDay;

public class BCalendar extends AbstractControl implements IBCalendar {

	private static final long serialVersionUID = 6836359852210359364L;

	private List<BSpecialDay> specialDays = new LinkedList<BSpecialDay>();

	public BCalendar(String name, Calendar value)
			throws CommonsControlException {
		super(name);
		setValue(value);
	}

	public BCalendar(String name, IBRequest request, Calendar value)
			throws CommonsControlException {
		super(name, request);
		setValue(value);
	}

	public List<BSpecialDay> getSpecialDays() {
		return specialDays;
	}

	public void setSpecialDays(List<BSpecialDay> specialDays) {
		this.specialDays = specialDays;
	}

	public void setModel(Object model) throws CommonsControlException {
		if (model instanceof IBCalendar) {
			copyFrom((IBCalendar) model);
		}
	}

	public Object getModel() throws CommonsControlException {
		return this;
	}

	@SuppressWarnings("unchecked")
	public void setValue(Object value) throws CommonsControlException {
		if (value instanceof List) {
			this.specialDays = (List<BSpecialDay>) value;
		}
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nCALENDAR[ name=").append(name).append(" , class=")
				.append(getClass().getName()).append(" ]").append("\n\t")
				.append("{");
		int i = 0;
		for (BSpecialDay specialDay : specialDays) {
			if (i++ != 0)
				buffer.append("; ");
			buffer.append("special day = ").append(specialDay.toString());
		}
		return buffer.append("\n\t}//").append(name).toString();
	}

	public void copyFrom(BCalendar impl) throws CommonsControlException {
		super.copyFrom(impl);
		setSpecialDays(impl.getSpecialDays());
	}

}
