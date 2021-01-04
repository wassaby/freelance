/*
 * Created on 06.03.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BRowItem.java,v 1.2 2016/04/15 10:37:06 dauren_home Exp $
 */
package com.realsoft.commons.beans.control;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;

public class BRowItem implements IBItem, Serializable {

	private static final long serialVersionUID = 4476596602244320487L;

	private Long rowNum;

	private Object id;

	private Map<String, Object> values = null;

	private Object object = null;

	private Integer pushedColumn = -1;

	public BRowItem(Long rowNum) {
		super();
		this.rowNum = rowNum;
		values = new HashMap<String, Object>();
	}

	public BRowItem(Long rowNum, Map<String, Object> values) {
		super();
		this.rowNum = rowNum;
		this.values = values;
	}

	public BRowItem(Long rowNum, Object id, Map<String, Object> values) {
		super();
		this.rowNum = rowNum;
		this.id = id;
		this.values = values;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public Map<String, Object> getValues() {
		return values;
	}

	public void setValues(Map<String, Object> values) {
		this.values = values;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Object getValue(String name) {
		return values.get(name);
	}

	public void addValue(String name, Object value) {
		values.put(name, value);
	}

	public Long getRowNum() {
		return rowNum;
	}

	public void setRowNum(Long rowNum) {
		this.rowNum = rowNum;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BRowItem)) {
			return false;
		}
		BRowItem castOther = (BRowItem) other;
		EqualsBuilder builder = new EqualsBuilder().append(getId(), castOther
				.getId());
		for (String name : values.keySet()) {
			builder.append(getValue(name), castOther.getValue(name));
		}
		return builder.isEquals();
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n").append(rowNum).append("[").append(
				id != null ? id.toString() : "");
		for (String column : values.keySet()) {
			buffer.append(", ").append(values.get(column));
		}
		buffer.append("]\n\t");
		return buffer.toString();
	}

}
