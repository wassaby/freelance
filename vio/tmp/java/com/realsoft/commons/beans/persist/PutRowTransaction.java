/*
 * Created on 28.09.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: PutRowTransaction.java,v 1.2 2016/04/15 10:37:29 dauren_home Exp $
 */
package com.realsoft.commons.beans.persist;

import java.util.Date;

import org.apache.log4j.Logger;
import org.prevayler.TransactionWithQuery;

import com.realsoft.commons.beans.CommonsBeansConstants;

public class PutRowTransaction implements TransactionWithQuery {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(PutRowTransaction.class);

	private String schemaName = null;

	private String tableName = null;

	private IBRow row = null;

	private String regionId = null;

	public PutRowTransaction(String schemaName, String tableName, IBRow row,
			String regionId) {
		this.schemaName = schemaName;
		this.tableName = tableName;
		this.row = row;
		this.regionId = regionId;
	}

	public Object executeAndQuery(Object prevalentSystem, Date date)
			throws Exception {
		BPrevalentDatabase database = (BPrevalentDatabase) prevalentSystem;
		try {
			BPrevalentTable table = database.getSchema(schemaName).getTable(
					tableName);
			BPrevalentTable generatorTable = database.getSchema(schemaName)
					.getTable(IBPersist.GENERATOR_TABLE);
			IBRow generatorRow = generatorTable.getRow((long) 1);
			if (row.getId() == null) {
				log.debug("Generating transId, regionId = " + regionId
						+ "; generatorRow.getObject() = "
						+ ((Long) generatorRow.getObject()).longValue());
				String rowIdObject = generatorRow.getObject().toString();
				rowIdObject = rowIdObject
						.substring(0, rowIdObject.length() - 3);
				Long rowId = Long
						.valueOf(((Long) (Long.valueOf(rowIdObject) + 1))
								.toString()
								+ regionId);
				log.debug("new rowId = " + rowId);
				generatorRow.setObject(rowId);
				generatorTable.putRow(generatorRow);
				row.setId(rowId);
			}
			return table.putRow(row);
		} catch (BPersistException e) {
			log.error("Could not execute insertion to table " + schemaName
					+ "." + tableName, e);
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.store.executing-put-transaction.error",
					"Could not execute transaction");
		}
	}

}
