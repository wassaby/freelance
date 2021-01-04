package com.realsoft.commons.beans.calendar;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class BSpecialDay implements Serializable {

	private static final long serialVersionUID = -2795568565706381471L;

	private Calendar day = null;

	private String name = null;

	private int color = 1;

	private List<String> notes = new LinkedList<String>();

	public BSpecialDay(Calendar day, String name, int color) {
		super();
		this.day = day;
		this.name = name;
		this.color = color;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Calendar getDay() {
		return day;
	}

	public void setDay(Calendar day) {
		this.day = day;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getNotes() {
		return notes;
	}

	public void setNote(List<String> notes) {
		this.notes = notes;
	}

	public void addNote(String note) {
		notes.add(note);
	}

	@Override
	public String toString() {
		return day.get(Calendar.YEAR) + "/" + day.get(Calendar.MONTH) + "/"
				+ day.get(Calendar.DAY_OF_MONTH) + " - [ name = " + name
				+ "; color = " + color + " ]";
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		}
		if (!(obj instanceof BSpecialDay)) {
			return false;
		}
		BSpecialDay specialDay = (BSpecialDay) obj;
		return (specialDay.getDay().get(Calendar.MONTH) == day
				.get(Calendar.MONTH));
	}
}
