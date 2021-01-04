/*
 * Created on 28.09.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BPrevalentTable.java,v 1.2 2016/04/15 10:37:29 dauren_home Exp $
 */
package com.realsoft.commons.beans.persist;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class BPrevalentTable implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name = null;

	private Map<Serializable, IBRow> rows = new LinkedHashMap<Serializable, IBRow>();

	public BPrevalentTable(String name) {
		super();
		this.name = name;
	}

	public BPrevalentTable(String name, Map<Serializable, IBRow> rows) {
		super();
		this.name = name;
		this.rows = rows;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<IBRow> getAllRows() {
		return rows.values();
	}

	public IBRow getRow(Object rowId) {
		return rows.get(rowId);
	}

	public IBRow putRow(IBRow row) {
		rows.put(row.getId(), row);
		return row;
	}

	public void deleteRow(Object rowId) {
		rows.remove(rowId);
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BPrevalentTable other = (BPrevalentTable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
