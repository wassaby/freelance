/*
 * Created on 28.09.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: DeleteRowTransaction.java,v 1.2 2016/04/15 10:37:28 dauren_home Exp $
 */
package com.realsoft.commons.beans.persist;

import java.util.Date;

import org.apache.log4j.Logger;
import org.prevayler.Transaction;

public class DeleteRowTransaction implements Transaction {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(DeleteRowTransaction.class);

	private String schemaName = null;

	private String tableName = null;

	private Object rowId = null;

	public DeleteRowTransaction(String schemaName, String tableName,
			Object rowId) {
		this.schemaName = schemaName;
		this.tableName = tableName;
		this.rowId = rowId;
	}

	public void executeOn(Object prevalentSystem, Date executionTime) {
		BPrevalentDatabase database = (BPrevalentDatabase) prevalentSystem;
		try {
			BPrevalentTable table = database.getSchema(schemaName).getTable(
					tableName);
			table.deleteRow(rowId);
		} catch (BPersistException e) {
			log.error("Could not execute delete transaction on table "
					+ schemaName + "." + tableName, e);
		}
	}

}
