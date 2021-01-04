/*
 * Created on 28.09.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BPrevalentSchema.java,v 1.2 2016/04/15 10:37:29 dauren_home Exp $
 */
package com.realsoft.commons.beans.persist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.realsoft.commons.beans.CommonsBeansConstants;

public class BPrevalentSchema implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name = null;

	private Map<String, BPrevalentTable> tables = new LinkedHashMap<String, BPrevalentTable>();

	public BPrevalentSchema(String name) {
		super();
		this.name = name;
	}

	public BPrevalentTable getTable(String name) throws BPersistException {
		if (!tables.containsKey(name))
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.store.getting-table.table-abcent.error",
					"Such table " + name + " does not exists");
		return tables.get(name);
	}

	public BPrevalentTable createTable(String name) {
		if (!tables.containsKey(name)) {
			BPrevalentTable table = new BPrevalentTable(name);
			tables.put(name, table);
			return table;
		} else {
			return tables.get(name);
		}
	}

	public List<String> getTableList() {
		List<String> tableList = new ArrayList<String>();
		for (BPrevalentTable table : tables.values()) {
			tableList.add(table.getName());
		}
		return tableList;
	}

	public void removeTable(String name) {
		tables.remove(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		final BPrevalentSchema other = (BPrevalentSchema) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
