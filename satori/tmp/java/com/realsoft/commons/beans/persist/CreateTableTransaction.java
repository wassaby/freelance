/*
 * Created on 28.09.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: CreateTableTransaction.java,v 1.1 2014/07/01 11:58:21 dauren_work Exp $
 */
package com.realsoft.commons.beans.persist;

import java.util.Date;

import org.apache.log4j.Logger;
import org.prevayler.Transaction;

public class CreateTableTransaction implements Transaction {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CreateTableTransaction.class);

	private String schemaName = null;

	private String tableName = null;

	public CreateTableTransaction(String schemaName, String tableName) {
		this.schemaName = schemaName;
		this.tableName = tableName;
	}

	public void executeOn(Object prevalentSystem, Date executionTime) {
		BPrevalentDatabase database = (BPrevalentDatabase) prevalentSystem;
		try {
			database.getSchema(schemaName).createTable(tableName);
		} catch (BPersistException e) {
			log.error("Could not create table " + tableName + " on schema "
					+ schemaName, e);
		}
	}

}
